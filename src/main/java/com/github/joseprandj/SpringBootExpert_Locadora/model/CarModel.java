package com.github.joseprandj.SpringBootExpert_Locadora.model;

public record CarModel(String model, double diaryValue) {
    public double calcRentalValue(int dias){
        double valor = (dias * diaryValue);
        if (dias >= 5 ) return (valor - 50);
        return valor;
    }
}
