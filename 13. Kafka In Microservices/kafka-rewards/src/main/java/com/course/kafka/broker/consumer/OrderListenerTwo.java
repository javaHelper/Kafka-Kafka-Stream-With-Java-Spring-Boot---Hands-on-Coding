package com.course.kafka.broker.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Service;

import com.course.kafka.broker.message.OrderMessage;
import com.course.kafka.broker.message.OrderReplyMessage;

@Service
public class OrderListenerTwo {

	private static final Logger LOG = LoggerFactory.getLogger(OrderListenerTwo.class);

	@KafkaListener(topics = "t.commodity.order")
	@SendTo("t.commodity.order-reply")
	public OrderReplyMessage listen(ConsumerRecord<String, OrderMessage> consumerRecord) {
		var headers = consumerRecord.headers();
		var orderMessage = consumerRecord.value();

		LOG.info("Processing order {}, item {}, credit card number {}", orderMessage.getOrderNumber(),
				orderMessage.getItemName(), orderMessage.getCreditCardNumber());
		LOG.info("Headers are :");
		headers.forEach(h -> LOG.info("  key : {}, value : {}", h.key(), new String(h.value())));

		var bonusPercentage = Double.parseDouble(new String(headers.lastHeader("surpriseBonus").value()));
		var bonusAmount = (bonusPercentage / 100) * orderMessage.getPrice() * orderMessage.getQuantity();

		LOG.info("Surprise bonus is {}", bonusAmount);

		var replyMessage = new OrderReplyMessage();
		replyMessage.setReplyMessage(
				"Order " + orderMessage.getOrderNumber() + " item " + orderMessage.getItemName() + " processed.");

		return replyMessage;
	}

}
