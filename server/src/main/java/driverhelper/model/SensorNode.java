package driverhelper.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SensorNode {
    private int leftSide;
    private int leftSideAngle;
    private int rightSide;
    private int rightSideAngle;
    private int front;
}
