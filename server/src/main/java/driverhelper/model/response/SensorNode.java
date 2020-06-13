package driverhelper.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SensorNode {
    private int leftSide;
    private int leftSideAngle;
    private int rightSide;
    private int rightSideAngle;
    private int front;
}
