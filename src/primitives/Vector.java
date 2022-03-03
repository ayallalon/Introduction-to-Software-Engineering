package primitives;

public class Vector extends Point {


    public Vector(double x, double y, double z) {
        this(new Double3(x, y, z));
    }

    public Vector(Double3 xyz) {
        super(xyz);
        if(_xyz.equals(Double3.ZERO)){
            throw new IllegalArgumentException("Vector(0,0,0) is not allowed");
        }

    }

    /**
     *
     * @return
     */
    public double lengthSquared() {
        return _xyz._d1 * _xyz._d1
                + _xyz._d2 * _xyz._d2
                + _xyz._d3 * _xyz._d3;
    }

    /**
     *
     * @return
     */
    public double length() {
        return Math.sqrt(lengthSquared());
    }

    /**
     *
     * @param other the right vector of U*V
     * @return
     */
    public double dotProduct(Vector other) {
        return _xyz._d1    * other._xyz._d1
                + _xyz._d2 * other._xyz._d2
                + _xyz._d3 * other._xyz._d3;
    }

    /**
     *
     * @param other
     * @return
     * link cuemath.com/geometry/cross-product/
     */
    public Vector crossProduct(Vector other) {

        double ax = _xyz._d1;
        double ay = _xyz._d2;
        double az = _xyz._d3;

        double bx = other._xyz._d1;
        double by = other._xyz._d2;
        double bz = other._xyz._d3;

        double cx = ay * bz - az * by;
        double cy = -(ax * bz - az * bx);
        double cz =  ax * by - ay * bx;

        if(cx == 0 && cy == 0 && cz == 0) {
            throw new IllegalArgumentException("parallel vectors- CROS PRODUCT");
        }
        return new Vector(cx, cy, cz);
    }

    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    public Vector normalize() {
        Double len = length();
        return new Vector(_xyz.reduce(len));
    }

    public Vector add(Vector v) {
        return new Vector(_xyz.add(v._xyz));
    }
    public  Vector scale(double scalar) {
        return new Vector(_xyz.scale(scalar));

    }
}
