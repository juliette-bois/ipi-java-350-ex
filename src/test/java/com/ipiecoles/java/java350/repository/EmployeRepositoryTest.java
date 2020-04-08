package com.ipiecoles.java.java350.repository;

import com.ipiecoles.java.java350.model.Employe;
import com.ipiecoles.java.java350.model.Entreprise;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class EmployeRepositoryTest {
    @Autowired
    private EmployeRepository employeRepository;

    @BeforeEach
    @AfterEach
    public void setup(){
        employeRepository.deleteAll();
    }

    @Test
    public void testAvgPerformanceWhereMatriculeStartsWith() {
        //Given
        List<Employe> listEmploye = new ArrayList<Employe>();
        listEmploye.add(new Employe("Doe", "John", "C12345", LocalDate.now(), Entreprise.SALAIRE_BASE, 3, 1.0));
        listEmploye.add(new Employe("Bois", "Juliette", "C12312", LocalDate.now(), Entreprise.SALAIRE_BASE, 5, 1.0));
        listEmploye.add(new Employe("Champion", "Tého", "C23456", LocalDate.now(), Entreprise.SALAIRE_BASE, 9, 1.0));

        listEmploye.add(new Employe("Green", "Rachel", "T12345", LocalDate.now(), Entreprise.SALAIRE_BASE, 13, 1.0));
        listEmploye.add(new Employe("Tribbiani", "Joey", "T12312", LocalDate.now(), Entreprise.SALAIRE_BASE, 19, 1.0));
        listEmploye.add(new Employe("Geller", "Ross", "T23456", LocalDate.now(), Entreprise.SALAIRE_BASE, 26, 1.0));

        listEmploye.add(new Employe("Geller", "Monica", "M12345", LocalDate.now(), Entreprise.SALAIRE_BASE, 130, 1.0));
        listEmploye.add(new Employe("Buffay", "Phoebe", "M12312", LocalDate.now(), Entreprise.SALAIRE_BASE, 165, 1.0));
        listEmploye.add(new Employe("Bing", "Chandler", "M23456", LocalDate.now(), Entreprise.SALAIRE_BASE, 2398, 1.0));

        employeRepository.saveAll(listEmploye);

        //When
        Double performanceMoyenneCommercial = employeRepository.avgPerformanceWhereMatriculeStartsWith("C");
        Double performanceMoyenneTechnicien = employeRepository.avgPerformanceWhereMatriculeStartsWith("T");
        Double performanceMoyenneManager = employeRepository.avgPerformanceWhereMatriculeStartsWith("M");

        //Then
        Assertions.assertEquals(5.67, new BigDecimal(Double.toString(performanceMoyenneCommercial)).setScale(2, RoundingMode.HALF_UP).doubleValue());
        Assertions.assertEquals(19.33, new BigDecimal(Double.toString(performanceMoyenneTechnicien)).setScale(2, RoundingMode.HALF_UP).doubleValue());
        Assertions.assertEquals(897.67, new BigDecimal(Double.toString(performanceMoyenneManager)).setScale(2, RoundingMode.HALF_UP).doubleValue());
    }
}