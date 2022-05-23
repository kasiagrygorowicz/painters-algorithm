package world;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.*;

import javax.swing.*;
import java.awt.*;

import utils.ROTATION_TYPE;

import java.util.ArrayList;
import java.util.List;


public class Camera extends JPanel {

    private static final Logger log = LoggerFactory.getLogger(Camera.class);

    //  == camera parameters ==
    private double D = 150;

    private List<Polygon3D> objects = null;

    public Camera() {
        this.setBackground(Color.BLACK);
    }

    public void setObjects(List<Polygon3D> objects) {
        this.objects = objects;
    }

    //  == move camera ==

    public void moveAxisX(double v) {
        for (Polygon3D poly : objects)
            for (Point p : poly.getPoints())
                p.setX(p.getX() + v);
    }

    public void moveAxisY(double v) {
        for (Polygon3D poly : objects)
            for (Point p : poly.getPoints())
                p.setY(p.getY() + v);
    }

    public void moveAxisZ(double v) {
        for (Polygon3D poly : objects)
            for (Point p : poly.getPoints())
                p.setZ(p.getZ() + v);
    }

    //  == zoom ==
    public void setZoom(double v) {
        D += v;
    }


    //  == rotate camera ==
    public void rotateAxisX(double v) {
        for (Polygon3D poly : objects)
            for (Point p : poly.getPoints())
                TransformationUtility.rotate(p, ROTATION_TYPE.X, v);
    }

    public void rotateAxisY(double v) {
        for (Polygon3D poly : objects)
            for (Point p : poly.getPoints())
                TransformationUtility.rotate(p, ROTATION_TYPE.Y, v);
    }

    public void rotateAxisZ(double v) {
        for (Polygon3D poly : objects)
            for (Point p : poly.getPoints())
                TransformationUtility.rotate(p, ROTATION_TYPE.Z, v);

    }

    private java.awt.Point scalePoint(Point p){
        double x = p.getX() * D / (p.getZ());
        double y = p.getY() * D / (p.getZ());

        double xPositioned = getSize().width / 2.0;
        double yPositioned = getSize().height / 2.0;

        return new java.awt.Point((int)Math.ceil(x + xPositioned), (int)Math.ceil(y + yPositioned));
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Polygon3D.sortPolygons(this.objects);
        Graphics2D g2D = (Graphics2D) g;
        if (objects != null) {
            for (Polygon3D poly : objects) {
                int n =poly.getPoints().size();
                int[] points2d_x = new int[n];
                int [] points2d_y =  new int[n];
                int index =0;
                for(Point p : poly.getPoints()) {
                    java.awt.Point scaled = scalePoint(p);
                    points2d_x[index]=scaled.x;
                    points2d_y[index]=scaled.y;
                    index++;
                }

                Polygon polygon = new Polygon(
                        points2d_x,points2d_y,n
                );
                g2D.setPaint(poly.getColor());
                g2D.drawPolygon(polygon);
                g2D.fillPolygon(polygon);
            }
        }
    }
}

