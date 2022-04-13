package geometries;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

public abstract class Geometry extends Intersectable {

    protected Color emission = Color.BLACK;;


    /**
     * get Emission
     * @return light emission of the geometry
     */
    public Color getEmission() {
        return emission;
    }

    /**
     *
     * @param emission
     */
    public Geometry setEmission(Color emission) {
        this.emission = emission;
        return this;
    }

    /**
     * @return normal from the point to geometries
     */
    public abstract Vector getNormal(Point point);
}
