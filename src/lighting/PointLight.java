package lighting;

import primitives.Color;
import primitives.Point;

public class PointLight extends Light{

    final Point Position;
    final double kC=1;
    final double kL=0;
    final double kQ=0;

    /**
     * constructor
     *
     * @param intensity
     * @param position
     */
    protected PointLight(Color intensity, Point position) {
        super(intensity);
        Position = position;
    }

    /**
     *
     * @return
     */
    @Override
    public Color getIntensity() {
        return super.getIntensity();
    }

    /**
     *
     * @param P
     * @return
     */
    public Color getIntensity(Point P)
    {
        // but kL and Kq are 0
        double calc=(kC+Position.distance(P))*kL+(Position.distanceSquared(P));
        return super.getIntensity().reduce(calc);
    }
}
