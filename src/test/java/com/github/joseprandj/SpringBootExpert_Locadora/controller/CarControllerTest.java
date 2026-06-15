package com.github.joseprandj.SpringBootExpert_Locadora.controller;

import com.github.joseprandj.SpringBootExpert_Locadora.entity.CarEntity;
import com.github.joseprandj.SpringBootExpert_Locadora.exception.EntityNotFoundException;
import com.github.joseprandj.SpringBootExpert_Locadora.service.CarService;
import org.json.JSONObject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(CarController.class)
public class CarControllerTest {

    @Autowired
    MockMvc mvc;

    @MockitoBean
    CarService carService;

    @Test
    @DisplayName("Deve salvar um carro")
    public void mustSaveCar() throws Exception {
        CarEntity car = new CarEntity("Civic", 150, 2027);
        car.setId(1L);

        when(carService.save(Mockito.any())).thenReturn(car);

        JSONObject json = new JSONObject();
        json.put("model", "Civic");
        json.put("diaryValue", 150);
        json.put("carYear", 2027);

        ResultActions request = mvc.perform(
            MockMvcRequestBuilders
                    .post("/cars")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(json.toString())
        );

        request
            .andExpect(status().isCreated())
            .andExpect(MockMvcResultMatchers.jsonPath("$.id" ).value(1))
            .andExpect(MockMvcResultMatchers.jsonPath("$.model" ).value("Civic"));
    }

    @Test
    @DisplayName("Deve retornar erro ao salvar um carro com valor <= 0")
    public void mustReturnUnprocessableExceptionToSaveCar() throws Exception {
        when(carService.save(Mockito.any())).thenThrow(IllegalArgumentException.class);

        JSONObject json = new JSONObject();
        json.put("model", "Civic");
        json.put("diaryValue", 0);
        json.put("carYear", 2027);

        ResultActions request = mvc.perform(
            MockMvcRequestBuilders
                .post("/cars")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json.toString())
        );

        request.andExpect(status().isUnprocessableEntity());
    }

    @Test
    @DisplayName("Deve retornar dados de um carro")
    public void mustReturnDataCar() throws Exception {
        CarEntity car = new CarEntity("Civic", 150, 2027);
        car.setId(1L);

        when(carService.getById(Mockito.any())).thenReturn(car);

        mvc
            .perform(MockMvcRequestBuilders.get("/cars/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(1))
            .andExpect(jsonPath("$.model").value("Civic"))
            .andExpect(jsonPath("$.diaryValue").value(150))
            .andExpect(jsonPath("$.carYear").value(2027));

    }

    @Test
    @DisplayName("Deve retornar erro de não encontrado ao busca um carro inexistente")
    public void mustReturnNotFoundWhenGetCarDoesNotExist() throws Exception {
        CarEntity car = new CarEntity(1L,"Civic", 150, 2027);

        when(carService.getById(Mockito.any())).thenThrow(EntityNotFoundException.class);

        mvc
            .perform(MockMvcRequestBuilders.get("/cars/1"))
            .andExpect(status().isNotFound());

    }

    @Test
    @DisplayName("Deve listar todos os carros")
    public void mustReturnAllCars() throws Exception {
        List<CarEntity> listCars = List.of(
            new CarEntity(1L, "Argo", 150, 2025),
            new CarEntity(2L, "Celta", 80, 2015)
        );

        when(carService.getAllCars()).thenReturn(listCars);

        mvc
            .perform(MockMvcRequestBuilders.get("/cars"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].model").value("Argo"))
            .andExpect(jsonPath("$[1].model").value("Celta"));

    }

    @Test
    @DisplayName("Deve atualizar um carro")
    public void mustUpdateDataCar() throws Exception {
        CarEntity car = new CarEntity(1L,"Civic", 150, 2027);

        JSONObject json = new JSONObject();
        json.put("model", "Civic");
        json.put("diaryValue", 150);
        json.put("carYear", 2027);

        when(carService.update(Mockito.any(), Mockito.any())).thenReturn(car);

        mvc
            .perform(MockMvcRequestBuilders
                .put("/cars/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json.toString())
            )
            .andExpect(status().isNoContent());

    }

    @Test
    @DisplayName("Deve retornar erro de não encontrado ao busca um carro inexistente")
    public void mustReturnNotFoundWhenGetAllCarDoesNotExist() throws Exception {
        JSONObject json = new JSONObject();
        json.put("model", "Civic");
        json.put("diaryValue", 150);
        json.put("carYear", 2027);

        when(carService.update(Mockito.any(), Mockito.any())).thenThrow(EntityNotFoundException.class);

        mvc
            .perform(MockMvcRequestBuilders
                .put("/cars/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json.toString())
            )
            .andExpect(status().isNotFound());

    }

    @Test
    @DisplayName("Deve excluir um carro")
    public void mustDeleteCarWithId() throws Exception {
        Mockito.doNothing().when(carService).delete(Mockito.any());

        mvc
            .perform(MockMvcRequestBuilders.delete("/cars/1"))
            .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("Deve retornar erro ao excluir um carro inexistente")
    public void mustReturnNotFoundWhenDeleteCarDoesNotExist() throws Exception {
        Mockito.doThrow(EntityNotFoundException.class).when(carService).delete(Mockito.any());

        mvc
            .perform(MockMvcRequestBuilders.delete("/cars/1"))
            .andExpect(status().isNotFound());
    }
}
