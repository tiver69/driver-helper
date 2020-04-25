package driverhelper.helper;

import driverhelper.model.Coordinates;

import java.math.BigDecimal;
import java.math.MathContext;

public class AngleSensorHelper {

    /**
     * @return (360 - angle) if left sensor data is lesser than right,
     * and angle if left sensor data is bigger than right
     */
    public static double getAngle(int leftAngleSensorData, int rightAngleSensorData) {
        Coordinates leftSensor = getLeftSensorCoordinates(leftAngleSensorData);
        Coordinates rightSensor = getRightSensorCoordinates(rightAngleSensorData);
        double cathet = getDistanceBetweenTwoCoordinates(rightSensor, new Coordinates(150, rightSensor.getY()));
        double hypotenuse = getDistanceBetweenTwoCoordinates(rightSensor, getTochechka(leftSensor, rightSensor));
        double cosine = new BigDecimal(cathet).divide(new BigDecimal(hypotenuse), new MathContext(10)).doubleValue();
        if (leftSensor.getY() > rightSensor.getY()) {
            return Math.toRadians(360 - Math.toDegrees(Math.acos(cosine)));
        } else {
            return Math.acos(cosine);
        }
    }

    /**
     * y = -2x
     */
    protected static Coordinates getLeftSensorCoordinates(int distance) {
        BigDecimal resultX = getRightX(distance);
        BigDecimal resultY = resultX.add(resultX);
        return new Coordinates(resultX.doubleValue(), resultY.doubleValue());
    }

    /**
     * y = 2x
     */
    protected static Coordinates getRightSensorCoordinates(int distance) {
        BigDecimal resultX = getLeftX(distance);
        BigDecimal resultY = resultX.multiply(new BigDecimal(-2));
        return new Coordinates(resultX.doubleValue(), resultY.doubleValue());
    }

    /**
     * x = -150, y = 300
     * 5x^2 + 1500x + 150^2 + 300^2 = distance^2
     */
    protected static BigDecimal getRightX(int distance) {
        BigDecimal b = new BigDecimal(1500);
        BigDecimal add1 = new BigDecimal(150);
        BigDecimal add2 = new BigDecimal(300);
        BigDecimal distanceDecimal = new BigDecimal(distance);
        BigDecimal discriminant = b.pow(2).subtract(add1.pow(2).add(add2.pow(2)).subtract(distanceDecimal.pow(2)).multiply(new BigDecimal(20)));
        if (discriminant.compareTo(BigDecimal.ZERO) > 0) {
            BigDecimal divide = discriminant.sqrt(new MathContext(10)).divide(BigDecimal.TEN, new MathContext(10));
            BigDecimal firstVal = divide.add(new BigDecimal(150));
            BigDecimal secondVal = new BigDecimal(150).subtract(divide);
//            System.out.printf("First: %s, \nSecond: %s", firstVal, secondVal);
            return firstVal.compareTo(secondVal) < 0 ? firstVal : secondVal;
        }
        throw new IllegalArgumentException("Square has lesser than two results");
    }

    /**
     * x = 150, y = 300
     * 5x^2 - 1500x + 150^2 + 300^2 = distance^2
     */
    protected static BigDecimal getLeftX(int distance) {
        BigDecimal b = new BigDecimal(1500);
        BigDecimal add1 = new BigDecimal(150);
        BigDecimal add2 = new BigDecimal(300);
        BigDecimal distanceDecimal = new BigDecimal(distance);
        BigDecimal discriminant = b.pow(2).subtract(add1.pow(2).add(add2.pow(2)).subtract(distanceDecimal.pow(2)).multiply(new BigDecimal(20)));
        if (discriminant.compareTo(BigDecimal.ZERO) > 0) {
            BigDecimal divide = discriminant.sqrt(new MathContext(10)).divide(BigDecimal.TEN, new MathContext(10));
            BigDecimal firstVal = divide.subtract(new BigDecimal(150));
            BigDecimal secondVal = new BigDecimal(-150).subtract(divide);
//            System.out.printf("First: %s, \nSecond: %s", firstVal, secondVal);
            return firstVal.compareTo(secondVal) > 0 ? firstVal : secondVal;
        }
        throw new IllegalArgumentException("Square has lesser than two results");
    }

    /**
     * Gets coordinates of intersection with X = 150
     * Y = (Yb-Ya)/(Xb-Xa)*X - (Yb-Ya)/(Xb-Xa)*Xa + Ya
     */
    protected static Coordinates getTochechka(Coordinates leftSensor, Coordinates rightSensor) {
        BigDecimal drob = BigDecimal.valueOf(rightSensor.getY()).subtract(BigDecimal.valueOf(leftSensor.getY()))
                .divide(BigDecimal.valueOf(rightSensor.getX()).subtract(BigDecimal.valueOf(leftSensor.getX())), new MathContext(10));
        BigDecimal y = drob.multiply(new BigDecimal(150))
                .subtract(drob.multiply(BigDecimal.valueOf(leftSensor.getX())))
                .add(BigDecimal.valueOf(leftSensor.getY()));
        return new Coordinates(150, y.doubleValue());
    }

    /**
     * @return result^2 = (Xb-Xa)^2 + (Yb-Ya)^2
     */
    protected static double getDistanceBetweenTwoCoordinates(Coordinates pointOne, Coordinates pointTwo) {
        BigDecimal sum1 = BigDecimal.valueOf(pointTwo.getX()).subtract(BigDecimal.valueOf(pointOne.getX())).pow(2);
        BigDecimal sum2 = BigDecimal.valueOf(pointTwo.getY()).subtract(BigDecimal.valueOf(pointOne.getY())).pow(2);
        BigDecimal result = sum1.add(sum2).sqrt(new MathContext(10));
        return result.doubleValue();
    }
}
