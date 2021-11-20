package com.course.kafka.broker.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.course.kafka.broker.message.OrderReplyMessage;

@Service
public class OrderReplyListener {

	private static final Logger LOG = LoggerFactory.getLogger(OrderReplyListener.class);

	@KafkaListener(topics = "t.commodity.order-reply")
	public void listen(OrderReplyMessage message) {
		LOG.info("Reply message received : {}", message);
	}

}
