package world;

import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
public class Point {

    //  == coordinates ==
    @EqualsAndHashCode.Include
    private double x;
    @EqualsAndHashCode.Include
    private double y;
    @EqualsAndHashCode.Include
    private double z;

    public double[] getCoordinates() {
        return new double[]{x, y, z};
    }

    public void setCoordinates(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

}
