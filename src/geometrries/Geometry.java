package geometrries;

import primitives.Point;
import primitives.Vector;

public interface Geometry {
    /**
     * @param point
     * @return normal from the point to geometrries
     */
    public Vector getNormal(Point point);
}
