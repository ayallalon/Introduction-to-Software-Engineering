package geometries;

import primitives.Color;
import primitives.Material;
import primitives.Point;
import primitives.Vector;

public abstract class Geometry extends Intersectable {

    protected Color emission = Color.BLACK;
    private Material material = new Material();

    /**
     * declaration of getNormal method from a specific Point
     * to a specific Geometry object
     * @param point Point from where to create a Normal vector to the geometry object
     * @return normal vector
     */
    public abstract Vector getNormal(Point point);

    /**
     * get Material
     */
    public Material getMaterial() {
        return material;
    }

    /**
     * set Material
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
     * set Emission
     */
    public Geometry setEmission(Color emission) {
        this.emission = emission;
        return this;
    }
}
