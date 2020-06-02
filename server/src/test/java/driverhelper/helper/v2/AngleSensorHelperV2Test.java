package driverhelper.helper.v2;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AngleSensorHelperV2Test {

    private static AngleSensorHelperV2 angleSensorHelperV2 = new AngleSensorHelperV2();

    @ParameterizedTest
    @MethodSource("getAngleParams")
    public void testGetAngle(double leftSensorData, double rightSensorData, double expectedResult) {
        double result = angleSensorHelperV2.getAngle(leftSensorData, rightSensorData);
        assertEquals(result, expectedResult);
    }

    private static Stream<Arguments> getAngleParams() {
        return Stream.of(
                Arguments.of(351.71, 351.41, 0.0),
                Arguments.of(423.17, 438.58, 0.3334731723223064),
                Arguments.of(399.37, 371.65, 5.949712134857281));
    }
}