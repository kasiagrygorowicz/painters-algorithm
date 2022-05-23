package world;

import lombok.*;
import org.jetbrains.annotations.NotNull;

@AllArgsConstructor
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
public class Line {

    @EqualsAndHashCode.Include @NotNull
    private Point p1;
    @EqualsAndHashCode.Include @NotNull
    private Point p2;

    public Point[] getLine() {
        return new Point[] {p1, p2};
    }
}
