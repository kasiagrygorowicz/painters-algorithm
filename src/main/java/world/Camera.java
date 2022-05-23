package world;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.*;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;


public class Camera extends JPanel {

    private static final Logger log = LoggerFactory.getLogger(Camera.class);

    //  == camera parameters ==
    private final int DIEWIDTH = 12;
    private final int DIEHEIGHT = 12;
    private double focalLength = 5;

    //World position
    private double xPosition = 0.0;
    private double yPosition = 0.0;
    private double zPosition = 0.0;
    //World rotation
    private double xRotation = 0.0;
    private double yRotation = 0.0;
    private double zRotation = 0.0;

    private List<Polygon3D> objects = null;

    public Camera() {
        this.setBackground(Color.BLACK);
    }

    public void setObjects(List<Polygon3D> objects) {
        this.objects = objects;
    }

    //  == move camera ==

    public void moveAxisX(double v) {
        xPosition += v;
    }

    public void moveAxisY(double v) {
        yPosition += v;
    }

    public void moveAxisZ(double v) {
        zPosition += v;
    }

    //  == zoom ==
    public void setZoom(double v) {
        focalLength += v;
    }


    //  == rotate camera ==
    public void rotateAxisX(double v) {
        xRotation += v;
    }

    public void rotateAxisY(double v) {
        yRotation += v;
    }

    public void rotateAxisZ(double v) {
        zRotation += v;
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

//        todo divide -> transform-> sort

        List<Polygon3D> objects =  DividerUtility.dividePolygon(transform());
        objects = SortingUtility.sort(objects);

        for (var polygon : objects) {
            Polygon p = new Polygon();
            for (var coord : polygon.getPoints())
                p.addPoint((int) coord.getX(), (int) coord.getY());

            g.setColor(polygon.getColor());
            g.fillPolygon(p);
        }


    }



    private List<Polygon3D> transform() {
        List<Polygon3D> world = new ArrayList<>();

        for(var polygon : this.objects) {
            boolean flag = false;
            Polygon3D p = new Polygon3D();

            for(var coord : polygon.getPoints()) {
                if(!flag) {
                    double x = coord.getX() + xPosition;
                    double y = coord.getY() + yPosition;
                    double z = coord.getZ() + zPosition;

                    //X rotation
                    double xRadian = (xRotation * Math.PI) / 180;
                    y = y*Math.cos(xRadian) - z*Math.sin(xRadian);
                    z = y*Math.sin(xRadian) + z*Math.cos(xRadian);

                    //Y rotation
                    double yRadian = (yRotation * Math.PI) / 180;
                    x = z*Math.sin(yRadian) + x*Math.cos(yRadian);
                    z = z*Math.cos(yRadian) - x*Math.sin(yRadian);

                    //Z rotation
                    double zRadian = (zRotation * Math.PI) / 180;
                    x = x*Math.cos(zRadian) - y*Math.sin(zRadian);
                    y = x*Math.sin(zRadian) + y*Math.cos(zRadian);

                    if(z < 0) {
                        flag = true;
                        continue;
                    }

                    x = x*focalLength / (z+focalLength);
                    y = y*focalLength / (z+focalLength);

                    int scaleX = getSize().width / DIEWIDTH;
                    int scaleY = getSize().height / DIEHEIGHT;

                    int centerX = getSize().width / 2;
                    int centerY = getSize().height / 2;

                    x = Math.ceil(x * scaleX + centerX);
                    y = Math.ceil(y * scaleY + centerY);

                    p.addPoint(new Point(x,y,z));
                }
            }

            if(!flag) {
                p.setColor(polygon.getColor());
                world.add(p);
            }
        }

        return world;
    }
}

