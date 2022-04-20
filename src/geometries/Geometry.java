package geometries;

import primitives.Color;
import primitives.Material;
import primitives.Point;
import primitives.Vector;

public abstract class Geometry extends Intersectable {

    protected Color emission = Color.BLACK;;
    private Material material = new Material();

    public Geometry() {
        this.emission = emission;
        this.material = material;
    }

    /**
     *
     * @return
     */
    public Material getMaterial() {
        return material;
    }

    /**
     *
     * @param material
     * @return
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
