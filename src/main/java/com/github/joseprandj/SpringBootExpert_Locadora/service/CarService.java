package com.github.joseprandj.SpringBootExpert_Locadora.service;

import com.github.joseprandj.SpringBootExpert_Locadora.entity.CarEntity;
import com.github.joseprandj.SpringBootExpert_Locadora.exception.EntityNotFoundException;
import com.github.joseprandj.SpringBootExpert_Locadora.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarService {

    @Autowired
    CarRepository repository;

    public CarEntity save(CarEntity car) {
        if (car.getDiaryValue() <= 0) throw new IllegalArgumentException("Price the diary invalid. It needs to be bigger 0");
        return repository.save(car);
    }

    public CarEntity update(Long id, CarEntity car) {
        CarEntity carExits = repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Car not found"));
        carExits.setCarYear(car.getCarYear());
        carExits.setModel(car.getModel());
        carExits.setDiaryValue(car.getDiaryValue());

        return repository.save(carExits);
    }

    public void delete(Long id){
        CarEntity carExits = repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Car not found"));
        repository.deleteById(id);
    }

    public CarEntity getById(Long id){
        return repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Car not found"));
    }

    public List<CarEntity> getAllCars(){
        return repository.findAll();
    }
}
