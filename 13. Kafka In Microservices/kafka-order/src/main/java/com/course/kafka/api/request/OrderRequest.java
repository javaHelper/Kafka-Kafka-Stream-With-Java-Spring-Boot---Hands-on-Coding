package com.course.kafka.api.request;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequest {
	private String creditCardNumber;
	private List<OrderItemRequest> items;
	private String orderLocation;
}
