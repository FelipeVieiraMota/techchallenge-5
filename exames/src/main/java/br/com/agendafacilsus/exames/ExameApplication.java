package br.com.agendafacilsus.exames;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "br.com.agendafacilsus.exames")
public class ExameApplication {
	public static void main(String[] args) {
		SpringApplication.run(ExameApplication.class, args);
	}
}
