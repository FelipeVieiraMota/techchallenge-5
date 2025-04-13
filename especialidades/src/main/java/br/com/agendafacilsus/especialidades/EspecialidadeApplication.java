package br.com.agendafacilsus.especialidades;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "br.com.agendafacilsus")
public class EspecialidadeApplication {
	public static void main(String[] args) {
		SpringApplication.run(EspecialidadeApplication.class, args);
	}
}
