package data;


import org.json.JSONArray;
import org.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import world.Line;
import world.Point;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class DataReader {

    private static final Logger log = LoggerFactory.getLogger(DataReader.class);

    public static List load(String path) throws IOException {
        List<Line> objects = new ArrayList<>();
        JSONArray parser = new JSONArray(Files.readString(Path.of(path), StandardCharsets.UTF_8));
        parser.forEach(lines -> {
            JSONArray line = (JSONArray) lines;
            for(var l :line) {
                JSONObject points = (JSONObject) ((JSONObject) l).get("line") ;
                Point p1 = new Point(points.getDouble("x1"), points.getDouble("y1"), points.getDouble("z1"));
                Point p2 = new Point(points.getDouble("x2"), points.getDouble("y2"), points.getDouble("z2"));
                objects.add(new Line(p1, p2));
            }
        });
        System.out.println("Size "+objects.size());
        return objects;
    }




}
