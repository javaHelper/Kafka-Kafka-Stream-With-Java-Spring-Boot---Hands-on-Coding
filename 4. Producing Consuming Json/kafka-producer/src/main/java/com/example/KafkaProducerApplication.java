package com.example;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.entity.Employee;
import com.example.producer.EmployeeJsonProducer;

@SpringBootApplication
public class KafkaProducerApplication implements CommandLineRunner{
	@Autowired
	private EmployeeJsonProducer producer;

	public static void main(String[] args) {
		SpringApplication.run(KafkaProducerApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		for (int i = 0; i < 5; i++) {
			var employee = Employee.builder().employeeId("emp-" + i).name("Employee " + i).birthDate(LocalDate.now()).build(); 
			producer.sendMessage(employee);
		}
	}
}
