package world;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.RotationType;
import utils.GeometricUtility;

import javax.swing.*;
import java.awt.*;
import java.util.List;


public class Camera extends JPanel {

    private static final Logger log = LoggerFactory.getLogger(Camera.class);

    private double D = 300;
    private List<Polygon3D> polygons;

    public Camera() {
        this.setBackground(Color.BLACK);
    }

    public void setPolygons(List<Polygon3D> polygons) {
        this.polygons = polygons;
    }

    //  == move camera ==

    public void moveAxisX(double v) {
        for (Polygon3D poly : polygons)
            for (Point3D p : poly.getPoints())
                p.setX(p.getX() + v);
    }

    public void moveAxisY(double v) {
        for (Polygon3D poly : polygons)
            for (Point3D p : poly.getPoints())
                p.setY(p.getY() + v);
    }

    public void moveAxisZ(double v) {
        for (Polygon3D poly : polygons)
            for (Point3D p : poly.getPoints())
                p.setZ(p.getZ() + v);
    }

    //  == zoom ==
    public void setZoom(double v) {
        D += v;
    }

    //  == rotate camera ==
    public void rotateAxisX(double v) {
        for (Polygon3D poly : polygons)
            for (Point3D p : poly.getPoints())
                GeometricUtility.rotate(p, RotationType.X, v);
    }

    public void rotateAxisY(double v) {
        for (Polygon3D poly : polygons)
            for (Point3D p : poly.getPoints())
                GeometricUtility.rotate(p, RotationType.Y, v);
    }

    public void rotateAxisZ(double v) {
        for (Polygon3D poly : polygons)
            for (Point3D p : poly.getPoints())
                GeometricUtility.rotate(p, RotationType.Z, v);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2D = (Graphics2D) g;

        List<Polygon3D> pp = Polygon3D.dividePolygon(polygons);
        Polygon3D.sortPolygons(pp);

        for (Polygon3D polygon3D : pp) {
            int n = polygon3D.getPoints().size();
            int[] points2D_x = new int[n];
            int[] points2D_y = new int[n];
            int index = 0;
            for (Point3D p : polygon3D.getPoints()) {
                java.awt.Point scaledPoint = GeometricUtility.scalePoint(p, D, getHeight(), getWidth());
                points2D_x[index] = scaledPoint.x;
                points2D_y[index] = scaledPoint.y;
                index++;
            }
            Polygon polygon2D = new Polygon(points2D_x, points2D_y, n);
            drawPolygon(g2D, polygon3D, polygon2D);
            drawLines(g2D, polygon3D, polygon2D);
        }

    }

    private void drawPolygon(Graphics2D g2D, Polygon3D polygon3D, Polygon polygon2D) {
        g2D.setPaint(polygon3D.getColor());
        g2D.drawPolygon(polygon2D);
        g2D.fillPolygon(polygon2D);
    }

    private void drawLines(Graphics2D g2D, Polygon3D polygon3D, Polygon polygon2D) {
        for (int i = 0; i < 4; i++) {
            g2D.setColor(polygon3D.getColor());
            g2D.drawLine(polygon2D.xpoints[i % 4], polygon2D.ypoints[i % 4], polygon2D.xpoints[(i + 1) % 4], polygon2D.ypoints[(i + 1) % 4]);
        }
    }
}

