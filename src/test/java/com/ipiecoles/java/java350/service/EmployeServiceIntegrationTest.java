package com.ipiecoles.java.java350.service;

import com.ipiecoles.java.java350.exception.EmployeException;
import com.ipiecoles.java.java350.model.Employe;
import com.ipiecoles.java.java350.model.Entreprise;
import com.ipiecoles.java.java350.repository.EmployeRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class EmployeServiceIntegrationTest {
    @Autowired
    EmployeService employeService;

    @Autowired
    private EmployeRepository employeRepository;

    @BeforeEach
    @AfterEach
    public void setup(){
        employeRepository.deleteAll();
    }

    @Test
    public void testIntegrationCalculPerformanceCommercial() throws EmployeException {
        // Given
        String matricule = "C12345";
        Long caTraite = 799L;
        Long objectifCa = 1000L;
        Integer performanceInit = 10;
        Integer performanceFinale = 8;
        employeRepository.save(new Employe("Doe", "John", matricule, LocalDate.now(), Entreprise.SALAIRE_BASE, performanceInit, 1.0));

        // When
        employeService.calculPerformanceCommercial(matricule, caTraite, objectifCa);

        // Then
        Employe employe = employeRepository.findByMatricule(matricule);
        Assertions.assertEquals(performanceFinale, employe.getPerformance());
    }
}
