package driverhelper.constants;

import driverhelper.model.response.SensorNode;
import driverhelper.service.SettingsService;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static driverhelper.constants.Constants.RESOURCES_BASE_PATH;

public final class TestDataArray {

    private static final Logger LOGGER = LoggerFactory.getLogger(TestDataArray.class);

    @Getter
    private static int currentSensorDataStep = 0;

    public static void nextSensorDataStep() {
        currentSensorDataStep++;
    }
    public static void resetSensorDataStep() {
        currentSensorDataStep = 0;
    }

    public static List<SensorNode> sensorData = Arrays.asList(
            new SensorNode(300, 410, 0, 400, 600),
            new SensorNode(300, 410, 0, 400, 592),
            new SensorNode(300, 410, 0, 400, 572),
            new SensorNode(300, 410, 0, 400, 551),
            new SensorNode(300, 410, 0, 400, 531),
            new SensorNode(94, 409, 0, 400, 511),
            new SensorNode(83, 409, 0, 400, 491),
            new SensorNode(69, 408, 0, 400, 470),
            new SensorNode(67, 407, 0, 400, 449),
            new SensorNode(61, 406, 0, 400, 428),
            new SensorNode(59, 405, 0, 400, 406),
            new SensorNode(56, 404, 0, 400, 386),
            new SensorNode(37, 403, 0, 400, 364),
            new SensorNode(48, 402, 0, 400, 343),
            new SensorNode(49, 401, 0, 400, 322),
            new SensorNode(48, 400, 0, 400, 301),
            new SensorNode(49, 400, 0, 400, 281),
            new SensorNode(48, 400, 0, 400, 261),
            new SensorNode(51, 400, 0, 400, 241),
            new SensorNode(51, 400, 0, 400, 220),
            new SensorNode(51, 400, 0, 400, 200),
            new SensorNode(50, 400, 0, 400, 180),
            new SensorNode(50, 400, 0, 400, 160),
            new SensorNode(50, 400, 0, 400, 140),
            new SensorNode(50, 400, 0, 400, 120),
            new SensorNode(51, 400, 0, 400, 100),
            new SensorNode(53, 400, 0, 400, 80),
            new SensorNode(60, 400, 0, 400, 60),
            new SensorNode(67, 400, 0, 400, 46),
            new SensorNode(300, 400, 0, 400, 20),
            new SensorNode(300, 400, 0, 400, 20));

    public static void reloadTestDataArray(String fileName) {
        sensorData = new ArrayList<>();
        LOGGER.info("Using test data array from "+ fileName);
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line = reader.readLine();
            while (line != null) {
                List<String> sensorNode = Arrays.asList(line.split(","));
                sensorData.add(
                        SensorNode.builder()
                                .leftSide(Integer.parseInt(sensorNode.get(0)))
                                .leftSideAngle(Integer.parseInt(sensorNode.get(1)))
                                .rightSide(Integer.parseInt(sensorNode.get(2)))
                                .rightSideAngle(Integer.parseInt(sensorNode.get(3)))
                                .front(Integer.parseInt(sensorNode.get(4)))
                                .build()
                );
                line = reader.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
