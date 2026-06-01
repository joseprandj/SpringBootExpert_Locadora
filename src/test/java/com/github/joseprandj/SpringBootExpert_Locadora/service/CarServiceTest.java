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

import java.util.ArrayList;
import java.util.List;
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

        // Independente do objeto que foi atualizado, ele sempre ira retornar CarUpdate devido à forma com que o Mokito foi configurado
        assertEquals(result.getModel(), "Gol");
        Mockito.verify(repository).save(Mockito.any());
    }

    @Test
    @DisplayName("Deve retornar erro ao atualizar um carro inexistente")
    void mustReturnErroWhenTryUpdateCarNotExists(){
        CarEntity car = new CarEntity("Sedan", 0, 2027);

        Mockito.when(repository.findById(Mockito.any())).thenReturn(Optional.empty());

        // Independente do objeto que foi atualizado, ele sempre irá retornar VAZIO devido à forma com que o Mokito foi configuro
        Throwable erro = catchThrowable(() -> service.update(1L, car));
        assertThat(erro).isInstanceOf(EntityNotFoundException.class);

        Mockito.verify(repository, Mockito.never()).save(Mockito.any());
    }

    @Test
    @DisplayName("Deve deletar um carro existente")
    void mustDeleteCar(){
        Long id = 1L;
        CarEntity car = new CarEntity("Sedan", 50.0, 2026);
        car.setId(id);

        Mockito.when(repository.findById(1L)).thenReturn(Optional.of(car));

        service.delete(id);
        Mockito.verify(repository, Mockito.times(1)).delete(car);
    }

    @Test
    @DisplayName("Deve retornar erro ao deletar um carro inexistente")
    void mustReturnErroWhenTryDeleteCarNotExists(){
       Mockito.when(repository.findById(Mockito.any())).thenReturn(Optional.empty());

        Throwable erro = catchThrowable(() -> service.delete(1L));
        assertThat(erro).isInstanceOf(EntityNotFoundException.class);

        Mockito.verify(repository, Mockito.never()).delete(Mockito.any());
    }

    @Test
    @DisplayName("Deve encontra um carro pelo id")
    void mustFindCarWithId(){
        Long id = 1L;

        CarEntity car = new CarEntity("Sedan", 50.0, 2026);
        car.setId(id);
        Mockito.when(repository.findById(id)).thenReturn(Optional.of(car));

        CarEntity cartFound = service.getById(id);

        assertEquals("Sedan", cartFound.getModel());
        assertEquals(50, cartFound.getDiaryValue());
        assertEquals(2026, cartFound.getCarYear());
    }

    @Test
    @DisplayName("Deve retornar erro ao buscar um carro com id inexistente")
    void mustReturnErroWhenTryGetCarNotExists(){
        Mockito.when(repository.findById(1L)).thenReturn(Optional.empty());

        Throwable erro = catchThrowable(() -> service.getById(1L));
        assertThat(erro).isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    @DisplayName("Deve listar todos")
    void mustListAllCars(){
        List<CarEntity> carList = List.of(
            new CarEntity("Sedan", 50, 2026),
            new CarEntity("BYD", 100, 2027)
        );

        Mockito.when(repository.findAll()).thenReturn(carList);

        List<CarEntity> result = service.getAllCars();

        assertThat(result).hasSize(2);
        assertThat(result).extracting(CarEntity::getModel).contains("BYD");
        Mockito.verify(repository, Mockito.times(1)).findAll();
        Mockito.verifyNoMoreInteractions(repository);
    }
}