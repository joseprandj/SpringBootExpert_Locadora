package com.github.joseprandj.SpringBootExpert_Locadora.exception;

public class InvalidReservationException extends RuntimeException {
    public InvalidReservationException(String message) {
        super(message);
    }
}
