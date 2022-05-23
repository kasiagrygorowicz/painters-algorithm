package utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import world.Camera;
import world.Polygon3D;

import java.util.ArrayList;
import java.util.List;

public class DividerUtility {

    private static final Logger log = LoggerFactory.getLogger(DividerUtility.class);

    public static List<Polygon3D> dividePolygon(List<Polygon3D> world) {
        List<Polygon3D> newWorld = new ArrayList<>();
        log.info("Starting with {} polygons".format(String.valueOf(world.size())));

        for (var polygon : world) {
            if (polygon.getPoints().size() > 3) {
                Polygon3D p = new Polygon3D();

                for (int i = 0; i < polygon.getPoints().size() - 2; i++) {
                    for (int j = i; j < i + 3; j++)
                        p.addPoint(polygon.getPoints().get(j));
                    p.setColor(polygon.getColor());
                }
                log.info(p.toString());
                newWorld.add(p);
            } else {
                newWorld.add(polygon);
            }
        }
        log.info("Finishign with total number of divided polygons {}".format(String.valueOf(newWorld.size())));
        return newWorld;
    }
}
