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

    private List<Point> points= new ArrayList<>();
    private Color color;

    public void addPoint(Point point){
        this.points.add(point);
    }

    public double getAverageZ() {
        double sum = 0;
        for(Point p : this.points) {
            sum += p.getZ();
        }

        return sum / this.points.size();
    }

    public double getAverageX() {
        double sum = 0;
        for(Point p : this.points) {
            sum += p.getX();
        }

        return sum / this.points.size();
    }

    public double getAverageY() {
        double sum = 0;
        for(Point p : this.points) {
            sum += p.getY();
        }

        return sum / this.points.size();
    }


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
}
