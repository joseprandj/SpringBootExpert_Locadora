package com.github.joseprandj.SpringBootExpert_Locadora.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "CAR")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CarEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String model;
    private double diaryValue;
    private int carYear;

    public CarEntity(String model, double diaryValue, int carYear) {
        this.model = model;
        this.diaryValue = diaryValue;
        this.carYear = carYear;
    }

}
