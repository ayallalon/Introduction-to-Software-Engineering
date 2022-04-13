package geometries;

import java.util.List;
import java.util.Objects;

import primitives.Point;
import primitives.Ray;

public abstract class Intersectable {

    /**
     *
     */
    public static class GeoPoint {

        public final Geometry geometry;//
        public final Point point;      //

        /**
         *
         * @param geometry
         * @param point
         */
        public GeoPoint(Geometry geometry, Point point) {
            this.geometry = geometry;
            this.point = point;
        }


        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            GeoPoint geoPoint = (GeoPoint) o;
            return geometry.equals(geoPoint.geometry) && point.equals(geoPoint.point);
        }

        @Override
        public int hashCode() {
            return Objects.hash(geometry, point);
        }

        @Override
        public String toString() {
            return "GeoPoint{" +
                    "geometry=" + geometry +
                    ", point=" + point +
                    '}';
        }
    }

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

    //NVI

    /**
     *
     * @param ray
     * @return
     */
    public final List<GeoPoint> findGeoIntersections(Ray ray) {
        //...
        return findGeoIntersectionsHelper(ray);
    }

    protected abstract List<GeoPoint> findGeoIntersectionsHelper(Ray ray);


}
