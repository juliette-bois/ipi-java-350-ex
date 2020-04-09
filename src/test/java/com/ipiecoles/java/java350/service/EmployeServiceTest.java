package com.ipiecoles.java.java350.service;

import com.ipiecoles.java.java350.exception.EmployeException;
import com.ipiecoles.java.java350.model.Employe;
import com.ipiecoles.java.java350.repository.EmployeRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.*;

import java.time.LocalDate;

@ExtendWith(MockitoExtension.class)
class EmployeServiceTest {
    @InjectMocks
    EmployeService employeService;

    @Mock
    EmployeRepository employeRepository;

    @BeforeEach
    public void setup(){
        MockitoAnnotations.initMocks(this.getClass());
    }

    @ParameterizedTest
    @CsvSource({
            "'C12345', , 1000",
            "'C12345', -2000, 1000"
    })
    public void testParam1CalculPerformanceCommercial(String matricule, Long caTraite, Long objectifCa) throws EmployeException {
        //Given/When/Then
        EmployeException e = Assertions.assertThrows(EmployeException.class, () -> employeService.calculPerformanceCommercial(matricule, caTraite, objectifCa));
        Assertions.assertEquals("Le chiffre d'affaire traité ne peut être négatif ou null !", e.getMessage());
    }

    @ParameterizedTest
    @CsvSource({
            "'C12345', 2000, ",
            "'C12345', 2000, -2000"
    })
    public void testParam2CalculPerformanceCommercial(String matricule, Long caTraite, Long objectifCa) throws EmployeException {
        //Given/When/Then
        EmployeException e = Assertions.assertThrows(EmployeException.class, () -> employeService.calculPerformanceCommercial(matricule, caTraite, objectifCa));
        Assertions.assertEquals("L'objectif de chiffre d'affaire ne peut être négatif ou null !", e.getMessage());
    }

    @ParameterizedTest
    @CsvSource({
            ", 2000, 2000",
            "'M12345', 2000, 2000"
    })
    public void testParam3CalculPerformanceCommercial(String matricule, Long caTraite, Long objectifCa) throws EmployeException {
        //Given/When/Then
        EmployeException e = Assertions.assertThrows(EmployeException.class, () -> employeService.calculPerformanceCommercial(matricule, caTraite, objectifCa));
        Assertions.assertEquals("Le matricule ne peut être null et doit commencer par un C !", e.getMessage());
    }

    //Tester tous les cas de performances (5 cas)
    @ParameterizedTest
    @CsvSource({
            "'C12345', 799, 1000, 10, 2, 9",
            "'C12345', 799, 1000, 10, 0.5, 9",
            "'C12345', 800, 1000, 3, 2, 1",
            "'C12345', 949, 1000, 2, 2, 1",
            "'C12345', 800, 1000, 5, 2, 1",
            "'C12345', 950, 1000, 2, 3, 2",
            "'C12345', 1050, 1000, 1, 0.5, 2",
            "'C12345', 1051, 1000, 2, 4, 3",
            "'C12345', 1200, 1000, 2, 2, 4",
            "'C12345', 1201, 1000, 3, 8, 7",
            "'C12345', 1201, 1000, 3, 5, 8",
    })
    public void testCalculPerformanceCommercial(
            String matricule,
            Long caTraite,
            Long objectifCa,
            Integer performanceInit,
            Double performanceMoyenne,
            Integer performanceFinale) throws EmployeException{

        //Given
        Employe employe = new Employe("Doe", "John", matricule, LocalDate.now(), 2000d, performanceInit, 1.0);
        when(employeRepository.findByMatricule(matricule)).thenReturn(employe);
        when(employeRepository.avgPerformanceWhereMatriculeStartsWith("C")).thenReturn(performanceMoyenne);

        //When
        employeService.calculPerformanceCommercial(matricule, caTraite, objectifCa);

        //Then
        ArgumentCaptor<Employe> employeArgumentCaptor = ArgumentCaptor.forClass(Employe.class);
        verify(employeRepository, times(1)).save(employeArgumentCaptor.capture());
        Assertions.assertEquals(performanceFinale, employeArgumentCaptor.getValue().getPerformance());
    }

    //Recherche d'un employé qui n'est pas présent dans la base.
    @Test
    public void testCalculPerformanceCommercialWithNoEmployeInBDD() throws EmployeException {
        //Given
        String matricule = "C12345";
        Long caTraite = 1500L;
        Long objectifCa = 1500L;
        when(employeRepository.findByMatricule(matricule)).thenReturn(null);

        //When/Then
        EmployeException e = Assertions.assertThrows(EmployeException.class, () -> employeService.calculPerformanceCommercial(matricule, caTraite, objectifCa));
        Assertions.assertEquals("Le matricule " + matricule + " n'existe pas !", e.getMessage());
    }
}