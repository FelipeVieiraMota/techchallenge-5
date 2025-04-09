package br.com.agendafacilsus.agendamentos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "br.com.agendafacilsus")
public class AgendamentoApplication {
	public static void main(String[] args) {
		SpringApplication.run(AgendamentoApplication.class, args);
	}
}
