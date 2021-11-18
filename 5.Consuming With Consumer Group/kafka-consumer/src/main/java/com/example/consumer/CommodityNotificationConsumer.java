package com.example.consumer;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.example.entity.Commodity;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class CommodityNotificationConsumer {

	private ObjectMapper objectMapper = new ObjectMapper();

	private static final Logger log = LoggerFactory.getLogger(CommodityNotificationConsumer.class);

	@KafkaListener(topics = "t_commodity", groupId = "cg-notification")
	public void consume(String message) throws JsonParseException, JsonMappingException, IOException {
		var commodity = objectMapper.readValue(message, Commodity.class);
		log.info("Notification logic for {}", commodity);
	}
}