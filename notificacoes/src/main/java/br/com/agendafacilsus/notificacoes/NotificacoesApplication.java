package br.com.agendafacilsus.notificacoes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "br.com.agendafacilsus.notificacoes")
public class NotificacoesApplication {

	public static void main(String[] args) {
		SpringApplication.run(NotificacoesApplication.class, args);
	}

}
