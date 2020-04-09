package com.ipiecoles.java.java350.model;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

public class EmployeTest {
    @Test
    public void testNbAnneeAncienneteNow(){
        //Given
        Employe employe = new Employe();
        employe.setDateEmbauche(LocalDate.now());

        //When
        Integer nbAnnees = employe.getNombreAnneeAnciennete();

        //Then
        Assertions.assertThat(nbAnnees).isEqualTo(0);
    }

    @Test
    public void testNbAnneeAncienneteNowMinus2(){
        //Given
        Employe employe = new Employe();
        employe.setDateEmbauche(LocalDate.now().minusYears(2));

        //When
        Integer nbAnnees = employe.getNombreAnneeAnciennete();

        //Then
        Assertions.assertThat(nbAnnees).isEqualTo(2);
    }

    @Test
    public void testNbAnneeAncienneteNowPlus3(){
        //Given
        Employe employe = new Employe();
        employe.setDateEmbauche(LocalDate.now().plusYears(3));

        //When
        Integer nbAnnees = employe.getNombreAnneeAnciennete();

        //Then
        Assertions.assertThat(nbAnnees).isEqualTo(0);
    }

    @Test
    public void testNbAnneeAncienneteNull(){
        //Given
        Employe employe = new Employe();
        employe.setDateEmbauche(null);

        //When
        Integer nbAnnees = employe.getNombreAnneeAnciennete();

        //Then
        Assertions.assertThat(nbAnnees).isEqualTo(0);
    }

    @Test
    public void testAugmenterSalaireNegatif() {
        //Given
        Employe employe = new Employe("Doe", "John", "M00001", LocalDate.now(), 1000d, 1, 1.5);
        employe.augmenterSalaire(-0.53);

        //When
        Double nouveauSalaire = employe.getSalaire();

        //Then
        Assertions.assertThat(nouveauSalaire).isEqualTo(470d);
    }

    @Test
    public void testAugmenterSalairePositif() {
        //Given
        Employe employe = new Employe("Doe", "John", "M00001", LocalDate.now(), 1000d, 1, 1.5);
        employe.augmenterSalaire(0.5);

        //When
        Double nouveauSalaire = employe.getSalaire();

        //Then
        Assertions.assertThat(nouveauSalaire).isEqualTo(1500);
    }

    @Test
    public void testAugmenterSalaireNull() {
        //Given
        Employe employe = new Employe("Doe", "John", "M00001", LocalDate.now(), 1000d, 1, 1.5);
        employe.augmenterSalaire(0.0);

        //When
        Double nouveauSalaire = employe.getSalaire();

        //Then
        Assertions.assertThat(nouveauSalaire).isEqualTo(1000);
    }

    @Test
    public void testAugmenterSalaire() {
        //Given
        Employe employe = new Employe("Doe", "John", "M00001", LocalDate.now(), 1000d, 1, 1.5);
        employe.augmenterSalaire(-1.5);

        //When
        Double nouveauSalaire = employe.getSalaire();

        //Then
        Assertions.assertThat(nouveauSalaire).isEqualTo(0);
    }
}
