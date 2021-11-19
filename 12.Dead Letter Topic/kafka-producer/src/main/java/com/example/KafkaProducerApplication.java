package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.entity.Invoice;
import com.example.producer.InvoiceProducer;
import com.example.service.InvoiceService;

@SpringBootApplication
public class KafkaProducerApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(KafkaProducerApplication.class, args);
	}

	@Autowired
	private InvoiceService invoiceService;
	@Autowired
	private InvoiceProducer invoiceProducer;

	@Override
	public void run(String... args) throws Exception {
		for (int i = 0; i < 10; i++) {
			Invoice invoice = invoiceService.generateInvoice();

			if (i >= 5) {
				invoice.setAmount(-1);
			}

			invoiceProducer.send(invoice);
		}
	}
}
