package driverhelper.controller;

import driverhelper.model.response.CarSettings;
import driverhelper.model.response.GarageSettings;
import driverhelper.service.SettingsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/settings")
public class SettingsController {

    private static final Logger LOGGER = LoggerFactory.getLogger(SettingsController.class);

    @Autowired
    SettingsService settingsService;

    @PostMapping
    public ResponseEntity<?> setUpActiveCar(@RequestParam("carId") Integer carId) {
        settingsService.setUpActiveCar(carId);
        LOGGER.info("Setting car#" + carId + " as active");
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> getGarageSettings() {
        GarageSettings response = settingsService.getGarageSettings();
        LOGGER.info("Sending current settings: " + response);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PatchMapping
    public ResponseEntity<?> patchGarageSettings(@RequestBody GarageSettings garageSettings) {
        LOGGER.info("Receive new settings" + garageSettings.toString());
        settingsService.patchGarageSettings(garageSettings);
        GarageSettings response = settingsService.getGarageSettings();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/cars")
    public ResponseEntity<?> getAllCars() {
        LOGGER.debug("Request for all cars from data base");
        List<CarSettings> response = settingsService.getAllCars();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
