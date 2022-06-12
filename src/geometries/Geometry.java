package geometries;

import primitives.Color;
import primitives.Material;
import primitives.Point;
import primitives.Vector;

/**
 *  interface for all geometries objects
 */
public abstract class Geometry extends Intersectable{

    protected Color emission = Color.BLACK;
    private Material material =  new Material();

    /**
     *
     * declaration of getNormal method from a specific Point
     * to a specific Geometry object
     *
     * @param point Point from where to create a Normal vector to the geometry object
     * @return normal vector
    */
    public abstract Vector getNormal(Point point);


    public Color getEmission() {
        return emission;
    }

    public Geometry setEmission(Color emission) {
        this.emission = emission;
        return this;
    }

    public Geometry setMaterial(Material material) {
        this.material = material;
        return this;
    }

    public Material getMaterial() {
        return material;
    }
}
