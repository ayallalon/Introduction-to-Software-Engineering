package geometries;

import primitives.Ray;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Geometries extends Intersectable {

    private List<Intersectable> _intersectables = null;

    public Geometries(Intersectable... intersectables) {
        this();
        add(intersectables);
    }

    public Geometries() {
        _intersectables = new LinkedList<>();
    }

    public void add(Intersectable... intersectables) {
        Collections.addAll(_intersectables, intersectables);
    }

    public void remove(Intersectable... intersectables) {
        _intersectables.removeAll(List.of(intersectables));
    }

    @Override
    public List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance) {
        List<GeoPoint> result = null;
        for (Intersectable item : _intersectables) {
            List<GeoPoint> itemList = item.findGeoIntersectionsHelper(ray, maxDistance);
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
