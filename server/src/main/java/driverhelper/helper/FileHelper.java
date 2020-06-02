package driverhelper.helper;

import driverhelper.model.response.GarageSettings;
import driverhelper.model.response.CarSettings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

import static driverhelper.constants.Constants.SETTINGS_PROPERTIES_PATH_READ;
import static driverhelper.constants.Constants.SETTINGS_PROPERTIES_PATH_WRITE;

@Component
public class FileHelper {

    public static String GARAGE_WIDTH = "garage_width";
    public static String GARAGE_HEIGHT = "garage_height";
    public static String FRONT_SENSOR = "front_sensor";
    public static String LEFT_ANGLE_SENSOR = "left_angle_sensor";
    public static String RIGHT_ANGLE_SENSOR = "right_angle_sensor";
    public static List<String> CARS = Arrays.asList("car1", "car2", "car3", "car4", "car5", "car6", "car7", "car8", "car9", "car10");
    public static String LEFT_SENSOR = "left_sensor";
    private static String DELIMITER = ":";
    private static final Logger LOGGER = LoggerFactory.getLogger(FileHelper.class);
    private static Properties propsBuff;
    private static boolean shouldBuffReload = false;

    public void setGarageSettings(GarageSettings garageSettings) {
        Properties props = getPropsBuff();
        props.put(GARAGE_WIDTH, String.valueOf(garageSettings.getGarageWidth()));
        props.put(GARAGE_HEIGHT, String.valueOf(garageSettings.getGarageHeight()));
        props.put(FRONT_SENSOR, String.valueOf(garageSettings.getFrontSensor()));
        props.put(LEFT_ANGLE_SENSOR, String.valueOf(garageSettings.getLeftAngleSensor()));
        props.put(RIGHT_ANGLE_SENSOR, String.valueOf(garageSettings.getRightAngleSensor()));
        props.put(LEFT_SENSOR, String.valueOf(garageSettings.getLeftSensor()));
        rewriteFile(props);
        LOGGER.info("Rewriting settings property file");
    }

    public void setPropertyValue(String propertyName, String value) {
        Properties props = getPropsBuff();
        props.put(propertyName, value);
        rewriteFile(props);
        LOGGER.info("Rewriting settings property file");
    }

    public Optional<CarSettings> getCarById(int id) {
        Properties props = getPropsBuff();
        String property = props.getProperty(CARS.get(id - 1));
        if (property.equals("")) {
            return Optional.empty();
        }
        List<String> carProps = splitComplexProperty(property);
        return Optional.of(CarSettings.builder()
                .id(Long.valueOf(carProps.get(0)))
                .brand(carProps.get(1))
                .model(carProps.get(2))
                .imageSrc(carProps.get(3))
                .build());
    }

    public String getPropValues(String propertyName) {
        Properties props = getPropsBuff();
        return props.getProperty(propertyName);
    }

    public double getDoublePropValues(String propertyName) {
        Properties props = getPropsBuff();
        return Double.parseDouble(props.getProperty(propertyName));
    }

    public GarageSettings getSettingsPropValues() {
        return GarageSettings.builder()
                .garageWidth(getDoublePropValues(GARAGE_WIDTH))
                .garageHeight(getDoublePropValues(GARAGE_HEIGHT))
                .frontSensor(getDoublePropValues(FRONT_SENSOR))
                .leftAngleSensor(getDoublePropValues(LEFT_ANGLE_SENSOR))
                .rightAngleSensor(getDoublePropValues(RIGHT_ANGLE_SENSOR))
                .leftSensor(getDoublePropValues(LEFT_SENSOR))
                .build();
    }

    private List<String> splitComplexProperty(String property) {
        String[] split = property.split(DELIMITER);
        return Arrays.asList(split);
    }

    private void rewriteFile(Properties props) {
        FileOutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(SETTINGS_PROPERTIES_PATH_WRITE);
            props.store(outputStream, "Settings for driver-helper application");
        } catch (IOException e) {
            LOGGER.error("Error while writing to settings.property file");
        }
        shouldBuffReload = true;
    }

    private Properties getPropsBuff() {
        try {
            if (propsBuff == null || shouldBuffReload) {
                propsBuff = new Properties();
                propsBuff.load(new FileInputStream(SETTINGS_PROPERTIES_PATH_READ));
                shouldBuffReload = false;
            }
        } catch (IOException e) {
            LOGGER.error("Error while reading settings.property file");
        }
        return propsBuff;
    }
}
