package geometries;

import java.util.List;
import java.util.Objects;
import primitives.Point;
import primitives.Ray;

public abstract class Intersectable {

    /**
     * Find intersections of a Ray with the Object(s)
     * @param ray The Ray to intersect
     * @return List of intersection points
     */
    public final List<Point> findIntersections(Ray ray) {
        List<GeoPoint> geoList = findGeoIntersections(ray);
        return geoList == null ? null
                               : geoList.stream()
                                        .map(gp -> gp.point)
                                        .toList();
    }

    /**
     *
     */
    public final List<GeoPoint> findGeoIntersections(Ray ray) {
        //...
        return findGeoIntersectionsHelper(ray);
    }

    //NVI

    protected abstract List<GeoPoint> findGeoIntersectionsHelper(Ray ray);

    /**
     *
     */
    public static class GeoPoint {

        public final Geometry geometry;//
        public final Point point;      //

        /**
         *
         */
        public GeoPoint(Geometry geometry, Point point) {
            this.geometry = geometry;
            this.point = point;
        }

        @Override
        public int hashCode() {
            return Objects.hash(geometry, point);
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            GeoPoint other = (GeoPoint)obj;
            boolean sameGeometryType = geometry.getClass().equals(other.geometry.getClass());
            return sameGeometryType && point.equals(other.point);
        }

        @Override
        public String toString() {
            return "GeoPoint{" +
                   "geometry=" + geometry +
                   ", point=" + point +
                   '}';
        }
    }
}
