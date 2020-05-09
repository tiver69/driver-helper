package driverhelper.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@lombok.Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Data {
    //todo to be removed
    private int sensorNodeStep;

    private double degree;
    private double rotatedWidth;
    private double rotatedHeight;
    private double shiftedWidth;
    private double shiftedHeight;
    private double positionX;
    private double positionY;
}
