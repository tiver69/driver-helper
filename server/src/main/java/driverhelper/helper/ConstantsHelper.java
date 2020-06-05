package driverhelper.helper;

import driverhelper.model.response.CarSettings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Optional;

import static driverhelper.constants.Constants.IMAGE_BASE_FORMAT;
import static driverhelper.constants.Constants.IMAGE_BASE_PATH;

@Service
public class ConstantsHelper {

    private String imagePath = ".\\src\\main\\resources\\aveo.png";
    private CarSettings currentCar = new CarSettings();
    private int currentCarId = 1;

    private static final Logger LOGGER = LoggerFactory.getLogger(ConstantsHelper.class);

    @Autowired
    private FileHelper fileHelper;

    public void setCurrentCarId(int carId) {
        currentCarId = carId;
        reloadCarSettings();
        reloadImagePath();
    }

    public BufferedImage getCurrentCarImage() {
        String imagePath = getCurrentCarImagePath();
        try {
            return ImageIO.read(new File(imagePath));
        } catch (IOException e) {
            LOGGER.error("Error while reading car#" + currentCarId + " image from file system");
            throw new IllegalArgumentException("Error while reading car#" + currentCarId + " image from file system");
        }
    }

    public CarSettings getCurrentCarSettings() {
        return CarSettings.builder()
                .id(currentCar.getId())
                .brand(currentCar.getBrand())
                .model(currentCar.getModel())
                .imageFileName(currentCar.getImageFileName())
                .build();
    }

    private String getCurrentCarImagePath() {
        if (imagePath == null) reloadImagePath();
        return imagePath;
    }

    private void reloadCarSettings() {
        Optional<CarSettings> car = fileHelper.getCarById(currentCarId);
        car.ifPresentOrElse(carSettings -> currentCar = carSettings, () -> {
            LOGGER.error("Error while retrieving car#" + currentCarId + " from settings file");
//            throw new IllegalArgumentException("Error while retrieving car#" + currentCarId + " from settings file");
        });
    }

    private void reloadImagePath() {
        imagePath = IMAGE_BASE_PATH + currentCar.getImageFileName() + IMAGE_BASE_FORMAT;
    }
}
