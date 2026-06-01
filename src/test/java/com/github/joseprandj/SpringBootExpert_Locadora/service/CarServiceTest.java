package com.github.joseprandj.SpringBootExpert_Locadora.service;

import com.github.joseprandj.SpringBootExpert_Locadora.entity.CarEntity;
import com.github.joseprandj.SpringBootExpert_Locadora.exception.EntityNotFoundException;
import com.github.joseprandj.SpringBootExpert_Locadora.repository.CarRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
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

    @Test
    @DisplayName("Deve retornar erro caso o valor da diária seja invalido")
    void mustReturnErrorWhenDiaryValueIsInvalid(){
        CarEntity car = new CarEntity("Sedan", 0, 2027);

        // JUnit
        assertThrows(IllegalArgumentException.class, () -> service.save(car));

        // AsserJ
        Throwable erro = catchThrowable(() -> service.save(car));
        assertThat(erro).isInstanceOf(IllegalArgumentException.class);

        Mockito.verify(repository, Mockito.never()).save(Mockito.any());
    }

    @Test
    @DisplayName("Deve atualizar um carro")
    void mustUpdateDataCar(){
        Long id = 1L;

        // Instanciado para passar a verificação do metodo de update
        CarEntity carSaved = new CarEntity("Gol", 80, 2026);
        Mockito.when(repository.findById(id)).thenReturn(Optional.of(carSaved));

        // Instanciado para criação do registro
        CarEntity carUpdate = new CarEntity("Gol", 80, 2026);
        carUpdate.setId(id);
        Mockito.when(repository.save(Mockito.any())).thenReturn(carUpdate);

        CarEntity car = new CarEntity("Sedan", 0, 2027);
        CarEntity result = service.update(id, car);

        assertEquals(result.getModel(), "Sedan");
        Mockito.verify(repository).save(Mockito.any());
    }

    @Test
    @DisplayName("Deve retornar erro ao atualizar um carro inexistente")
    void mustReturnErroWhenTryUpdateCarNotExists(){
        Long id = 1L;
        CarEntity car = new CarEntity("Sedan", 0, 2027);

        Mockito.when(repository.findById(Mockito.any())).thenReturn(Optional.empty());

        Throwable erro = catchThrowable(() -> service.update(id, car));
        assertThat(erro).isInstanceOf(EntityNotFoundException.class);

        Mockito.verify(repository, Mockito.never()).save(Mockito.any());
    }

}