package com.rasrov.similarproducts;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class SimilarproductsApplication {

	public static void main(String[] args) {
		SpringApplication.run(SimilarproductsApplication.class, args);
	}

}
