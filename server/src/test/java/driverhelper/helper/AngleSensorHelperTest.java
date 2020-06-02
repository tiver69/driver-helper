package driverhelper.helper;

import driverhelper.model.Coordinates;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigDecimal;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AngleSensorHelperTest {

    private static AngleSensorHelper angleSensorHelper = new AngleSensorHelper();

    @ParameterizedTest
    @MethodSource("getAngleParams")
    public void testGetAngle(int leftSensorData, int rightSensorData, double expectedResult) {
        AngleSensorHelper angleSensorHelper = new AngleSensorHelper();
        double result = angleSensorHelper.getAngle(leftSensorData, rightSensorData);
        assertEquals(result, expectedResult);
    }

    @ParameterizedTest
    @MethodSource("getDistanceBetweenTwoCoordinatesParams")
    public void testGetDistanceBetweenTwoCoordinates(Coordinates leftSensor, Coordinates rightSensor, double expectedResult) {
        double result = angleSensorHelper.getDistanceBetweenTwoCoordinates(leftSensor, rightSensor);
        assertEquals(result, expectedResult);
    }

    @ParameterizedTest
    @MethodSource("getTochechkaParams")
    public void testGetTochechka(Coordinates leftSensor, Coordinates rightSensor, Coordinates expectedResult) {
        Coordinates result = angleSensorHelper.getTochechka(leftSensor, rightSensor);
        assertEquals(result, expectedResult);
    }

    @ParameterizedTest
    @MethodSource("getRightSensorCoordinatesParams")
    public void testGetRightSensorCoordinates(int distance, Coordinates expectedResult) {
        Coordinates result = angleSensorHelper.getRightSensorCoordinates(distance);
        assertEquals(result, expectedResult);
    }

    @ParameterizedTest
    @MethodSource("getLeftSensorCoordinatesParams")
    public void testGetLeftSensorCoordinates(int distance, Coordinates expectedResult) {
        Coordinates result = angleSensorHelper.getLeftSensorCoordinates(distance);
        assertEquals(result, expectedResult);
    }

    @ParameterizedTest
    @MethodSource("getRightXParams")
    public void testGetRightX(int distance, double expectedResult) {
        BigDecimal result = angleSensorHelper.getRightX(distance);
        assertEquals(result.doubleValue(), expectedResult);
    }

    @ParameterizedTest
    @MethodSource("getLeftXParams")
    public void testGetLeftX(int distance, double expectedResult) {
        BigDecimal result = angleSensorHelper.getLeftX(distance);
        assertEquals(result.doubleValue(), expectedResult);
    }

    private static Stream<Arguments> getAngleParams() {
        return Stream.of(
                Arguments.of(450, 450, 0.0),
                Arguments.of(450, 400, 0.5090401546564025),
                Arguments.of(400, 450, 5.774145153138772));
    }

    private static Stream<Arguments> getDistanceBetweenTwoCoordinatesParams() {
        return Stream.of(
                Arguments.of(new Coordinates(-28.8854382, -57.7708764), new Coordinates(150.0, -57.7708764), 178.8854382),
                Arguments.of(new Coordinates(-51.246118, -102.492236), new Coordinates(150.0, -102.492236), 201.246118),
                Arguments.of(new Coordinates(-28.8854382, -57.7708764), new Coordinates(150.0, -157.6067012346419), 204.8589562));
    }

    private static Stream<Arguments> getTochechkaParams() {
        return Stream.of(
                Arguments.of(new Coordinates(-28.8854382, -57.7708764), new Coordinates(28.8854382, -57.7708764), new Coordinates(150.0, -57.7708764)),
                Arguments.of(new Coordinates(-51.246118, -102.492236), new Coordinates(51.246118, -102.492236), new Coordinates(150.0, -102.492236)),
                Arguments.of(new Coordinates(-28.8854382, -57.7708764), new Coordinates(51.246118, -102.492236), new Coordinates(150.0, -157.6067012346419)));
    }

    private static Stream<Arguments> getRightSensorCoordinatesParams() {
        return Stream.of(
                Arguments.of(400, new Coordinates(28.8854382, -57.7708764)),
                Arguments.of(450, new Coordinates(51.246118, -102.492236)));
    }

    private static Stream<Arguments> getLeftSensorCoordinatesParams() {
        return Stream.of(
                Arguments.of(400, new Coordinates(-28.8854382, -57.7708764)),
                Arguments.of(450, new Coordinates(-51.246118, -102.492236)));
    }

    private static Stream<Arguments> getRightXParams() {
        return Stream.of(
                Arguments.of(400, -28.8854382),
                Arguments.of(450, -51.246118));
    }

    private static Stream<Arguments> getLeftXParams() {
        return Stream.of(
                Arguments.of(400, 28.8854382),
                Arguments.of(450, 51.246118));
    }
}