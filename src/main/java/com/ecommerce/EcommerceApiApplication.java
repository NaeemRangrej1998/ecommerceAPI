package com.ecommerce;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class EcommerceApiApplication {

	public static void main(String[] args) {
		System.out.println("args = " + args);
		SpringApplication.run(EcommerceApiApplication.class, args);
	}

}
