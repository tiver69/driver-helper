package driverhelper.helper;

import driverhelper.model.Coordinates;
import driverhelper.model.Dimensions;
import driverhelper.model.DimensionsEnum;
import driverhelper.model.response.CarSettings;
import driverhelper.model.response.GarageSettings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static driverhelper.constants.Constants.IMAGE_BASE_FORMAT;
import static driverhelper.constants.Constants.RESOURCES_BASE_PATH;

@Component
public class ImageHelper {

    private static final Logger LOGGER = LoggerFactory.getLogger(ImageHelper.class);
    private static final Coordinates previous = new Coordinates();
    private static final Coordinates closest = new Coordinates();
    private static double delta = Double.MAX_VALUE;
    private static int resetStep = 0;

    @Autowired
    private FileHelper fileHelper;

    public double getRotatedImageShiftOnCurrentAngle(BufferedImage bi, double radsAlpha, DimensionsEnum dimension) {
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

    public Optional<Coordinates> getRotatedImagePositionOnCurrentSensorData(BufferedImage bi, double rads, double leftSideSensor, double frontSensor) {
        GarageSettings garageSettings = fileHelper.getCurrentGarageSettings();
        bi = rotateImage(bi, rads);
        delta = Double.MAX_VALUE;
        Optional<Coordinates> preConditions = resolvePreconditions(bi, garageSettings, leftSideSensor, frontSensor);
        if (preConditions.isPresent()) return preConditions;
        List<Integer> xDistance = new ArrayList<>();
        for (int y = 0; y < bi.getHeight(); y++) {
            xDistance.add(countXDestination(bi, y));
        }
        List<Integer> yDistance = new ArrayList<>();
        for (int x = 0; x < bi.getWidth(); x++) {
            yDistance.add(countYDestination(bi, x));
        }

        List<Coordinates> possibleValues = new ArrayList<>();
        int xStarting = (int) (garageSettings.getGarageWidth() < bi.getWidth() ? garageSettings.getGarageWidth() - bi.getWidth() :
                bi.getWidth() < garageSettings.getFrontSensor() ? garageSettings.getFrontSensor() - bi.getWidth() + 1 : 0);
        int yStarting = (int) (garageSettings.getGarageHeight() < bi.getHeight() ? garageSettings.getGarageHeight() - bi.getHeight() :
                bi.getHeight() < garageSettings.getLeftSensor() ? garageSettings.getLeftSensor() - bi.getHeight() + 1 : 0);
        for (int x = xStarting; x < garageSettings.getFrontSensor(); x++) {
            for (int y = yStarting; y < (int) garageSettings.getLeftSensor(); y++) {
                int xDestination = xDistance.get((int) garageSettings.getLeftSensor() - y) + x;
                int yDestination = yDistance.get((int) garageSettings.getFrontSensor() - x) + y;
                if (yDestination == frontSensor && xDestination == leftSideSensor) {
                    possibleValues.add(Coordinates.builder()
                            .x(x)
                            .y(y)
                            .build());
                } else {
                    double currentDelta =
                            (Math.abs(yDestination - frontSensor) + Math.abs(xDestination - leftSideSensor));
                    if (currentDelta <= delta) {
                        delta = currentDelta;
                        closest.setX(x);
                        closest.setY(y);
                    }
                }
            }
        }
        if (possibleValues.isEmpty())
            return resolvePostConditions(rads, leftSideSensor, frontSensor);

        Coordinates resultValue = new Coordinates();
        if (possibleValues.size() == 1) {
            resultValue = possibleValues.get(0);
        } else {
            double closestDelta = Double.MAX_VALUE;
            for (Coordinates v : possibleValues) {
                double currentDelta =
                        (Math.abs(v.getX() - previous.getX()) + Math.abs(v.getY() - v.getY()));
                if (currentDelta <= closestDelta) {
                    closestDelta = currentDelta;
                    resultValue = v;
//                    resultValue.setX(v.getX());
//                    resultValue.setY(v.getY());
                }
            }
        }
        resetStep = 0;
        LOGGER.info(frontSensor + ", " + leftSideSensor + ": {" + resultValue.getX() + ", " + resultValue.getY() + "} from " + possibleValues.size() + " options");
        previous.setX(resultValue.getX());
        previous.setY(resultValue.getY());
        return Optional.of(resultValue);
    }

    public Dimensions getDimensionsAfterRotation(BufferedImage bi, double rads) {
        final double sin = Math.abs(Math.sin(rads));
        final double cos = Math.abs(Math.cos(rads));
        return Dimensions.builder()
                .width(bi.getWidth() * cos + bi.getHeight() * sin)
                .height(bi.getHeight() * cos + bi.getWidth() * sin)
                .build();
    }

    public BufferedImage getCurrentCarImage() {
        CarSettings currentCar = fileHelper.getCurrentCar();
        String imagePath = RESOURCES_BASE_PATH + currentCar.getImageFileName() + IMAGE_BASE_FORMAT;
        try {
            return ImageIO.read(new File(imagePath));
        } catch (IOException e) {
            LOGGER.error("Error while reading car#" + currentCar.getId() + " image from file system");
            throw new IllegalArgumentException("Error while reading car#" + currentCar.getId() + " image from file system");
        }
    }

    private Optional<Coordinates> resolvePreconditions(BufferedImage bi, GarageSettings garageSettings,
                                                       double leftSideSensor, double frontSensor) {
        Coordinates rotatedPosition = null;
        boolean frontSensorCondition = frontSensor > garageSettings.getLeftSensor();
        boolean leftSideSensorCondition = (garageSettings.getGarageWidth() - leftSideSensor) <= 10;
        if (frontSensorCondition || leftSideSensorCondition) {
            double x = previous.getX() == 0 ? (garageSettings.getGarageWidth() - bi.getWidth()) / 2 : previous.getX();
            rotatedPosition = Coordinates.builder().x(x).y(frontSensor).build();
            LOGGER.info("Using position based on " + (frontSensorCondition ? "front" : "left side") +
                    " sensor data: " + rotatedPosition + " on sensor data: {" + leftSideSensor + ", " + frontSensor + "}");
        }
        return Optional.ofNullable(rotatedPosition);
    }

    private Optional<Coordinates> resolvePostConditions(double rads, double leftSideSensor, double frontSensor) {
        double deltaPrevious = (Math.abs(closest.getX() - previous.getX()) + Math.abs(closest.getY() - previous.getY()));
        if (delta <= 10 && deltaPrevious <= 10) {
            LOGGER.info("Using closest value with delta = " + delta + ": " + closest);
            return Optional.of(closest);
        } else {
            LOGGER.info("No closest position found for " + Math.toDegrees(rads) + " degrees rotation on sensor data: {" + leftSideSensor + ", " + frontSensor + "}: delta=" + delta +
                    " previous delta = " + deltaPrevious);
        }
        if (resetStep <= 1) {
            resetStep++;
            LOGGER.info("Using previous values for " + resetStep + " time(s): " + previous);
            return Optional.of(previous);
        }
        return Optional.empty();
    }

    private BufferedImage rotateImage(BufferedImage bi, double rads) {
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

    private int countXDestination(BufferedImage bi, int yPosition) {
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

    private int countYDestination(BufferedImage bi, int xPosition) {
        int y = 0;
        while (!Color.BLACK.equals(getPixelColor(bi, xPosition, y))) {
            y++;
            if (y >= bi.getHeight()) break;
        }
        LOGGER.debug("Destination on Ox before black pixel on {" + xPosition + ", y}: " + y);
        return y;
    }

    private Color getPixelColor(BufferedImage bi, int x, int y) {
        Object colorData = bi.getRaster().getDataElements(x, y, null);
        int argb = bi.getColorModel().getRGB(colorData);
        return new Color(argb, true);
    }
}
