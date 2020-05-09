package driverhelper.service;

import driverhelper.constants.TestDataArray;
import driverhelper.model.*;
import driverhelper.model.response.Data;
import driverhelper.model.response.SensorNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Optional;

import static driverhelper.constants.Constants.AVEO_IMAGE_PATH;
import static driverhelper.constants.GarageConstants.FRONT_SENSOR_WIDTH_POSITION;
import static driverhelper.constants.GarageConstants.LEFT_SIDE_SENSOR_HEIGHT_POSITION;
import static driverhelper.helper.AngleSensorHelper.getAngle;
import static driverhelper.helper.ImageHelper.*;

@Service
public class DataService {

    private BufferedImage CAR = ImageIO.read(new File(AVEO_IMAGE_PATH));
    private static final Logger LOGGER = LoggerFactory.getLogger(DataService.class);

    public DataService() throws IOException {
    }

    public Data getCurrentDataPack() {
        SensorNode currentSensorNode = TestDataArray.sensorData.get(TestDataArray.getCurrentSensorDataStep());

        double currentAngle = getAngle(currentSensorNode.getLeftSideAngle(), currentSensorNode.getRightSideAngle());
        Dimensions rotatedDimensions = getDimensionsAfterRotation(CAR, currentAngle);
        Optional<Coordinates> rotatedPosition;
        if (currentSensorNode.getFront() > LEFT_SIDE_SENSOR_HEIGHT_POSITION || currentSensorNode.getLeftSide() > FRONT_SENSOR_WIDTH_POSITION) {
            rotatedPosition = Optional.of(new Coordinates());
        } else
            rotatedPosition = getRotatedImagePositionOnCurrentSensorData(CAR, currentAngle, currentSensorNode.getLeftSide(), currentSensorNode.getFront());

        TestDataArray.nextSensorDataStep();
        return Data.builder()
                .sensorNodeStep(TestDataArray.getCurrentSensorDataStep())
                .degree(Math.toDegrees(currentAngle))
                .rotatedWidth(rotatedDimensions.getWidth())
                .rotatedHeight(rotatedDimensions.getHeight())
                .positionX(rotatedPosition.get().getX())
                .positionY(rotatedPosition.get().getY())
                .shiftedWidth(getRotatedImageShiftOnCurrentAngle(CAR, currentAngle, DimensionsEnum.WIDTH))
                .shiftedHeight(getRotatedImageShiftOnCurrentAngle(CAR, currentAngle, DimensionsEnum.HEIGHT))
                .build();
    }

    public SensorNode getCurrentSensorNodeDataPack() {
        return TestDataArray.sensorData.get(TestDataArray.getCurrentSensorDataStep());
    }
}
