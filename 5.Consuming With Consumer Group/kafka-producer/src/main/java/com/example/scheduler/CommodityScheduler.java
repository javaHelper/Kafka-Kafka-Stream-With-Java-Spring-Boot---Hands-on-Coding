package com.example.scheduler;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.entity.Commodity;
import com.example.producer.CommodityProducer;
import com.fasterxml.jackson.core.JsonProcessingException;

@Service
public class CommodityScheduler {

	private static final String URL = "http://localhost:8080/api/commodity/v1/all";

	private RestTemplate restTemplate = new RestTemplate();

	@Autowired
	private CommodityProducer producer;

	@Scheduled(fixedRate = 5000)
	public void fetchCommodities() {
		var commodities = restTemplate
				.exchange(URL, HttpMethod.GET, null, new ParameterizedTypeReference<List<Commodity>>() {
				}).getBody();

		commodities.forEach(c -> {
			try {
				producer.sendMessage(c);
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
		});
	}
}