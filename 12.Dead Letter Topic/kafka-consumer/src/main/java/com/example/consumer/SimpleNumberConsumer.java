package com.example.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.example.entity.SimpleNumber;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class SimpleNumberConsumer {

	private static final Logger LOGGER = LoggerFactory.getLogger(SimpleNumberConsumer.class);
	
	private ObjectMapper objectMapper = new ObjectMapper();
	
	@KafkaListener(topics = "t_simple_number")
	public void consume(String message) throws JsonMappingException, JsonProcessingException {
		SimpleNumber simpleNumber = objectMapper.readValue(message, SimpleNumber.class);
		if(simpleNumber.getNumber() % 2 != 0) {
			throw new IllegalArgumentException("Odd Number");
		}
		LOGGER.info("Valid Number : {}", simpleNumber);
	}
}
