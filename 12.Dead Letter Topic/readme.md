# Dead Letter topic

```
E:\>kafka-topics.bat --bootstrap-server localhost:9092 --create --topic t_invoice --partitions 2 --replication-factor 1
WARNING: Due to limitations in metric names, topics with a period ('.') or underscore ('_') could collide. To avoid issues it is best to use either, but not both.
Created topic t_invoice.

E:\>kafka-topics.bat --bootstrap-server localhost:9092 --create --topic t_invoice_dtl --partitions 2 --replication-factor 1
WARNING: Due to limitations in metric names, topics with a period ('.') or underscore ('_') could collide. To avoid issues it is best to use either, but not both.
Created topic t_invoice_dtl.

E:\>kafka-console-consumer.bat --bootstrap-server localhost:9092 --topic t_invoice --offset earliest --partition 0
{"number":"INV-4","amount":559.9641610382193,"currency":"INR"}
{"number":"INV-8","amount":-1.0,"currency":"INR"}
{"number":"INV-4","amount":682.4688237720636,"currency":"INR"}
{"number":"INV-8","amount":-1.0,"currency":"INR"}


E:\>kafka-topics.bat --bootstrap-server localhost:9092 --create --topic t_invoice_dlt --partitions 2 --replication-factor 1
WARNING: Due to limitations in metric names, topics with a period ('.') or underscore ('_') could collide. To avoid issues it is best to use either, but not both.
Created topic t_invoice_dlt.
```


