package service;

import com.github.joseprandj.SpringBootExpert_Locadora.entity.CarEntity;
import com.github.joseprandj.SpringBootExpert_Locadora.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CarService {

    @Autowired
    CarRepository repository;

    public CarEntity save(CarEntity car) {
        if (car.getDiaryValue() <= 0) throw new IllegalArgumentException("Price the diary invalid. It needs to be bigger 0");
        return repository.save(car);
    }

    public CarEntity update(Long id, CarEntity car) {
        CarEntity car = repository.findById(id).orElseThrow(() -> new EntityNotFoundException());
        return repository.save(car);
    }
}
