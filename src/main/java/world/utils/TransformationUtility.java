package world.utils;

import world.Line;

public class TransformationUtility {

    public static void rotate(Line line, world.utils.ROTATION_TYPE type, double rotation) {
        double x1 = line.getP1().getX();
        double y1 = line.getP1().getY();
        double z1 = line.getP1().getZ();
        double x2 = line.getP2().getX();
        double y2 = line.getP2().getY();
        double z2 = line.getP2().getZ();

        double rotationRadians =Math.toRadians(rotation);


        if (world.utils.ROTATION_TYPE.X == type) {
            y1 = y1 * Math.cos(rotationRadians) - z1 * Math.sin(rotationRadians);
            z1 = y1 * Math.sin(rotationRadians) + z1 * Math.cos(rotationRadians);
            y2 = y2 * Math.cos(rotationRadians) - z2 * Math.sin(rotationRadians);
            z2 = y2 * Math.sin(rotationRadians) + z2 * Math.cos(rotationRadians);
        }
//
        if (world.utils.ROTATION_TYPE.Y == type) {
            x1 = z1 * Math.sin(rotationRadians) + x1 * Math.cos(rotationRadians);
            z1 = z1 * Math.cos(rotationRadians) - x1 * Math.sin(rotationRadians);
            x2 = z2 * Math.sin(rotationRadians) + x2 * Math.cos(rotationRadians);
            z2 = z2 * Math.cos(rotationRadians) - x2 * Math.sin(rotationRadians);
        }
        if (world.utils.ROTATION_TYPE.Z == type) {
            x1 = x1 * Math.cos(rotationRadians) - y1 * Math.sin(rotationRadians);
            y1 = x1 * Math.sin(rotationRadians) + y1 * Math.cos(rotationRadians);
            x2 = x2 * Math.cos(rotationRadians) - y2 * Math.sin(rotationRadians);
            y2 = x2 * Math.sin(rotationRadians) + y2 * Math.cos(rotationRadians);
        }

        line.getP1().setX(x1);
        line.getP1().setY(y1);
        line.getP1().setZ(z1);
        line.getP2().setX(x2);
        line.getP2().setY(y2);
        line.getP2().setZ(z2);

    }
}
