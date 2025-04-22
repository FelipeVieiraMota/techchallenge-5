package br.com.agendafacilsus.agendamentos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = {"br.com.agendafacilsus.agendamentos",
										   "br.com.agendafacilsus.commonlibrary",
		                                   "br.com.agendafacilsus.notificacoes.infrastructure"})
@EntityScan(basePackages = "br.com.agendafacilsus.agendamentos.domain.model")
@EnableJpaRepositories(basePackages = "br.com.agendafacilsus.agendamentos.infrastructure.repository")
public class AgendamentoApplication {
	public static void main(String[] args) {
		SpringApplication.run(AgendamentoApplication.class, args);
	}
}
