# Retry Consuner 

```
E:\>kafka-topics.bat --bootstrap-server localhost:9092 --create --topic t_image --partitions 2 --replication-factor 1
WARNING: Due to limitations in metric names, topics with a period ('.') or underscore ('_') could collide. To avoid issues it is best to use either, but not both.
Created topic t_image.
```
#

```
E:\>kafka-console-consumer.bat --bootstrap-server localhost:9092 --topic t_image --from-beginning --max-messages 100
{"name":"image-1","size":515,"type":"jpg"}
{"name":"image-2","size":6927,"type":"svg"}
{"name":"image-3","size":2779,"type":"png"}
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
}
```

ImageConsumer.java

```java
@Service
public class ImageConsumer {
	private static final Logger LOGGER = LoggerFactory.getLogger(ImageConsumer.class);
	private ObjectMapper objectMapper = new ObjectMapper();

	
	@KafkaListener(topics = "t_image", containerFactory = "imageRetryContainerfactory")
	public void consume(String message)
			throws JsonMappingException, JsonProcessingException, HttpConnectTimeoutException {
		Image image = objectMapper.readValue(message, Image.class);

		if ("svg".equalsIgnoreCase(image.getType())) {
			throw new HttpConnectTimeoutException("Simulate Failed API call..");
		}

		LOGGER.info("Processing Image: {}", image);
	}
}

```

20 sec delay between next retry from Global Exception handler

![image](https://user-images.githubusercontent.com/54174687/142608022-6a3343f3-ede8-4e53-8aad-42f7cbc41952.png)


