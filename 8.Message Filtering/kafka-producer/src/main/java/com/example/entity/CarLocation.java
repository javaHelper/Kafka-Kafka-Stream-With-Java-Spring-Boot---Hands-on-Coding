package com.example.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CarLocation {
	@JsonProperty("car_id")
	private String carId;
	private long timestamp;
	private int distance;
}
