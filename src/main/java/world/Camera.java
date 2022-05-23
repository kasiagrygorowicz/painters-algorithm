package world;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import world.utils.ROTATION_TYPE;
import world.utils.TransformationUtility;

import javax.swing.*;
import java.awt.*;
import java.util.List;


public class Camera extends JPanel {

    private static final Logger log = LoggerFactory.getLogger(Camera.class);

    //  == camera parameters ==
    private double D = 150;

    private List<world.Line> objects = null;

    public Camera() {
        this.setBackground(Color.BLACK);
    }

    public void setObjects(List<world.Line> objects) {
        this.objects = objects;
    }

    //  == move camera ==

    public void moveAxisX(double v) {
        for (world.Line l : objects) {
            l.getP1().setX(l.getP1().getX() + v);
            l.getP2().setX(l.getP2().getX() + v);
        }
    }

    public void moveAxisY(double v) {
        for (world.Line l : objects) {
            l.getP1().setY(l.getP1().getY() + v);
            l.getP2().setY(l.getP2().getY() + v);
        }
    }

    public void moveAxisZ(double v) {
        for (world.Line l : objects) {
            l.getP1().setZ(l.getP1().getZ() + v);
            l.getP2().setZ(l.getP2().getZ() + v);
        }
    }

    //  == zoom ==
    public void setZoom(double v) {
        D += v;
    }


    //  == rotate camera ==
    public void rotateAxisX(double v) {
        for (world.Line l : objects)
            TransformationUtility.rotate(l,ROTATION_TYPE.X,v);
    }

    public void rotateAxisY(double v) {
        for (world.Line l : objects)
            TransformationUtility.rotate(l,ROTATION_TYPE.Y,v);
    }

    public void rotateAxisZ(double v) {
        for (world.Line l : objects)
            TransformationUtility.rotate(l,ROTATION_TYPE.Z,v);

    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2D = (Graphics2D) g;
        if (objects != null) {
            for (world.Line line : objects) {
                world.Line transformed = line;
                double x1, y1, x2, y2;

                x1 = transformed.getP1().getX()*D / (transformed.getP1().getZ());
                y1 = transformed.getP1().getY()*D / (transformed.getP1().getZ());
                x2 = transformed.getP2().getX()*D / (transformed.getP2().getZ());
                y2 = transformed.getP2().getY()*D / (transformed.getP2().getZ());

                double xPositioned = getSize().width/2.0;
                double yPositioned = getSize().height/2.0;

                g2D.setStroke(new BasicStroke(2));
                g2D.setColor(new Color(204, 204, 204));
                g2D.drawLine(
                        (int) Math.ceil(x1+xPositioned),
                        (int) Math.ceil(y1+yPositioned),
                        (int) Math.ceil(x2+xPositioned),
                        (int) Math.ceil(y2+yPositioned)
                );
            }
        }
    }
}
