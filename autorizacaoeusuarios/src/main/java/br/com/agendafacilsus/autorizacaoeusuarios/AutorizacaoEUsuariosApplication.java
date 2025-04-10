package br.com.agendafacilsus.autorizacaoeusuarios;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"br.com.agendafacilsus"})
public class AutorizacaoEUsuariosApplication {
	public static void main(String[] args) {
		SpringApplication.run(AutorizacaoEUsuariosApplication.class, args);
	}
}
