package driverhelper.service;

import driverhelper.constants.Constants;
import driverhelper.helper.ConstantsHelper;
import driverhelper.helper.FileHelper;
import driverhelper.model.Dimensions;
import driverhelper.model.response.CarSettings;
import driverhelper.model.response.GarageSettings;
import driverhelper.validator.SettingsValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.image.BufferedImage;
import java.util.List;

@Service
public class SettingsService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SettingsService.class);

    @Autowired
    private SettingsValidator settingsValidator;

    @Autowired
    private ConstantsHelper constantsHelper;

    @Autowired
    private FileHelper fileHelper;

    public GarageSettings getGarageSettings() {
        return fileHelper.getSettingsPropValues();
    }

    public void patchGarageSettings(GarageSettings garageSettings) {
        settingsValidator.validateGarageSettings(garageSettings);
        fileHelper.setGarageSettings(garageSettings);
    }

    public CarSettings setUpActiveCar(Integer carId) {
        settingsValidator.validateActiveCarNumber(carId);
        constantsHelper.setCurrentCarId(carId);
        return getCarImageDimensions(constantsHelper.getCurrentCarSettings());
    }

    public List<CarSettings> getAllCars() {
        List<CarSettings> carList = fileHelper.getAllAvailableCarSettings();
        carList.forEach(item -> item.setModel(item.getModel().toUpperCase()));
        return carList;
    }

    private CarSettings getCarImageDimensions(CarSettings car) {
        BufferedImage carImage = constantsHelper.getCurrentCarImage();
        car.setImageDimensions(Dimensions.builder().width(carImage.getWidth()).height(carImage.getHeight()).build());
        return car;
    }
}
