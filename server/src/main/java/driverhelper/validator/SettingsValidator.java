package driverhelper.validator;

import driverhelper.helper.FileHelper;
import driverhelper.model.response.GarageSettings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.function.Predicate;

@Component
public class SettingsValidator {

    private static final Logger LOGGER = LoggerFactory.getLogger(SettingsValidator.class);

    public void validateActiveCarNumber(Integer carId) {
        if (carId > FileHelper.MAX_CAR_SETTINGS_AVAILABLE || carId <= 0)
            throw new IllegalArgumentException("No car #" + carId + " found in settings file");
    }

    public void validateGarageSettings(GarageSettings garageSettings) {
        executeCheck(garageSettings.getGarageWidth(), width -> width < 200, "Width is less than 200");
        executeCheck(garageSettings.getGarageHeight(), height -> height < 500, "Height is less than 200");
        executeCheck(garageSettings.getFrontSensor(), frontSensor ->
                        frontSensor >= garageSettings.getGarageWidth() || frontSensor <= 0,
                "Front sensor is out of garage width");
        executeCheck(garageSettings.getLeftAngleSensor(), leftAngleSensor ->
                leftAngleSensor <= 0, "Left angle sensor is out of garage width");
        executeCheck(garageSettings.getLeftAngleSensor(), leftAngleSensor ->
                garageSettings.getLeftSensor() <= leftAngleSensor, "Left angle sensor is lower than left sensor");
        executeCheck(garageSettings.getRightAngleSensor(), rightAngleSensor ->
                        rightAngleSensor <= 0 || rightAngleSensor >= garageSettings.getGarageHeight(),
                "Right angle sensor is out of garage height");
        executeCheck(garageSettings.getLeftSensor(), leftSensor ->
                leftSensor >= garageSettings.getGarageHeight(), "Left sensor is out of garage height");
    }

    private void executeCheck(Double testInstance, Predicate<Double> errorCondition, String errorMessage) {
        if (errorCondition.test(testInstance)) {
            LOGGER.error(errorMessage);
            throw new IllegalArgumentException(errorMessage);
        }
    }
}
