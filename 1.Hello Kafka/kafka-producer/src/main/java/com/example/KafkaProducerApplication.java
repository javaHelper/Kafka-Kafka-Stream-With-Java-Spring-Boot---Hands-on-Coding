package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.producer.HelloKafkaProducer;

@SpringBootApplication
public class KafkaProducerApplication implements CommandLineRunner{
	@Autowired
	private HelloKafkaProducer helloKafkaProducer;
	
	public static void main(String[] args) {
		SpringApplication.run(KafkaProducerApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		helloKafkaProducer.sendHello("Timotius " + Math.random());
	}
}
