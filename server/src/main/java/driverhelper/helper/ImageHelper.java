package driverhelper.helper;

import driverhelper.constants.GarageConstants;
import driverhelper.model.Coordinates;
import driverhelper.model.Dimensions;
import driverhelper.model.DimensionsEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static driverhelper.constants.GarageConstants.FRONT_SENSOR_WIDTH_POSITION;
import static driverhelper.constants.GarageConstants.LEFT_SIDE_SENSOR_HEIGHT_POSITION;

public class ImageHelper {

    private static final Logger LOGGER = LoggerFactory.getLogger(ImageHelper.class);

    public static double getRotatedImageShiftOnCurrentAngle(BufferedImage bi, double radsAlpha, DimensionsEnum dimension) {
        BigDecimal a = dimension.equals(DimensionsEnum.WIDTH) ? BigDecimal.valueOf(bi.getHeight()) : BigDecimal.valueOf(bi.getWidth());
        BigDecimal b = dimension.equals(DimensionsEnum.HEIGHT) ? BigDecimal.valueOf(bi.getHeight()) : BigDecimal.valueOf(bi.getWidth());
        BigDecimal d = b.pow(2).add(a.pow(2)).sqrt(new MathContext(10));
        double betta = Math.asin(a.divide(d, new MathContext(10)).doubleValue());
        double gamma = Math.toRadians(180) - 2 * betta;
        BigDecimal x = d.multiply(BigDecimal.valueOf(Math.sin(radsAlpha / 2)));
        BigDecimal y = d.multiply(BigDecimal.valueOf(Math.sin(gamma / 2 - radsAlpha / 2)));
        BigDecimal polyPerimeter = b.add(x).add(y).divide(BigDecimal.valueOf(2), new MathContext(10));
        BigDecimal s = polyPerimeter.multiply(polyPerimeter.subtract(b))
                .multiply(polyPerimeter.subtract(x))
                .multiply(polyPerimeter.subtract(y))
                .sqrt(new MathContext(10));

        BigDecimal h = s.multiply(BigDecimal.valueOf(2)).divide(b, new MathContext(10));
        return h.doubleValue();
    }

    public static Optional<Coordinates> getRotatedImagePositionOnCurrentSensorData(BufferedImage bi, double rads, double leftSideSensor, double frontSensor) {
        bi = rotateImage(bi, rads);
        List<Integer> xDistance = new ArrayList<>();
        for (int y = 0; y < bi.getHeight(); y++) {
            xDistance.add(countXDestination(bi, y));
        }
        List<Integer> yDistance = new ArrayList<>();
        for (int x = 0; x < bi.getWidth(); x++) {
            yDistance.add(countYDestination(bi, x));
        }

        int xStarting = GarageConstants.WIDTH < bi.getWidth() ? GarageConstants.WIDTH - bi.getWidth() :
                bi.getWidth() < FRONT_SENSOR_WIDTH_POSITION ? FRONT_SENSOR_WIDTH_POSITION - bi.getWidth() + 1 : 0;
        int yStarting = GarageConstants.HEIGHT < bi.getHeight() ? GarageConstants.HEIGHT - bi.getHeight() :
                bi.getHeight() < LEFT_SIDE_SENSOR_HEIGHT_POSITION ? LEFT_SIDE_SENSOR_HEIGHT_POSITION - bi.getHeight() + 1 : 0;
        for (int x = xStarting; x < FRONT_SENSOR_WIDTH_POSITION; x++) {
            for (int y = yStarting; y < LEFT_SIDE_SENSOR_HEIGHT_POSITION; y++) {
                int xDestination = xDistance.get(LEFT_SIDE_SENSOR_HEIGHT_POSITION - y) + x;
                int yDestination = yDistance.get(FRONT_SENSOR_WIDTH_POSITION - x) + y;
                if (yDestination == frontSensor && xDestination == leftSideSensor) {
                    System.out.println(frontSensor + ": {" + x + ", " + y + "} " + xDestination + " " + yDestination);
                    return Optional.of(Coordinates.builder()
                            .x(x)
                            .y(y)
                            .build());
                }
            }
        }
        LOGGER.info("No position found for " + Math.toDegrees(rads) + " degrees rotation on sensor data: {" + leftSideSensor + ", " + frontSensor + "}");
        return Optional.empty();
    }

    public static BufferedImage rotateImage(BufferedImage bi, double rads) {
        Dimensions rotatedDimensions = getDimensionsAfterRotation(bi, rads);
        final int w = (int) Math.floor(rotatedDimensions.getWidth());
        final int h = (int) Math.floor(rotatedDimensions.getHeight());
        final BufferedImage rotatedImage = new BufferedImage(w, h, bi.getType());
        final AffineTransform at = new AffineTransform();
        at.translate(w / 2, h / 2);
        at.rotate(rads, 0, 0);
        at.translate(-bi.getWidth() / 2, -bi.getHeight() / 2);
        final AffineTransformOp rotateOp = new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);
        return rotateOp.filter(bi, rotatedImage);
    }

    public static Dimensions getDimensionsAfterRotation(BufferedImage bi, double rads) {
        final double sin = Math.abs(Math.sin(rads));
        final double cos = Math.abs(Math.cos(rads));
        return Dimensions.builder()
                .width(bi.getWidth() * cos + bi.getHeight() * sin)
                .height(bi.getHeight() * cos + bi.getWidth() * sin)
                .build();
    }

    private static int countXDestination(BufferedImage bi, int yPosition) {
        int x = 0;
        while (!Color.BLACK.equals(getPixelColor(bi, x, yPosition))) {
            x++;
            if (x >= bi.getWidth()) {
                break;
            }
        }
        LOGGER.debug("Destination on Ox before black pixel on {x, " + yPosition + "}: " + x);
        return x;
    }

    private static int countYDestination(BufferedImage bi, int xPosition) {
        int y = 0;
        while (!Color.BLACK.equals(getPixelColor(bi, xPosition, y))) {
            y++;
            if (y >= bi.getHeight()) break;
        }
        LOGGER.debug("Destination on Ox before black pixel on {" + xPosition + ", y}: " + y);
        return y;
    }

    private static Color getPixelColor(BufferedImage bi, int x, int y) {
        Object colorData = bi.getRaster().getDataElements(x, y, null);
        int argb = bi.getColorModel().getRGB(colorData);
        return new Color(argb, true);
    }

}
