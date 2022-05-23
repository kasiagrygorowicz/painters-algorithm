package world;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class Polygon3D {

    private List<Point> points = new ArrayList<>();
    private Color color;

    public static void sortPolygons(List<Polygon3D> polygons) {


        Collections.sort(polygons, (p1, p2) -> {

            double p2Average = Math.sqrt((Math.pow(p2.getAverageX(), 2)
                    + Math.pow(p2.getAverageY(), 2)
                    + Math.pow(p2.getAverageZ(), 2)));
            double p1Average = Math.sqrt((Math.pow(p1.getAverageX(), 2)
                    + Math.pow(p1.getAverageY(), 2)
                    + Math.pow(p1.getAverageZ(), 2)));
            double diff = p2Average - p1Average;

            if(diff == 0) {
                return 0;
            }

            return diff < 0 ? -1 : 1;
        });

    }

    public static List<Polygon3D> dividePolygon(List<Polygon3D> objects) {

        int k = 8;
        int kk = k * k;
        List<Polygon3D> newPolygons = new ArrayList<>();

        for (Polygon3D poly : objects) {
            System.out.println(poly.color);

            Point[] newPoints = new Point[81];

            newPoints[0] = poly.getPoints().get(3);
            newPoints[8] = poly.getPoints().get(0);
            newPoints[80] = poly.getPoints().get(1);
            newPoints[72] = poly.getPoints().get(2);

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
                int t = i * (k + 1);

                newPoints[4 + t] = averagePoint(newPoints[0 + t], newPoints[8 + t]);
                newPoints[2 + t] = averagePoint(newPoints[0 + t], newPoints[4 + t]);
                newPoints[1 + t] = averagePoint(newPoints[0 + t], newPoints[2 + t]);
                newPoints[3 + t] = averagePoint(newPoints[2 + t], newPoints[4 + t]);
                newPoints[6 + t] = averagePoint(newPoints[4 + t], newPoints[8 + t]);
                newPoints[5 + t] = averagePoint(newPoints[4 + t], newPoints[6 + t]);
                newPoints[7 + t] = averagePoint(newPoints[6 + t], newPoints[8 + t]);
            }


            int j = 0;
            for (int i = 0; i < kk; i++) {
                List<Point>points =new ArrayList<>();
                if (i % 8 == 0 && i != 0) {
                    j += 1;
                }
                points.add(newPoints[0+j]);
                points.add(newPoints[1+j]);
                points.add(newPoints[10+j]);
                points.add(newPoints[9+j]);
                newPolygons.add(new Polygon3D(points,poly.getColor()));
                j++;
            }


        }

        return newPolygons;
    }

    private static Point averagePoint(Point a, Point b) {
        return new Point((a.getX() + b.getX()) / 2, (a.getY() + b.getY()) / 2, (a.getZ() + b.getZ()) / 2);
    }

    public void addPoint(Point point) {
        this.points.add(point);
    }

    public double getAverageZ() {
        double sum = 0;
        for (Point p : this.points) {
            sum += p.getZ();
        }

        return sum / this.points.size();
    }

    public double getAverageX() {
        double sum = 0;
        for (Point p : this.points) {
            sum += p.getX();
        }

        return sum / this.points.size();
    }

    public double getAverageY() {
        double sum = 0;
        for (Point p : this.points) {
            sum += p.getY();
        }

        return sum / this.points.size();
    }
}
