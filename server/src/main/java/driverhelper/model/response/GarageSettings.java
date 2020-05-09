package driverhelper.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GarageSettings {
    private double garageWidth;
    private double  garageHeight;
    private double frontSensor;
    private double leftAngleSensor;
    private double rightAngleSensor;
    private double leftSensor;
}
