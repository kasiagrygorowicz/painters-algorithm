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
}
