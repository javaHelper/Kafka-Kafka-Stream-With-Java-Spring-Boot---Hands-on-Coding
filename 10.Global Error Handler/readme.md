# Global Error Handler

```sh
E:\>kafka-topics.bat --bootstrap-server localhost:9092 --create --topic t_simple_number --partitions 1 --replication-factor 1
WARNING: Due to limitations in metric names, topics with a period ('.') or underscore ('_') could collide. To avoid issues it is best to use either, but not both.
Created topic t_simple_number.
```

Food Order is handled by both `FoodOrderErrorHandler` and `GlobalErrorHandler`.

![image](https://user-images.githubusercontent.com/54174687/142579990-7e486105-82fd-41aa-9998-3b22e68cface.png)

GlobalErrorHandler.java

```java
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.listener.ConsumerAwareErrorHandler;
import org.springframework.stereotype.Service;


@Service
public class GlobalErrorHandler implements ConsumerAwareErrorHandler {

	private static final Logger Logger = LoggerFactory.getLogger(GlobalErrorHandler.class);
	
	@Override
	public void handle(Exception thrownException, ConsumerRecord<?, ?> data, Consumer<?, ?> consumer) {
		Logger.error("### Global Error Handler for message : {}", data.value().toString());
	}
}
```

FoodOrderErrorHandler.java

```java
import org.apache.kafka.clients.consumer.Consumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.listener.ConsumerAwareListenerErrorHandler;
import org.springframework.kafka.listener.ListenerExecutionFailedException;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;


@Service(value = "myFoodOrderErrorHandler")
public class FoodOrderErrorHandler implements ConsumerAwareListenerErrorHandler {

	private static final Logger logger = LoggerFactory.getLogger(FoodOrderErrorHandler.class);

	@Override
	public Object handleError(Message<?> message, ListenerExecutionFailedException exception, Consumer<?, ?> consumer) {
		logger.warn("### Food Order Error. Pretending to send to Elastic Search: {}, because : {}", message.getPayload(),
				exception.getMessage());
		
		// Rethrow Exception, so that it can be handled by Global Exception handler
		if(exception.getCause() instanceof RuntimeException) {
			throw exception;
		}
		
		return null;
	}
}
```

KafkaConfig.java

```java
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.ConcurrentKafkaListenerContainerFactoryConfigurer;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;

import com.example.error.handler.GlobalErrorHandler;

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
}
```
