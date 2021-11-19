# Message Filter

# Create Topic
```
E:\>kafka-topics.bat --bootstrap-server localhost:9092 --create --topic t_location --partitions 1 --replication-factor 1
WARNING: Due to limitations in metric names, topics with a period ('.') or underscore ('_') could collide. To avoid issues it is best to use either, but not both.
Created topic t_location.
```

![image](https://user-images.githubusercontent.com/54174687/142571380-e956ed1d-e3c1-4621-9e2c-714f9c3410a7.png)


# Consumer

```java
@Service
public class CarLocationConsumer {
	private Logger logger = LoggerFactory.getLogger(CarLocationConsumer.class);

	private ObjectMapper objectMapper = new ObjectMapper();

	@KafkaListener(topics = "t_location", groupId = "cg-all-location")
	public void listenAll(String message) throws JsonMappingException, JsonProcessingException {
		CarLocation carLocation = objectMapper.readValue(message, CarLocation.class);
		logger.info("listenAll : {}", carLocation);
	}
	
	@KafkaListener(topics = "t_location", groupId = "cg-far-location", containerFactory = "farLocationContainerFactory")
	public void listenFar(String message) throws JsonMappingException, JsonProcessingException {
		CarLocation carLocation = objectMapper.readValue(message, CarLocation.class);
		logger.info("listenFar : {}", carLocation);
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

	@Bean(name = "farLocationContainerFactory")
	public ConcurrentKafkaListenerContainerFactory<Object, Object> farLocationContainerFactory(
			ConcurrentKafkaListenerContainerFactoryConfigurer configurer) {
		var factory = new ConcurrentKafkaListenerContainerFactory<Object, Object>();
		configurer.configure(factory, consumerFactory());
		factory.setRecordFilterStrategy(new RecordFilterStrategy<Object, Object>() {

			ObjectMapper objectMapper = new ObjectMapper();

			@Override
			public boolean filter(ConsumerRecord<Object, Object> consumerRecord) {
				try {
					CarLocation carLocation = objectMapper.readValue(consumerRecord.value().toString(),
							CarLocation.class);
					return carLocation.getDistance() <= 100;
				} catch (Exception e) {
					return false;
				}
			}
		});
		return factory;
	}
}

```
