package driverhelper.validator;

import driverhelper.model.response.GarageSettings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class SettingsValidator {

    private static final Logger LOGGER = LoggerFactory.getLogger(SettingsValidator.class);

    //todo: refactor
    public void validateGarageSettings(GarageSettings garageSettings) {
        if (garageSettings.getGarageWidth() < 200) throw new IllegalArgumentException("Width is less than 200");
        if (garageSettings.getGarageHeight() < 500) throw new IllegalArgumentException("Height is less than 500");
        if (garageSettings.getFrontSensor() >= garageSettings.getGarageWidth() || garageSettings.getFrontSensor() <= 0)
            throw new IllegalArgumentException("Front sensor is out of garage width");
        if (garageSettings.getLeftAngleSensor() <= 0)
            throw new IllegalArgumentException("Left angle sensor is out of garage width");
        if (garageSettings.getLeftSensor() >= garageSettings.getLeftAngleSensor())
            throw new IllegalArgumentException("Left angle sensor is lower than left sensor");
        if (garageSettings.getRightAngleSensor() <= 0 || garageSettings.getRightAngleSensor() >= garageSettings.getGarageHeight())
            throw new IllegalArgumentException("Right angle sensor is out of garage height");
        if (garageSettings.getLeftSensor() >= garageSettings.getGarageHeight())
            throw new IllegalArgumentException("Left sensor is out of garage height");
    }

//    private void executeCheck(HashMap<String, String> causeObject, ValidationCondition condition) {
//        try {
//            condition.executeCheck();
//        } catch (DataNotFoundException ex) {
//            causeObject.put(ex.getKey(), ex.getMessage());
//        }
//    }
}
