package world;

import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
public class Point3D {

    //  == coordinates ==
    @EqualsAndHashCode.Include
    private double x;
    @EqualsAndHashCode.Include
    private double y;
    @EqualsAndHashCode.Include
    private double z;

    public Point3D(Point3D point3D) {
        this.x = point3D.getX();
        this.y = point3D.getY();
        this.z = point3D.getZ();
    }

    public double[] getCoordinates() {
        return new double[]{x, y, z};
    }

    public void setCoordinates(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public void multiplyCoordinates(double t) {
        this.x *= t;
        this.y *= t;
        this.z *= t;
    }
}
