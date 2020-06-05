package driverhelper.service;

import driverhelper.constants.TestDataArray;
import driverhelper.helper.FileHelper;
import driverhelper.helper.ImageHelper;
import driverhelper.helper.v2.AngleSensorHelperV2;
import driverhelper.model.Coordinates;
import driverhelper.model.Dimensions;
import driverhelper.model.DimensionsEnum;
import driverhelper.model.response.Data;
import driverhelper.model.response.GarageSettings;
import driverhelper.model.response.SensorNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.image.BufferedImage;
import java.util.Optional;

@Service
public class DataService {

    private static final Logger LOGGER = LoggerFactory.getLogger(DataService.class);

    @Autowired
    private AngleSensorHelperV2 angleSensorHelper;
    @Autowired
    private ImageHelper imageHelper;
    @Autowired
    private FileHelper fileHelper;

    public Data getCurrentDataPack() {
        SensorNode currentSensorNode = TestDataArray.sensorData.get(TestDataArray.getCurrentSensorDataStep());
        BufferedImage carImage = imageHelper.getCurrentCarImage();
        GarageSettings garageSettings = fileHelper.getCurrentGarageSettings();

        double currentAngle = angleSensorHelper.getAngle(currentSensorNode.getLeftSideAngle(), currentSensorNode.getRightSideAngle());
        Dimensions rotatedDimensions = imageHelper.getDimensionsAfterRotation(carImage, currentAngle);
        Optional<Coordinates> rotatedPosition;
        if (currentSensorNode.getFront() > garageSettings.getLeftSensor() || currentSensorNode.getLeftSide() >
                garageSettings.getFrontSensor()) {
            rotatedPosition = Optional.of(new Coordinates());
        } else
            rotatedPosition = imageHelper.getRotatedImagePositionOnCurrentSensorData(carImage, currentAngle, currentSensorNode.getLeftSide(), currentSensorNode.getFront());

        TestDataArray.nextSensorDataStep();
        return Data.builder()
                .sensorNodeStep(TestDataArray.getCurrentSensorDataStep())
                .degree(Math.toDegrees(currentAngle))
                .rotatedWidth(rotatedDimensions.getWidth())
                .rotatedHeight(rotatedDimensions.getHeight())
                .positionX(rotatedPosition.get().getX())
                .positionY(rotatedPosition.get().getY())
                .shiftedWidth(imageHelper.getRotatedImageShiftOnCurrentAngle(carImage, currentAngle, DimensionsEnum.WIDTH))
                .shiftedHeight(imageHelper.getRotatedImageShiftOnCurrentAngle(carImage, currentAngle, DimensionsEnum.HEIGHT))
                .build();
    }

    public SensorNode getCurrentSensorNodeDataPack() {
        return TestDataArray.sensorData.get(TestDataArray.getCurrentSensorDataStep());
    }
}
