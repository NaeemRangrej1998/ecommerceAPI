package com.ecommerce;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EntityScan("com.entity.entity")
//@ComponentScan(basePackages = {"com.entity.entity"})
public class EcommerceApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(EcommerceApiApplication.class, args);
	}

}
