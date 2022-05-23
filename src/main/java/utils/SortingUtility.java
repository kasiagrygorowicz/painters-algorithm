package utils;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import world.Camera;
import world.Polygon3D;

import java.util.List;

public class SortingUtility {

    private static final Logger log = LoggerFactory.getLogger(SortingUtility.class);

    //    ==    This method sorts polygons by depth (necessary for painter's algorithm)    ==
    public static List<Polygon3D> sort(List<Polygon3D> world) {
        double max = Double.MIN_VALUE;
        for (var polygon : world)
            for (var coord : polygon.getPoints())
                if (coord.getZ() > max)
                    max = coord.getZ();

        double[] count = new double[world.size()];
        for (int i = 0; i < world.size(); i++) {
            double sum = 0;
            for (var coord : world.get(i).getPoints())
                sum += Math.sqrt(
                        Math.pow(coord.getX(), 2) *
                                Math.pow(coord.getY(), 2) *
                                Math.pow(coord.getZ(), 2)
                );
            count[i] = sum;
        }

        for (int i = 0; i < count.length; i++)
            for (int j = count.length - 1; j > 0; j--)
                if (count[j] > count[j - 1]) {
                    var tmp = count[j];
                    count[j] = count[j - 1];
                    count[j - 1] = tmp;

                    var tmp2 = world.get(j);
                    world.set(j, world.get(j - 1));
                    world.set(j - 1, tmp2);
                }

        return world;
    }

}
