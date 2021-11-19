package com.example.service;

import java.util.concurrent.ThreadLocalRandom;

import org.springframework.stereotype.Service;

import com.example.entity.Invoice;

@Service
public class InvoiceService {
	private static int counter = 0;

	public Invoice generateInvoice() {
		counter++;
		var invoiceNumber = "INV-" + counter;
		var invoiceAmount = ThreadLocalRandom.current().nextDouble(1, 1000);
		return Invoice.builder().number(invoiceNumber).amount(invoiceAmount).currency("INR").build();
	}
}
