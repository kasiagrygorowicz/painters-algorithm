package utils;

import world.Point3D;

public class GeometricUtility {
    public static void rotate(Point3D point3D, RotationType type, double rotation) {
        double x = point3D.getX();
        double y = point3D.getY();
        double z = point3D.getZ();

        double rotationRadians = Math.toRadians(rotation);

        if (RotationType.X == type) {
            y = y * Math.cos(rotationRadians) - z * Math.sin(rotationRadians);
            z = y * Math.sin(rotationRadians) + z * Math.cos(rotationRadians);
        }

        if (RotationType.Y == type) {
            x = z * Math.sin(rotationRadians) + x * Math.cos(rotationRadians);
            z = z * Math.cos(rotationRadians) - x * Math.sin(rotationRadians);
        }
        if (RotationType.Z == type) {
            x = x * Math.cos(rotationRadians) - y * Math.sin(rotationRadians);
            y = x * Math.sin(rotationRadians) + y * Math.cos(rotationRadians);
        }

        point3D.setX(x);
        point3D.setY(y);
        point3D.setZ(z);
    }

    public static java.awt.Point scalePoint(Point3D p, double D, double screenHeight, double screenWidth) {
        double x = p.getX() * D / (p.getZ());
        double y = p.getY() * D / (p.getZ());

        double xPositioned = screenWidth / 2.0;
        double yPositioned = screenHeight / 2.0;

        return new java.awt.Point((int) Math.ceil(x + xPositioned), (int) Math.ceil(y + yPositioned));
    }
}
