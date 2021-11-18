package com.example.producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.example.entity.Commodity;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class CommodityProducer {

	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;

	private ObjectMapper objectMapper = new ObjectMapper();

	public void sendMessage(Commodity commodity) throws JsonProcessingException {
		var json = objectMapper.writeValueAsString(commodity);
		kafkaTemplate.send("t_commodity", commodity.getName(), json);
	}

}