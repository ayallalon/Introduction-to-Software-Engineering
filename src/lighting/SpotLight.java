package lighting;

import primitives.*;

public class SpotLight extends PointLight{

    /**
     *
     * @param intensity
     * @param position
     * @param direction
     */
    public SpotLight(Color intensity, Point position, Vector direction) {
        super(intensity, position);
        this.direction = direction;
    }

    Vector direction;

    @Override
    public Color getIntensity() {
        return super.getIntensity();
    }

    public Color getIntensity(Point P)
    {
        // but kL and Kq are 0
        double calc=(kC+Position.distance(P))*kL+(Position.distanceSquared(P));
        Vector l=P.subtract(Position);
        return (super.getIntensity().scale(Math.max(0,direction.normalize().dotProduct(l))).reduce(calc));
    }

    public Vector getL(Point p)
    {
        return direction;
    }

    private SpotLight setDirection(Vector dir){
        direction = dir;
        return this;
    }
}
