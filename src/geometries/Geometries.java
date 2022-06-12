package geometries;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import primitives.Ray;

public class Geometries extends Intersectable {

    protected List<Intersectable> intersectList = null;


    /**
     * Default constructor
     */
    public Geometries() {
        intersectList = new LinkedList<>();
    }

    /**
     * Initialize the geometries based on the geometries received
     */
    public Geometries(Intersectable... intersectables) {
        this();
        add(intersectables);
    }

    /**
     * Add new geometries
     */
    public void add(Intersectable... intersectables) {

        Collections.addAll(intersectList, intersectables);
    }

    public void remove(Intersectable... intersectables) {
        intersectList.removeAll(List.of(intersectables));
    }

    /**
     * findIntersections find intersections between the geometries to ray
     * @param ray The Ray to intersect
     * @return list of point that intersections between the geometries to ray
     */

    @Override
    public List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance) {
        if (intersectList.isEmpty()) {
            return null;                                         // if have no intersections
        }
        List<GeoPoint> result = null;
        for (var item : intersectList) {                        // for all geometries in the list
            List<GeoPoint> itemList = item.findGeoIntersectionsHelper(ray,
                                                                      maxDistance); // find intersections
            if (itemList != null) {
                if (result == null) {
                    result = new LinkedList<>();
                }
                result.addAll(itemList);
            }
        }
        return result;
    }
}

