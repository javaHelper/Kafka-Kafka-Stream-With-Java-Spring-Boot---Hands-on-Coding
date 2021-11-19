package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.entity.FoodOrder;
import com.example.producer.FoodOrderProducer;

@SpringBootApplication
public class KafkaProducerApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(KafkaProducerApplication.class, args);
	}

	@Autowired
	private FoodOrderProducer foodOrderProducer;
	
	@Override
	public void run(String... args) throws Exception {
		FoodOrder chickenOrder = FoodOrder.builder().amount(3).item("Chicken").build();
		FoodOrder fishOrder = FoodOrder.builder().amount(10).item("Fish").build();
		FoodOrder pizzaOrder = FoodOrder.builder().amount(5).item("Pizza").build();
		
		foodOrderProducer.send(chickenOrder);
		foodOrderProducer.send(fishOrder);
		foodOrderProducer.send(pizzaOrder);
	}
}
