package driverhelper.controller;

import driverhelper.constants.TestDataArray;
import driverhelper.model.Data;
import driverhelper.service.DataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/api/data")
public class DataController {

    private static final Logger LOGGER = LoggerFactory.getLogger(DataController.class);

    @Autowired
    private DataService dataService;

    @GetMapping
    public ResponseEntity<?> getCurrentDataPack(
            /*@RequestParam("trainId") Long trainId,
            @RequestParam("departureDate") String departureDate,
            @RequestParam("coachNumber") Integer coachNumber*/) {
        LOGGER.debug("Request for next data-pack");
        Data response = dataService.getCurrentDataPack();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/reset")
    public ResponseEntity<?> resetSensorDataToFirstElement() {
        LOGGER.debug("Reset test data-pack to first element");
        TestDataArray.resetSensorDataStep();
        Data response = new Data();
        response.setSensorNodeStep(TestDataArray.getCurrentSensorDataStep());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
