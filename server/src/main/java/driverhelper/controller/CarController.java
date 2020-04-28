package driverhelper.controller;

import driverhelper.model.Data;
import driverhelper.model.database.Car;
import driverhelper.repository.CarRepository;
import driverhelper.service.DataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/car")
public class CarController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CarController.class);

    @Autowired
    private CarRepository carRepository;

    @GetMapping("/all")
    public ResponseEntity<?> getAllCars() {
        LOGGER.debug("Request for all cars from data base");
        List<Car> response = carRepository.findAll();
        response.forEach(item -> item.setModel(item.getModel().toUpperCase()));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
