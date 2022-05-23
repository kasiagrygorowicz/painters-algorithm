package utils;


import world.Line;
import world.Point;


public class TransformationUtility {

    public static void rotate(Point point, ROTATION_TYPE type, double rotation) {
        double x = point.getX();
        double y = point.getY();
        double z = point.getZ();

        double rotationRadians = Math.toRadians(rotation);


        if (ROTATION_TYPE.X == type) {
            y = y * Math.cos(rotationRadians) - z * Math.sin(rotationRadians);
            z = y * Math.sin(rotationRadians) + z * Math.cos(rotationRadians);
        }
//
        if (ROTATION_TYPE.Y == type) {
            x = z * Math.sin(rotationRadians) + x * Math.cos(rotationRadians);
            z = z * Math.cos(rotationRadians) - x * Math.sin(rotationRadians);
        }
        if (ROTATION_TYPE.Z == type) {
            x = x * Math.cos(rotationRadians) - y * Math.sin(rotationRadians);
            y = x * Math.sin(rotationRadians) + y * Math.cos(rotationRadians);
        }

        point.setX(x);
        point.setY(y);
        point.setZ(z);
    }
}
