package driverhelper.helper;

import driverhelper.model.response.GarageSettings;
import driverhelper.model.response.CarSettings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;
import java.util.stream.IntStream;

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
    public static String CURRENT_CAR_ID = "current_car_id";
    public static String TEST_DATA_ARRAY = "test_data_array";
    private static Properties propsBuff;
    public static int MAX_CAR_SETTINGS_AVAILABLE = 10;
    private static boolean shouldBuffReload = false;

    private static final String DELIMITER = ":";
    private static final Logger LOGGER = LoggerFactory.getLogger(FileHelper.class);

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

    public void setCurrentCarId(int carId) {
        setPropertyValue(CURRENT_CAR_ID, Integer.toString(carId));
    }

    public List<CarSettings> getAllAvailableCarSettings() {
        List<CarSettings> carList = new ArrayList<>();
        IntStream.rangeClosed(1, MAX_CAR_SETTINGS_AVAILABLE).forEach(i -> {
            getCarById(i).ifPresent(carList::add);
        });
        return carList;
    }

    public int getCurrentCarId() {
        return Integer.parseInt(getPropValues(CURRENT_CAR_ID));
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
                .imageFileName(carProps.get(3))
                .build());
    }

    public CarSettings getCurrentCar() {
        int currentCarId = getCurrentCarId();
        Optional<CarSettings> currentCar = getCarById(currentCarId);
        if (currentCar.isPresent())
            return currentCar.get();
        throw new IllegalArgumentException("Current car#" + currentCarId + " from settings is not present");
    }

    public GarageSettings getCurrentGarageSettings() {
        return GarageSettings.builder()
                .garageWidth(getDoublePropValues(GARAGE_WIDTH))
                .garageHeight(getDoublePropValues(GARAGE_HEIGHT))
                .frontSensor(getDoublePropValues(FRONT_SENSOR))
                .leftAngleSensor(getDoublePropValues(LEFT_ANGLE_SENSOR))
                .rightAngleSensor(getDoublePropValues(RIGHT_ANGLE_SENSOR))
                .leftSensor(getDoublePropValues(LEFT_SENSOR))
                .build();
    }

    public String getTestDataArrayFileName(){
        shouldBuffReload = true;
        return getPropValues(FileHelper.TEST_DATA_ARRAY);
    }

    public void setPropertyValue(String propertyName, String value) {
        Properties props = getPropsBuff();
        props.put(propertyName, value);
        rewriteFile(props);
        LOGGER.info("Rewriting settings property file");
    }

    public String getPropValues(String propertyName) {
        Properties props = getPropsBuff();
        return props.getProperty(propertyName);
    }

    public double getDoublePropValues(String propertyName) {
        Properties props = getPropsBuff();
        return Double.parseDouble(props.getProperty(propertyName));
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
