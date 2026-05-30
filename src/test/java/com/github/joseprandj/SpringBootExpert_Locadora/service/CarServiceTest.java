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

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class CarServiceTest {

    @Mock // É uma implementação vazia
    CarRepository repository;

    @InjectMocks
    CarService service;

    @Test
    @DisplayName("Deve salvar um carro")
    void mustSaveCar(){
        Mockito
            .when(repository.findById(1L))
            .thenReturn(Optional.of(new CarEntity("Test Mock", 10, 2026)));

        Optional<CarEntity> carFound = repository.findById(1L);
        System.out.println(carFound.get());


    }

}