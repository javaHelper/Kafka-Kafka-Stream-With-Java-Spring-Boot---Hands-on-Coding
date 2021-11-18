package com.example.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.Commodity;
import com.example.service.CommodityService;

@RestController
@RequestMapping("/api/commodity/v1")
public class CommodityApi {

	@Autowired
	private CommodityService commodityService;

	@GetMapping(value = "/all")
	public List<Commodity> generateCommodities() {
		return commodityService.createDummyCommodities();
	}
}