package br.com.agendafacilsus.analytics;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableAdminServer
public class AnalyticsApplication {
	public static void main(String[] args) {
		SpringApplication.run(AnalyticsApplication.class, args);
	}
}
