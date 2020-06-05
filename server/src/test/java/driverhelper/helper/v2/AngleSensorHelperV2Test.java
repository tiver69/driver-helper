package driverhelper.helper.v2;

import driverhelper.helper.FileHelper;
import driverhelper.model.response.GarageSettings;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class AngleSensorHelperV2Test {

    @Mock
    private FileHelper fileHelperMock;

    @InjectMocks
    private static AngleSensorHelperV2 angleSensorHelperV2 = new AngleSensorHelperV2();

    @ParameterizedTest
    @MethodSource("getAngleParams")
    public void testGetAngle(double leftSensorData, double rightSensorData, double expectedResult) {
        when(fileHelperMock.getCurrentGarageSettings()).thenReturn(GarageSettings.builder()
                .garageWidth(300).garageHeight(600)
                .leftAngleSensor(300).rightAngleSensor(300)
                .frontSensor(150).leftSensor(530)
                .build());
        double result = angleSensorHelperV2.getAngle(leftSensorData, rightSensorData);
        assertEquals(result, expectedResult);
    }

    private static Stream<Arguments> getAngleParams() {
        return Stream.of(
                Arguments.of(351.71, 351.41, 0.005038854405636683),
                Arguments.of(423.17, 438.58, 6.1815655756045045),
                Arguments.of(399.37, 371.65, 0.27369636394800484));
    }
}