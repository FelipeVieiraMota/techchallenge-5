package br.com.agendafacilsus.consultas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "br.com.agendafacilsus.consultas")
public class ConsultaApplication {
	public static void main(String[] args) {
		SpringApplication.run(ConsultaApplication.class, args);
	}
}
