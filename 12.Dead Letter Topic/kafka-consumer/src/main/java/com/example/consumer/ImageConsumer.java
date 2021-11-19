package com.example.consumer;

import java.net.http.HttpConnectTimeoutException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.example.entity.Image;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

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
