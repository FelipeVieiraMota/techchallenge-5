package br.com.agendafacilsus.agendamentos.configuration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class AppConfigTest {

    @Autowired
    private RestTemplate restTemplate;

    @Test
    public void testRestTemplateBeanCreation() {
        assertNotNull(restTemplate, "O RestTemplate deve ser criado e injetado pelo Spring");
    }
}
