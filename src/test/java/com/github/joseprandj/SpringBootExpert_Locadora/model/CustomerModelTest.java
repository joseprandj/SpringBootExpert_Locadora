package com.github.joseprandj.SpringBootExpert_Locadora.model;

import static org.junit.jupiter.api.Assertions.*;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CustomerModelTest {

    @Test
    @DisplayName("Garante a criação de cliente com nome")
    void mustCreateCustomerWithName(){
        CustomerModel customerModel = new CustomerModel("JJ");

        String name = customerModel.name();

        assertNotNull(name);
        Assertions.assertThat(name).isNotEmpty();
    }
}
