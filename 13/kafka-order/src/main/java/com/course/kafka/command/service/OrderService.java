package com.course.kafka.command.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.course.kafka.api.request.OrderRequest;
import com.course.kafka.command.action.OrderAction;
import com.course.kafka.entity.Order;

@Service
public class OrderService {

	@Autowired
	private OrderAction action;

	public String saveOrder(OrderRequest request) {
		// 1. convert OrderRequest to Order
		Order order = action.convertToOrder(request);

		// 2. save Order to database
		action.saveToDatabase(order);

		// 3. flatten the item & order as kafka message, and publish
		order.getItems().forEach(action::publishToKafka);

		// 4. return order number (auto generated)
		return order.getOrderNumber();
	}

}
