package br.com.agendafacilsus.agendamentoseconsultas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = "br.com.agendafacilsus")
@SpringBootApplication
public class AgendamentoseconsultasApplication {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(AgendamentoseconsultasApplication.class, args);
        ServiceTest serviceTest = context.getBean(ServiceTest.class);
        serviceTest.test();
    }
}