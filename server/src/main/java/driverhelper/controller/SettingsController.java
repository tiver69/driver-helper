package driverhelper.controller;

import driverhelper.helper.FileHelper;
import driverhelper.model.response.CarSettings;
import driverhelper.model.response.GarageSettings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

@RestController
@CrossOrigin
@RequestMapping("/api/settings")
public class SettingsController {

    private static final Logger LOGGER = LoggerFactory.getLogger(SettingsController.class);

    @GetMapping
    public ResponseEntity<?> setUpActiveCarAndGetGarageSettings(@RequestParam("carId") Integer carId) {
        GarageSettings response = FileHelper.getSettingsPropValues();
        LOGGER.info("Setting car#"+carId+" as active and sending current settings" + response);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PatchMapping
    public ResponseEntity<?> patchGarageSettings(@RequestBody GarageSettings garageSettings) {
        LOGGER.info("Receive new settings" + garageSettings.toString());
        FileHelper.setGarageSettings(garageSettings);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/cars")
    public ResponseEntity<?> getAllCars() {
        LOGGER.debug("Request for all cars from data base");
        List<CarSettings> response = new ArrayList<>();
        IntStream.rangeClosed(1, 10).forEach(i -> {
            FileHelper.getCarById(i).ifPresent(response::add);
        });
        response.forEach(item -> item.setModel(item.getModel().toUpperCase()));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
