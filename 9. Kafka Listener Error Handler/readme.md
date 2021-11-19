# Kafka Listener Error Handler

```
E:\>kafka-topics.bat --bootstrap-server localhost:9092 --create --topic t_food_order --partitions 1 --replication-factor 1
WARNING: Due to limitations in metric names, topics with a period ('.') or underscore ('_') could collide. To avoid issues it is best to use either, but not both.
Created topic t_food_order.
```

# Consumer

```
E:\>kafka-console-consumer.bat --bootstrap-server localhost:9092 --topic t_food_order --from-beginning --max-messages 100
{"amount":3,"item":"Chicken"}
{"amount":10,"item":"Fish"}
{"amount":5,"item":"Pizza"}
{"amount":3,"item":"Chicken"}
{"amount":10,"item":"Fish"}
{"amount":5,"item":"Pizza"}
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
		return null;
	}

}
```

FoodOrderConsumer.java

```java
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.example.entity.FoodOrder;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class FoodOrderConsumer {
	private static final Logger LOGGER = LoggerFactory.getLogger(FoodOrder.class);

	private ObjectMapper objectMapper = new ObjectMapper();

	private static final int MAX_AMOUNT_ORDER = 7;

	@KafkaListener(topics = "t_food_order", errorHandler = "myFoodOrderErrorHandler")
	public void consume(String message) throws JsonMappingException, JsonProcessingException {
		FoodOrder foodOrder = objectMapper.readValue(message, FoodOrder.class);

		if (foodOrder.getAmount() > MAX_AMOUNT_ORDER) {
			LOGGER.error("Food Order Not valid: {}", foodOrder);
			throw new IllegalArgumentException("Food order amount is high");
		}
		LOGGER.info("Food Order valid: {}", foodOrder);
	}
}

```
