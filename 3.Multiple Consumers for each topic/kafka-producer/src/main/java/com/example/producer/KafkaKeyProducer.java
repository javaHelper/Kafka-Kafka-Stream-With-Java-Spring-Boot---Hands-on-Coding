package com.example.producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaKeyProducer {

	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;

	public void send(String key, String data) {
		kafkaTemplate.send("t_multi_partitions", key, data);
	}
}
