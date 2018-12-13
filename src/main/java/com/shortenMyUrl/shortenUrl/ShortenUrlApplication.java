package com.shortenMyUrl.shortenUrl;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ShortenUrlApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShortenUrlApplication.class, args);
		System.out.println("Done");
	}

}

