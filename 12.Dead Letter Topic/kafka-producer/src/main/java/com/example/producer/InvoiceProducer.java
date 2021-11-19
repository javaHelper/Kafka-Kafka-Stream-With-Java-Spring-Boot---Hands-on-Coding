package com.example.producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.example.entity.Invoice;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class InvoiceProducer {
	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;

	private ObjectMapper objectMapper = new ObjectMapper();

	public void send(Invoice invoice) throws JsonProcessingException {
		String json = objectMapper.writeValueAsString(invoice);
		kafkaTemplate.send("t_invoice", invoice.getNumber(), json);
	}
}
