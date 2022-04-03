package geometries;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import primitives.Point;
import primitives.Ray;

public class Geometries implements Intersectable {

    protected List<Intersectable> intersectList;


    /**
     * Default constructor
     */
    public Geometries() {
        intersectList = new LinkedList<>();
    }

    /**
     * Initialize the geometries based on the geometries received
     */
    public Geometries(Intersectable... geometries) {
        intersectList = new LinkedList<>();
        add(geometries);
    }

    /**
     * Add new geometries
     */
    public void add(Intersectable... geometries) {
        Collections.addAll(intersectList, geometries);
    }

    /**
     * findIntersections find intersections between the geometries to ray
     * @param ray The Ray to intersect
     * @return list of point that intersections between the geometries to ray
     */
    @Override
    public List<Point> findIntersections(Ray ray) {
        if (intersectList.isEmpty()) {
            return null; // if have no intersections
        }
        List<Point> result = null;
        for (var item : intersectList) { //for all geometries in the list
            List<Point> itemList = item.findIntersections(ray);
            if (itemList != null) {
                if (result == null) {
                    result = new LinkedList<Point>();
                }
                result.addAll(itemList);
            }
        }
        return result;
    }
}

