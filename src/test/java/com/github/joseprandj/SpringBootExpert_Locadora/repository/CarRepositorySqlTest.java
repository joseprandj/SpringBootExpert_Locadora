package com.github.joseprandj.SpringBootExpert_Locadora.repository;

import com.github.joseprandj.SpringBootExpert_Locadora.entity.CarEntity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;
import java.util.Optional;

@DataJpaTest
@ActiveProfiles("test")
public class CarRepositorySqlTest {

    @Autowired
    CarRepository repository;

    CarEntity car = new CarEntity("Civic", 200, 2027);

    @Test
    @DisplayName("Deve criar um carro")
    @Sql("/sql/dataCars.sql")
    void mustGetCarByModel(){
        List<CarEntity> carList = repository.findByModel("SUV");

        CarEntity car = carList.stream().findFirst().get();

        assertEquals(1, carList.size());
        assertThat(car.getDiaryValue()).isEqualTo(150.0);
        assertThat(car.getModel()).isEqualTo("SUV");
    }

    @Test
    @DisplayName("Deve buscar caro por ID")
    void mustGetCarById(){
        CarEntity carSaved = repository.save(car);

        Optional<CarEntity> carFound = repository.findById(carSaved.getId());

        assertThat(carFound).isPresent();
        assertThat(carFound.get().getModel()).isEqualTo("Civic");
    }

    @Test
    @DisplayName("Deve atualizar um carro")
    void mustUpdateCar(){
        CarEntity carSaved = repository.save(car);
        carSaved.setCarYear(2028);

        CarEntity carUpdate = repository.save(car);

        assertThat(carUpdate.getCarYear()).isEqualTo(2028);
    }

    @Test
    @DisplayName("Deve deletar um carro")
    void mustDeleteCar(){
        CarEntity carSaved = repository.save(car);
        repository.deleteById(carSaved.getId());

        Optional<CarEntity> carFound = repository.findById(carSaved.getId());

        assertThat(carFound).isEmpty();
    }



}
