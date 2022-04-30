package geometries;

import primitives.Color;
import primitives.Material;
import primitives.Point;
import primitives.Vector;

public abstract class Geometry extends Intersectable {

    protected Color emission;
    private Material material;

    public Geometry() {
        this.emission = Color.BLACK;
        this.material = new Material();
    }

    /**
     *
     */
    public Material getMaterial() {
        return material;
    }

    /**
     *
     */
    public Geometry setMaterial(Material material) {
        this.material = material;
        return this;
    }

    /**
     * get Emission
     * @return light emission of the geometry
     */
    public Color getEmission() {
        return emission;
    }

    /**
     *
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
