package driverhelper.model.response;

import driverhelper.model.Dimensions;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CarSettings {
    private Long id;
    private String brand;
    private String model;
    private Dimensions imageDimensions;
    private String imageFileName;
}
