package com.example.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.example.entity.Invoice;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class InvoiceConsumer {

	private static final Logger LOGGER = LoggerFactory.getLogger(InvoiceConsumer.class);

	private ObjectMapper objectMapper = new ObjectMapper();

	@KafkaListener(topics = "t_invoice", containerFactory = "invoiceDltContainerFactory")
	public void consume(String message) throws JsonMappingException, JsonProcessingException {
		Invoice invoice = objectMapper.readValue(message, Invoice.class);

		if (invoice.getAmount() < 1) {
			throw new IllegalArgumentException(
					"Invalid Amount : " + invoice.getAmount() + " for Invoice " + invoice.getNumber());
		}
		LOGGER.info("Processing Invoice : {}", invoice);
	}
}
