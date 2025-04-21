package br.com.agendafacilsus.especialidades;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "br.com.agendafacilsus")
@EntityScan(basePackages = "br.com.agendafacilsus.commonlibrary.domain.model")
@EnableJpaRepositories(basePackages = "br.com.agendafacilsus.especialidades.infrastructure.repository")
public class EspecialidadeApplication {
	public static void main(String[] args) {
		SpringApplication.run(EspecialidadeApplication.class, args);
	}
}
