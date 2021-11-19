package com.example.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.example.entity.CarLocation;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

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
