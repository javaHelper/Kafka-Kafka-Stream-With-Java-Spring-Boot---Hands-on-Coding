package com.example.entity;

import java.time.LocalDate;

import com.example.producer.json.CustomLocalDateSerializer;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Employee {
	@JsonProperty("employee_id")
	private String employeeId;
	
	private String name;
	
	@JsonProperty("birth_date")
	@JsonSerialize(using = CustomLocalDateSerializer.class)
	private LocalDate birthDate;
}