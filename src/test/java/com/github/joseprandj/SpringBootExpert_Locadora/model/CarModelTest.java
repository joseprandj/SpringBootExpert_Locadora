package com.github.joseprandj.SpringBootExpert_Locadora.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CarModelTest {

    @Test
    @DisplayName("Calculou o valor correto do aluguel")
    void mustCalcRentalValue(){
        // Cenário
        CarModel carModel = new CarModel("Virtus", 100);

        // Execução
        double total = carModel.calcRentalValue(3);

        // Verificação
        Assertions.assertEquals(300, total);
    }

    @Test
    @DisplayName("Calculou o valor correto do aluguel com desconto")
    void mustCalcRentalValueWithDiscount(){
        CarModel carModel = new CarModel("Virtus", 100);
        int dias = 5;

        double total = carModel.calcRentalValue(dias);

        Assertions.assertEquals(450, total);
    }
}
