package com.github.joseprandj.SpringBootExpert_Locadora.repository;

import com.github.joseprandj.SpringBootExpert_Locadora.entity.CarEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
@ActiveProfiles("test")
class CarRepositoryTest {

    @Autowired
    CarRepository repository;

    @Test
    void mustSaveCar(){
       CarEntity carEntity = new CarEntity("Sedan", 100.0, 2027);

       repository.save(carEntity);

       assertNotNull(carEntity.getId());
    }
}