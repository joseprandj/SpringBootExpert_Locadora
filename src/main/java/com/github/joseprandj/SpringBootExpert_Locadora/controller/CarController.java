package com.github.joseprandj.SpringBootExpert_Locadora.controller;

import com.github.joseprandj.SpringBootExpert_Locadora.entity.CarEntity;
import com.github.joseprandj.SpringBootExpert_Locadora.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("cars")
public class CarController {

    @Autowired
    private CarService service;

    @PostMapping
    public ResponseEntity<CarEntity> save(@RequestBody CarEntity car){
        try {
            return ResponseEntity.ok().body(service.save(car));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.unprocessableEntity().build();
        }
    }
}
