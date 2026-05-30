package com.github.joseprandj.SpringBootExpert_Locadora.model;

import com.github.joseprandj.SpringBootExpert_Locadora.exception.InvalidReservationException;

public record CarReservation(CarModel car, CustomerModel customer, int days) {

    public double calcReservationValue(){
        if (days < 1) throw new InvalidReservationException("Days invalid. Report 1 or more days");
        return car.calcRentalValue(days());
    }
}