![image](https://user-images.githubusercontent.com/54174687/142614717-03389c8e-10a8-4763-9e49-b635dd202a47.png)

InvoiceConsumer.java

```java
@Service
public class InvoiceConsumer {

	private static final Logger LOGGER = LoggerFactory.getLogger(InvoiceConsumer.class);

	private ObjectMapper objectMapper = new ObjectMapper();

	@KafkaListener(topics = "t_invoice", containerFactory = "invoiceDltContainerFactory")
	public void consume(String message) throws JsonMappingException, JsonProcessingException {
		Invoice invoice = objectMapper.readValue(message, Invoice.class);

		if (invoice.getAmount() < 1) {
			throw new IllegalArgumentException(
					"Invalid Amount : " + invoice.getAmount() + " for Invoice " + invoice.getNumber());
		}
		LOGGER.info("Processing Invoice : {}", invoice);
	}
}
```

KafkaConfig.java

```java
@Configuration
public class KafkaConfig {

	@Autowired
	private KafkaProperties kafkaProperties;

	@Bean
	public ConsumerFactory<Object, Object> consumerFactory() {
		var properties = kafkaProperties.buildConsumerProperties();

		properties.put(ConsumerConfig.METADATA_MAX_AGE_CONFIG, "120000");

		return new DefaultKafkaConsumerFactory<Object, Object>(properties);
	}

	@Bean(name = "kafkaListenerContainerFactory")
	public ConcurrentKafkaListenerContainerFactory<Object, Object> kafkaListenerContainerFactory(
			ConcurrentKafkaListenerContainerFactoryConfigurer configurer) {
		var factory = new ConcurrentKafkaListenerContainerFactory<Object, Object>();
		configurer.configure(factory, consumerFactory());
		
		factory.setErrorHandler(new GlobalErrorHandler());
		return factory;
	}
	
	private RetryTemplate createRetryTemplate() {
		// FixedBackOffPolicy
		FixedBackOffPolicy fixedBackOffPolicy = new FixedBackOffPolicy();
		fixedBackOffPolicy.setBackOffPeriod(10_000);
		
		// RetryPolicy
		RetryPolicy retryPolicy = new SimpleRetryPolicy(3);
		
		// RetryTemplate
		RetryTemplate retryTemplate = new RetryTemplate();
		retryTemplate.setRetryPolicy(retryPolicy);
		retryTemplate.setBackOffPolicy(fixedBackOffPolicy);
		return retryTemplate;
	}
	
	@Bean(name = "imageRetryContainerfactory")
	public ConcurrentKafkaListenerContainerFactory<Object, Object> imageRetryContainerfactory(
			ConcurrentKafkaListenerContainerFactoryConfigurer configurer) {
		var factory = new ConcurrentKafkaListenerContainerFactory<Object, Object>();
		configurer.configure(factory, consumerFactory());
		
		factory.setErrorHandler(new GlobalErrorHandler());
		factory.setRetryTemplate(createRetryTemplate());
		return factory;
	}
	
	
	@Bean(value = "invoiceDltContainerFactory")
	public ConcurrentKafkaListenerContainerFactory<Object, Object> invoiceDltContainerFactory(
			ConcurrentKafkaListenerContainerFactoryConfigurer configurer, 
			KafkaOperations<Object, Object> kafkaTemplate) {
		
	    var factory = new ConcurrentKafkaListenerContainerFactory<Object, Object>();
	    configurer.configure(factory, consumerFactory());
	 
	    var recoverer = new DeadLetterPublishingRecoverer(kafkaTemplate,
	        (record, ex) -> new TopicPartition("t_invoice_dlt", record.partition()));
	 
	    // 5 retry, 10 second interval for each retry
	    var errorHandler = new SeekToCurrentErrorHandler(recoverer, new FixedBackOff(10_000, 5));
	    factory.setErrorHandler(errorHandler);
	    return factory;
	}
}
```


Logs:

```java
org.springframework.kafka.KafkaException: Seek to current after exception; nested exception is org.springframework.kafka.listener.ListenerExecutionFailedException: Listener method 'public void com.example.consumer.InvoiceConsumer.consume(java.lang.String) throws com.fasterxml.jackson.databind.JsonMappingException,com.fasterxml.jackson.core.JsonProcessingException' threw exception; nested exception is java.lang.IllegalArgumentException: Invalid Amount : -1.0 for Invoice INV-9; nested exception is java.lang.IllegalArgumentException: Invalid Amount : -1.0 for Invoice INV-9
	at org.springframework.kafka.listener.SeekUtils.seekOrRecover(SeekUtils.java:206) ~[spring-kafka-2.7.9.jar:2.7.9]
	at org.springframework.kafka.listener.SeekToCurrentErrorHandler.handle(SeekToCurrentErrorHandler.java:112) ~[spring-kafka-2.7.9.jar:2.7.9]
	at org.springframework.kafka.listener.KafkaMessageListenerContainer$ListenerConsumer.invokeErrorHandler(KafkaMessageListenerContainer.java:2371) ~[spring-kafka-2.7.9.jar:2.7.9]
	at org.springframework.kafka.listener.KafkaMessageListenerContainer$ListenerConsumer.doInvokeRecordListener(KafkaMessageListenerContainer.java:2240) ~[spring-kafka-2.7.9.jar:2.7.9]
	at org.springframework.kafka.listener.KafkaMessageListenerContainer$ListenerConsumer.doInvokeWithRecords(KafkaMessageListenerContainer.java:2154) ~[spring-kafka-2.7.9.jar:2.7.9]
	at org.springframework.kafka.listener.KafkaMessageListenerContainer$ListenerConsumer.invokeRecordListener(KafkaMessageListenerContainer.java:2036) ~[spring-kafka-2.7.9.jar:2.7.9]
	at org.springframework.kafka.listener.KafkaMessageListenerContainer$ListenerConsumer.invokeListener(KafkaMessageListenerContainer.java:1709) ~[spring-kafka-2.7.9.jar:2.7.9]
	at org.springframework.kafka.listener.KafkaMessageListenerContainer$ListenerConsumer.invokeIfHaveRecords(KafkaMessageListenerContainer.java:1276) ~[spring-kafka-2.7.9.jar:2.7.9]
	at org.springframework.kafka.listener.KafkaMessageListenerContainer$ListenerConsumer.pollAndInvoke(KafkaMessageListenerContainer.java:1268) ~[spring-kafka-2.7.9.jar:2.7.9]
	at org.springframework.kafka.listener.KafkaMessageListenerContainer$ListenerConsumer.run(KafkaMessageListenerContainer.java:1163) ~[spring-kafka-2.7.9.jar:2.7.9]
	at java.base/java.util.concurrent.Executors$RunnableAdapter.call(Executors.java:515) ~[na:na]
	at java.base/java.util.concurrent.FutureTask.run(FutureTask.java:264) ~[na:na]
	at java.base/java.lang.Thread.run(Thread.java:832) ~[na:na]
Caused by: org.springframework.kafka.listener.ListenerExecutionFailedException: Listener method 'public void com.example.consumer.InvoiceConsumer.consume(java.lang.String) throws com.fasterxml.jackson.databind.JsonMappingException,com.fasterxml.jackson.core.JsonProcessingException' threw exception; nested exception is java.lang.IllegalArgumentException: Invalid Amount : -1.0 for Invoice INV-9; nested exception is java.lang.IllegalArgumentException: Invalid Amount : -1.0 for Invoice INV-9
	at org.springframework.kafka.listener.KafkaMessageListenerContainer$ListenerConsumer.decorateException(KafkaMessageListenerContainer.java:2383) ~[spring-kafka-2.7.9.jar:2.7.9]
	at org.springframework.kafka.listener.KafkaMessageListenerContainer$ListenerConsumer.doInvokeOnMessage(KafkaMessageListenerContainer.java:2354) ~[spring-kafka-2.7.9.jar:2.7.9]
	at org.springframework.kafka.listener.KafkaMessageListenerContainer$ListenerConsumer.invokeOnMessage(KafkaMessageListenerContainer.java:2315) ~[spring-kafka-2.7.9.jar:2.7.9]
	at org.springframework.kafka.listener.KafkaMessageListenerContainer$ListenerConsumer.doInvokeRecordListener(KafkaMessageListenerContainer.java:2229) ~[spring-kafka-2.7.9.jar:2.7.9]
	... 9 common frames omitted
	Suppressed: org.springframework.kafka.listener.ListenerExecutionFailedException: Restored Stack Trace
		at org.springframework.kafka.listener.adapter.MessagingMessageListenerAdapter.invokeHandler(MessagingMessageListenerAdapter.java:363) ~[spring-kafka-2.7.9.jar:2.7.9]
		at org.springframework.kafka.listener.adapter.RecordMessagingMessageListenerAdapter.onMessage(RecordMessagingMessageListenerAdapter.java:92) ~[spring-kafka-2.7.9.jar:2.7.9]
		at org.springframework.kafka.listener.adapter.RecordMessagingMessageListenerAdapter.onMessage(RecordMessagingMessageListenerAdapter.java:53) ~[spring-kafka-2.7.9.jar:2.7.9]
		at org.springframework.kafka.listener.KafkaMessageListenerContainer$ListenerConsumer.doInvokeOnMessage(KafkaMessageListenerContainer.java:2334) ~[spring-kafka-2.7.9.jar:2.7.9]
Caused by: java.lang.IllegalArgumentException: Invalid Amount : -1.0 for Invoice INV-9
	at com.example.consumer.InvoiceConsumer.consume(InvoiceConsumer.java:25) ~[classes/:na]
	at jdk.internal.reflect.GeneratedMethodAccessor30.invoke(Unknown Source) ~[na:na]
	at java.base/jdk.internal.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43) ~[na:na]
	at java.base/java.lang.reflect.Method.invoke(Method.java:564) ~[na:na]
	at org.springframework.messaging.handler.invocation.InvocableHandlerMethod.doInvoke(InvocableHandlerMethod.java:169) ~[spring-messaging-5.3.13.jar:5.3.13]
	at org.springframework.messaging.handler.invocation.InvocableHandlerMethod.invoke(InvocableHandlerMethod.java:119) ~[spring-messaging-5.3.13.jar:5.3.13]
	at org.springframework.kafka.listener.adapter.HandlerAdapter.invoke(HandlerAdapter.java:56) ~[spring-kafka-2.7.9.jar:2.7.9]
	at org.springframework.kafka.listener.adapter.MessagingMessageListenerAdapter.invokeHandler(MessagingMessageListenerAdapter.java:347) ~[spring-kafka-2.7.9.jar:2.7.9]
	at org.springframework.kafka.listener.adapter.RecordMessagingMessageListenerAdapter.onMessage(RecordMessagingMessageListenerAdapter.java:92) ~[spring-kafka-2.7.9.jar:2.7.9]
	at org.springframework.kafka.listener.adapter.RecordMessagingMessageListenerAdapter.onMessage(RecordMessagingMessageListenerAdapter.java:53) ~[spring-kafka-2.7.9.jar:2.7.9]
	at org.springframework.kafka.listener.KafkaMessageListenerContainer$ListenerConsumer.doInvokeOnMessage(KafkaMessageListenerContainer.java:2334) ~[spring-kafka-2.7.9.jar:2.7.9]
	... 11 common frames omitted

[2m2021-11-19 16:39:30.323[0;39m [32m INFO[0;39m [35m14764[0;39m [2m---[0;39m [2m[ntainer#2-0-C-1][0;39m [36mo.a.k.clients.consumer.KafkaConsumer    [0;39m [2m:[0;39m [Consumer clientId=consumer-default-spring-consumer-1, groupId=default-spring-consumer] Seeking to offset 14 for partition t_invoice-1
[2m2021-11-19 16:39:30.324[0;39m [32m INFO[0;39m [35m14764[0;39m [2m---[0;39m [2m[ntainer#2-0-C-1][0;39m [36mo.a.k.clients.consumer.KafkaConsumer    [0;39m [2m:[0;39m [Consumer clientId=consumer-default-spring-consumer-1, groupId=default-spring-consumer] Seeking to offset 2 for partition t_invoice-0
[2m2021-11-19 16:39:30.325[0;39m [31mERROR[0;39m [35m14764[0;39m [2m---[0;39m [2m[ntainer#2-0-C-1][0;39m [36messageListenerContainer$ListenerConsumer[0;39m [2m:[0;39m Error handler threw an exception
```
