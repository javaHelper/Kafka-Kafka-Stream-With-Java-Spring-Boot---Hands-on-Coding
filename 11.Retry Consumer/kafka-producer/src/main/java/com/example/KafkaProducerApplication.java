package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.entity.Image;
import com.example.producer.ImageProducer;
import com.example.service.ImageService;

@SpringBootApplication
public class KafkaProducerApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(KafkaProducerApplication.class, args);
	}

	@Autowired
	private ImageProducer imageProducer;
	@Autowired
	private ImageService imageService;

	@Override
	public void run(String... args) throws Exception {
		Image image1 = imageService.generateImage("jpg");
		Image image2 = imageService.generateImage("svg");
		Image image3 = imageService.generateImage("png");

		imageProducer.send(image1);
		imageProducer.send(image2);
		imageProducer.send(image3);
	}
}
