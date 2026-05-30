package com.github.joseprandj.SpringBootExpert_Locadora.repository;

import com.github.joseprandj.SpringBootExpert_Locadora.entity.CarEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CarRepository extends JpaRepository<CarEntity, Long> {
    List<CarEntity> findByModel(String model);
}
