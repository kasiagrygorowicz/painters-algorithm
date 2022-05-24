package world;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.jetbrains.annotations.NotNull;

@AllArgsConstructor
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
public class Line {

    @EqualsAndHashCode.Include
    @NotNull
    private Point3D p1;
    @EqualsAndHashCode.Include
    @NotNull
    private Point3D p2;

    public Point3D[] getLine() {
        return new Point3D[]{p1, p2};
    }
}
