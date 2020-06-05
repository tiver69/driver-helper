package driverhelper.service;

import driverhelper.constants.Constants;
import driverhelper.constants.TestDataArray;
import driverhelper.helper.ConstantsHelper;
import driverhelper.helper.ImageHelper;
import driverhelper.helper.v2.AngleSensorHelperV2;
import driverhelper.model.Coordinates;
import driverhelper.model.Dimensions;
import driverhelper.model.DimensionsEnum;
import driverhelper.model.response.Data;
import driverhelper.model.response.SensorNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Optional;

import static driverhelper.constants.GarageConstants.FRONT_SENSOR_WIDTH_POSITION;
import static driverhelper.constants.GarageConstants.LEFT_SIDE_SENSOR_HEIGHT_POSITION;

@Service
public class DataService {

    private BufferedImage carImage;
    private static final Logger LOGGER = LoggerFactory.getLogger(DataService.class);

    @Autowired
    private AngleSensorHelperV2 angleSensorHelper;
    @Autowired
    private ImageHelper imageHelper;
    @Autowired
    private ConstantsHelper constantsHelper;

    public Data getCurrentDataPack() {
        SensorNode currentSensorNode = TestDataArray.sensorData.get(TestDataArray.getCurrentSensorDataStep());
        carImage = constantsHelper.getCurrentCarImage();

        double currentAngle = angleSensorHelper.getAngle(currentSensorNode.getLeftSideAngle(), currentSensorNode.getRightSideAngle());
        Dimensions rotatedDimensions = imageHelper.getDimensionsAfterRotation(carImage, currentAngle);
        Optional<Coordinates> rotatedPosition;
        if (currentSensorNode.getFront() > LEFT_SIDE_SENSOR_HEIGHT_POSITION || currentSensorNode.getLeftSide() > FRONT_SENSOR_WIDTH_POSITION) {
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
