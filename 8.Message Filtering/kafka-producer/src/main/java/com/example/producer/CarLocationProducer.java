package com.example.producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.example.entity.CarLocation;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


@Service
public class CarLocationProducer {
	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;

	private ObjectMapper objectMapper = new ObjectMapper();

	public void send(CarLocation carLocation) throws JsonProcessingException {
		var json = objectMapper.writeValueAsString(carLocation);
		kafkaTemplate.send("t_location", carLocation.getCarId(), json);
	}
}
