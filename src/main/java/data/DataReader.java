package data;


import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import world.Point3D;
import world.Polygon3D;

import java.awt.*;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class DataReader {

    private static final Logger log = LoggerFactory.getLogger(DataReader.class);
    private static final List<Color> WALLS_COLORS = List.of(Color.BLUE, Color.RED, Color.WHITE, Color.green, Color.ORANGE, Color.MAGENTA);

    public static List<Polygon3D> load(String path) throws IOException {
        List<Polygon3D> polygonsList = new ArrayList<>();

        final String jsonText = Files.readString(Path.of(path), StandardCharsets.UTF_8);

        JSONObject json = new JSONObject(jsonText);
        JSONArray polygonsArray = json.getJSONArray("polygons");
        int k = 0;
        for (var polygonItem : polygonsArray) {
            JSONObject polygonObject = (JSONObject) polygonItem;

            Polygon3D polygon3d = new Polygon3D();

            JSONArray pointsArray = polygonObject.getJSONArray("points");

            for (var pointsItem : pointsArray) {
                JSONArray pointsList = (JSONArray) pointsItem;
//               x -> 0, y -> 1, z-> 2
                polygon3d.addPoint(new Point3D(pointsList.getDouble(0), pointsList.getDouble(1), pointsList.getDouble(2)));
            }

            polygon3d.setColor(WALLS_COLORS.get(k % 6));
            k++;

            polygonsList.add(polygon3d);
        }
        return polygonsList;
    }

}


