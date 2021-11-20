package com.course.kafka.broker.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.course.kafka.broker.message.OrderMessage;

@Service
public class OrderListener {

	private static final Logger LOG = LoggerFactory.getLogger(OrderListener.class);

	@KafkaListener(topics = "t.commodity.order")
	public void listen(OrderMessage message) {
		var totalItemAmount = message.getPrice() * message.getQuantity();

		LOG.info("Processing order {}, item {}, credit card number {}. Total amount for this item is {}",
				message.getOrderNumber(), message.getItemName(), message.getCreditCardNumber(), totalItemAmount);
	}

}
