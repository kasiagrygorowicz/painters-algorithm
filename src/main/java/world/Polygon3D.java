package world;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class Polygon3D {

    private List<Point3D> points = new ArrayList<>();
    private Color color;

    public static void sortPolygons(List<Polygon3D> polygons) {
        polygons.sort((p1, p2) -> {
            double p2Average = Math.sqrt((Math.pow(p2.getAverageX(), 2)
                    + Math.pow(p2.getAverageY(), 2)
                    + Math.pow(p2.getAverageZ(), 2)));
            double p1Average = Math.sqrt((Math.pow(p1.getAverageX(), 2)
                    + Math.pow(p1.getAverageY(), 2)
                    + Math.pow(p1.getAverageZ(), 2)));
            double diff = p2Average - p1Average;

            if (diff == 0) {
                return 0;
            }

            return diff < 0 ? -1 : 1;
        });
    }

    public static List<Polygon3D> dividePolygon(List<Polygon3D> polygons) {
        int subPolygons = 8;
        int totalPolygons = subPolygons * subPolygons;

        List<Polygon3D> newPolygons = new ArrayList<>();
        List<Point3D> createdPoints = new ArrayList<>();

        for (Polygon3D polygon : polygons) {

//            Point3D lineAStartPoint = polygon.getPoints().get(0);
//            Point3D lineAEndPoint = polygon.getPoints().get(1);
//            Point3D lineBStartPoint = polygon.getPoints().get(3);
//            Point3D lineBEndPoint = polygon.getPoints().get(2);
//
//            Point3D startA = lineAStartPoint;
//            Point3D endA;
//            Point3D startB = lineBStartPoint;
//            Point3D endB;
//
//            double step = 1.0 / subPolygons;
//            double multiplier = step;
//            for (int i = 0; i < subPolygons - 1; i++) {
//                endA = getWeightedAverage(lineAStartPoint, lineAEndPoint, multiplier);
//                endB = getWeightedAverage(lineBStartPoint, lineBEndPoint, multiplier);
//                Polygon3D polygon3D = new Polygon3D(List.of(startA, endA, endB, startB), polygon.getColor());
//                newPolygons.add(polygon3D);
//                startA = endA;
//                startB = endB;
//                multiplier += step;
//            }
//            Polygon3D polygon3D = new Polygon3D(List.of(startA, lineAEndPoint, lineBEndPoint, startB), polygon.getColor());
//            newPolygons.add(polygon3D);

            Point3D[] newPoints = new Point3D[81];

            newPoints[0] = polygon.getPoints().get(3);
            newPoints[8] = polygon.getPoints().get(0);
            newPoints[80] = polygon.getPoints().get(1);
            newPoints[72] = polygon.getPoints().get(2);

            newPoints[36] = averagePoint(newPoints[0], newPoints[72]);
            newPoints[18] = averagePoint(newPoints[0], newPoints[36]);
            newPoints[9] = averagePoint(newPoints[0], newPoints[18]);
            newPoints[27] = averagePoint(newPoints[18], newPoints[36]);
            newPoints[54] = averagePoint(newPoints[36], newPoints[72]);
            newPoints[45] = averagePoint(newPoints[36], newPoints[54]);
            newPoints[63] = averagePoint(newPoints[54], newPoints[72]);

            newPoints[44] = averagePoint(newPoints[8], newPoints[80]);
            newPoints[26] = averagePoint(newPoints[8], newPoints[44]);
            newPoints[17] = averagePoint(newPoints[8], newPoints[26]);
            newPoints[35] = averagePoint(newPoints[26], newPoints[44]);
            newPoints[62] = averagePoint(newPoints[44], newPoints[80]);
            newPoints[53] = averagePoint(newPoints[44], newPoints[62]);
            newPoints[71] = averagePoint(newPoints[80], newPoints[62]);

            for (int i = 0; i < 9; i++) {
                int t = i * (subPolygons + 1);

                newPoints[4 + t] = averagePoint(newPoints[0 + t], newPoints[8 + t]);
                newPoints[2 + t] = averagePoint(newPoints[0 + t], newPoints[4 + t]);
                newPoints[1 + t] = averagePoint(newPoints[0 + t], newPoints[2 + t]);
                newPoints[3 + t] = averagePoint(newPoints[2 + t], newPoints[4 + t]);
                newPoints[6 + t] = averagePoint(newPoints[4 + t], newPoints[8 + t]);
                newPoints[5 + t] = averagePoint(newPoints[4 + t], newPoints[6 + t]);
                newPoints[7 + t] = averagePoint(newPoints[6 + t], newPoints[8 + t]);
            }


            int j = 0;
            for (int i = 0; i < totalPolygons; i++) {
                List<Point3D> points = new ArrayList<>();
                if (i % 8 == 0 && i != 0) {
                    j += 1;
                }
                points.add(newPoints[0 + j]);
                points.add(newPoints[1 + j]);
                points.add(newPoints[10 + j]);
                points.add(newPoints[9 + j]);
                newPolygons.add(new Polygon3D(points, polygon.getColor()));
                j++;
            }


        }
        return newPolygons;
    }

    private static Point3D getFromListIfExists(Point3D point3D, List<Point3D> createdPoints) {
        if (createdPoints.contains(point3D)) {
            return createdPoints.get(createdPoints.indexOf(point3D));
        }
        return point3D;
    }

    private static void addUniqueToList(Point3D point3D, List<Point3D> createdPoints) {
        if (!createdPoints.contains(point3D)) {
            createdPoints.add(point3D);
        }
    }

    private static Point3D getWeightedAverage(Point3D a, Point3D b, double t) {
        Point3D pointA = new Point3D(a.getX(), a.getY(), a.getZ());
        pointA.multiplyCoordinates(1-t);
        Point3D pointB = new Point3D(b.getX(), b.getY(), b.getZ());
        pointB.multiplyCoordinates(t);
        return new Point3D(pointA.getX() + pointB.getX(), pointA.getY() + pointB.getY(), pointA.getZ() + pointB.getZ());
    }

    private static Point3D averagePoint(Point3D a, Point3D b) {
        return new Point3D((a.getX() + b.getX()) / 2, (a.getY() + b.getY()) / 2, (a.getZ() + b.getZ()) / 2);
    }

    public void addPoint(Point3D point) {
        this.points.add(point);
    }

    public double getAverageZ() {
        double sum = 0;
        for (Point3D p : this.points) {
            sum += p.getZ();
        }
        return sum / this.points.size();
    }

    public double getAverageX() {
        double sum = 0;
        for (Point3D p : this.points) {
            sum += p.getX();
        }
        return sum / this.points.size();
    }

    public double getAverageY() {
        double sum = 0;
        for (Point3D p : this.points) {
            sum += p.getY();
        }
        return sum / this.points.size();
    }
}
