package com.github.joseprandj.SpringBootExpert_Locadora.service;

import com.github.joseprandj.SpringBootExpert_Locadora.entity.CarEntity;
import com.github.joseprandj.SpringBootExpert_Locadora.repository.CarRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CarServiceTest {

    @Mock // É uma implementação vazia
    CarRepository repository;

    @InjectMocks
    CarService service;

    @Test
    @DisplayName("Deve salvar um carro")
    void mustSaveCar(){
        CarEntity car = new CarEntity("Sedan", 10, 2027);
        car.setId(1L);

        Mockito.when(repository.save(Mockito.any())).thenReturn(car);

        CarEntity savedCar = service.save(car);

        assertNotNull(savedCar);
        assertEquals("Sedan", car.getModel());

        Mockito.verify(repository).save(Mockito.any());
    }

}