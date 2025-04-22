package br.com.agendafacilsus.especialidades.infrastructure.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class EspecialidadeRepositoryTest {

    @Autowired
    EspecialidadeRepository repository;

    @Test
    void testExistsByDescricaoIgnoreCase() {
        boolean exists = repository.existsByDescricaoIgnoreCase("Cardiologia");
        System.out.println("Existe? " + exists);
    }
}
