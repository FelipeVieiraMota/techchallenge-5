package br.com.agendafacilsus.autorizacaoeusuarios;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = {"br.com.agendafacilsus.autorizacaoeusuarios",
										   "br.com.agendafacilsus.commonlibrary",
										   "br.com.agendafacilsus.notificacoes.infrastructure"})
@EntityScan(basePackages = "br.com.agendafacilsus.commonlibrary.domain.model")
@EnableJpaRepositories(basePackages = "br.com.agendafacilsus.autorizacaoeusuarios.infrastructure.repository")
public class AutorizacaoEUsuariosApplication {
	public static void main(String[] args) {
		SpringApplication.run(AutorizacaoEUsuariosApplication.class, args);
	}
}
