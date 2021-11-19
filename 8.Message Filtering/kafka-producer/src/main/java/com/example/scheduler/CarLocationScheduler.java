package com.example.scheduler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.example.entity.CarLocation;
import com.example.producer.CarLocationProducer;
import com.fasterxml.jackson.core.JsonProcessingException;

@Service
public class CarLocationScheduler {

	private Logger logger = LoggerFactory.getLogger(CarLocationScheduler.class);

	@Autowired
	private CarLocationProducer carLocationProducer;

	private CarLocation carOne;
	private CarLocation carTwo;
	private CarLocation carThree;

	public CarLocationScheduler() {
		var now = System.currentTimeMillis();

		carOne = CarLocation.builder().carId("car-one").timestamp(now).distance(0).build();
		carTwo = CarLocation.builder().carId("car-two").timestamp(now).distance(110).build();
		carThree = CarLocation.builder().carId("car-three").timestamp(now).distance(95).build();
	}

	@Scheduled(fixedRate = 10000)
	public void generateCarLocation() {
		var now = System.currentTimeMillis();
		carOne.setTimestamp(now);
		carTwo.setTimestamp(now);
		carThree.setTimestamp(now);

		carOne.setDistance(carOne.getDistance() + 1);
		carTwo.setDistance(carTwo.getDistance() - 1);
		carThree.setDistance(carThree.getDistance() + 1);

		try {
			carLocationProducer.send(carOne);
			logger.info("Sent {} " + carOne);

			carLocationProducer.send(carTwo);
			logger.info("Sent {} " + carTwo);

			carLocationProducer.send(carThree);
			logger.info("Sent {} " + carThree);
		} catch (JsonProcessingException e) {
			logger.error("Error occurred : " + e);
		}
	}
}
