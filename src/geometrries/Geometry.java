package geometrries;

import primitives.Point;
import primitives.Vector;

public interface Geometry extends Intersectable {
    /**
     * @return normal from the point to geometrries
     */
    Vector getNormal(Point point);
}
