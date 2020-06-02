package driverhelper.helper.v2;

import driverhelper.controller.SettingsController;
import driverhelper.model.Coordinates;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.MathContext;

import static driverhelper.constants.GarageConstants.*;

@Component
public class AngleSensorHelperV2 extends driverhelper.helper.AngleSensorHelper {

    private static final Logger LOGGER = LoggerFactory.getLogger(SettingsController.class);
    private static final MathContext ROUNDED = new MathContext(10);
    private BigDecimal xb = BigDecimal.valueOf(WIDTH / -2);
    private BigDecimal yb = BigDecimal.valueOf(HEIGHT - LEFT_ANGLE_SENSOR_HEIGHT_POSITION);
    private BigDecimal xc = BigDecimal.valueOf(WIDTH / 2);
    private BigDecimal yc = BigDecimal.valueOf(HEIGHT - RIGHT_ANGLE_SENSOR_HEIGHT_POSITION);

    /**
     * @return (360 - angle) if left sensor data is lesser than right,
     * and angle if left sensor data is bigger than right
     */
    public double getAngle(double leftAngleSensorData, double rightAngleSensorData) {
        Coordinates leftSensor = getLeftSensorCoordinates(leftAngleSensorData);
        Coordinates rightSensor = getRightSensorCoordinates(rightAngleSensorData);
        Coordinates zPoint = getZ(leftSensor, rightSensor);
        double angleCosine = countAngleCosine(rightSensor, zPoint);
        return leftSensor.getY() > rightSensor.getY() ?
                Math.toRadians(360 - Math.toDegrees(angleCosine)) :
                angleCosine;
    }

    /**
     * y = -√3x + yb + √3x_b
     */
    protected Coordinates getLeftSensorCoordinates(double distance) {
        BigDecimal resultX = xb.add(BigDecimal.valueOf(distance / 2));
        BigDecimal resultY = yb.add(BigDecimal.valueOf(Math.sqrt(3) * distance / -2));
        return new Coordinates(resultX.doubleValue(), resultY.doubleValue());
    }

    /**
     * y = √3x + yc - √3xc
     */
    protected Coordinates getRightSensorCoordinates(double distance) {
        BigDecimal resultX = xc.subtract(BigDecimal.valueOf(distance / 2));
        BigDecimal resultY = yc.add(BigDecimal.valueOf(Math.sqrt(3) * distance / -2));
        return new Coordinates(resultX.doubleValue(), resultY.doubleValue());
    }

    /**
     * Gets coordinates of intersection with X = xc
     * Y = (Yb-Ya)/(Xb-Xa)*X - (Yb-Ya)/(Xb-Xa)*Xa + Ya
     */
    protected Coordinates getZ(Coordinates b, Coordinates c) {
        BigDecimal kCoefficient = BigDecimal.valueOf(c.getY() - b.getY()).divide(BigDecimal.valueOf(c.getX() - b.getX()), ROUNDED);
        BigDecimal bCoefficient = BigDecimal.valueOf(c.getX() * b.getY() - b.getX() * c.getY()).divide(
                BigDecimal.valueOf(c.getX() - b.getX()), ROUNDED);
        return new Coordinates(xc.doubleValue(), kCoefficient.multiply(xc).add(bCoefficient).doubleValue());
    }

    protected double countAngleCosine(Coordinates c, Coordinates z) {
        BigDecimal hypotenuse = getDistanceBetweenTwoCoordinates1(c, z);
        BigDecimal cathetus = xc.subtract(BigDecimal.valueOf(c.getX()));
        double cosine = cathetus.divide(hypotenuse, ROUNDED).doubleValue();
        return Math.acos(cosine);
    }

    /**
     * @return result^2 = (Xb-Xa)^2 + (Yb-Ya)^2
     */
    protected BigDecimal getDistanceBetweenTwoCoordinates1(Coordinates pointOne, Coordinates pointTwo) {
        BigDecimal sum1 = BigDecimal.valueOf(pointTwo.getX()).subtract(BigDecimal.valueOf(pointOne.getX())).pow(2);
        BigDecimal sum2 = BigDecimal.valueOf(pointTwo.getY()).subtract(BigDecimal.valueOf(pointOne.getY())).pow(2);
        return sum1.add(sum2).sqrt(ROUNDED);
    }
}
