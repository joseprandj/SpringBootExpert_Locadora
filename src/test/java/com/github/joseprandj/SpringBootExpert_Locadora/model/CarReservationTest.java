package com.github.joseprandj.SpringBootExpert_Locadora.model;

import com.github.joseprandj.SpringBootExpert_Locadora.exception.InvalidReservationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CarReservationTest {

    CarModel car = new CarModel("Virtus", 100);
    CustomerModel customer = new CustomerModel("JJ");

    @Test
    @DisplayName("Valida a criação de reserva")
    void mustReservationCreation(){
        CarReservation reservation = new CarReservation(car, customer, 5);

        String carModel = reservation.car().model();
        Double dayValue = reservation.car().diaryValue();
        String customerName = reservation.customer().name();
        double valueReservation = reservation.calcReservationValue();

        Assertions.assertNotNull(carModel);
        Assertions.assertNotEquals("", carModel);

        Assertions.assertNotNull(dayValue);

        Assertions.assertNotNull(customerName);
        Assertions.assertNotEquals("", customerName);

        Assertions.assertTrue(valueReservation > 0);
        Assertions.assertEquals(450, valueReservation);
        Assertions.assertDoesNotThrow(() -> valueReservation);
    }

    @Test
    @DisplayName("Valida a criação de reserva com dias inválidos")
    void mustReservationCreationWithDaysInvalid(){
        CarReservation reservation = new CarReservation(car, customer, 0);

        // JUnit
       Assertions.assertThrows(InvalidReservationException.class, () -> reservation.calcReservationValue());

       // AsserJ
        Throwable erro = org.assertj.core.api.Assertions.catchThrowable(() -> reservation.calcReservationValue());
        org.assertj.core.api.Assertions.assertThat(erro)
                .isInstanceOf(InvalidReservationException.class)
                .hasMessage("Days invalid. Report 1 or more days");
    }
}
