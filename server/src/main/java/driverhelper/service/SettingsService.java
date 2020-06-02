package driverhelper.service;

import driverhelper.helper.FileHelper;
import driverhelper.model.response.CarSettings;
import driverhelper.model.response.GarageSettings;
import driverhelper.validator.SettingsValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

@Service
public class SettingsService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SettingsService.class);

    @Autowired
    private SettingsValidator settingsValidator;

    @Autowired
    private FileHelper fileHelper;

    public GarageSettings getGarageSettings() {
        GarageSettings garageSettings = fileHelper.getSettingsPropValues();
        return garageSettings;
    }

    public void patchGarageSettings(GarageSettings garageSettings) {
        settingsValidator.validateGarageSettings(garageSettings);
        fileHelper.setGarageSettings(garageSettings);
    }

    public List<CarSettings> getAllCars() {
        List<CarSettings> carList = new ArrayList<>();
        /// TODO: create method in helper
        IntStream.rangeClosed(1, 10).forEach(i -> {
            fileHelper.getCarById(i).ifPresent(carList::add);
        });
        carList.forEach(item -> item.setModel(item.getModel().toUpperCase()));
        return carList;
    }
}
