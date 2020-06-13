package driverhelper.controller;

import driverhelper.constants.TestDataArray;
import driverhelper.helper.FileHelper;
import driverhelper.model.response.Data;
import driverhelper.model.response.GarageSettings;
import driverhelper.model.response.SensorNode;
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
    public ResponseEntity<?> getCurrentDataPack() {
        LOGGER.debug("Request for next data-pack");
        Data response = dataService.getCurrentDataPack();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/sensor-node")
    public ResponseEntity<?> getCurrentSensorNodeDataPack() {
        LOGGER.debug("Request for latest sensor node data");
        SensorNode response = dataService.getCurrentSensorNodeDataPack();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/reset") //todo: will be removed later
    public ResponseEntity<?> resetSensorDataToFirstElement() {
        LOGGER.debug("Reset test data-pack to first element");
        dataService.reloadTestDataArray();
        TestDataArray.resetSensorDataStep();
        Data response = new Data();
        response.setSensorNodeStep(TestDataArray.getCurrentSensorDataStep());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
