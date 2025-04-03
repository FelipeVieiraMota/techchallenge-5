package br.com.agendafacilsus.dummy_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"br.com.agendafacilsus"})
public class DummyApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(DummyApiApplication.class, args);
	}

}
