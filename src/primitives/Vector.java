package primitives;

public class Vector extends Point {

    /**
     *constructor
     * @param x coordinate value for x axis
     * @param y coordinate value for y axis
     * @param z coordinate value for z axis
     */
    public Vector(double x, double y, double z) {
        this(new Double3(x, y, z));
    }

    /**
     * throw exception if the vector is zero vector
     *
     * another constructor
     * @param xyz
     */
    public Vector(Double3 xyz) {
        super(xyz);
        if(_xyz.equals(Double3.ZERO)){
            throw new IllegalArgumentException("Vector(0,0,0) is not allowed");
        }

    }

    /**
     *
     * @return length Squared of vector
     */
    public double lengthSquared() {
        return _xyz._d1 * _xyz._d1
                + _xyz._d2 * _xyz._d2
                + _xyz._d3 * _xyz._d3;
    }

    /**
     *
     * @return length of vector
     */
    public double length() {
        return Math.sqrt(lengthSquared());
    }

    /**
     * dot product between two vectors (scalar product)
     *
     * @param other the right vector of U.V
     * @return scalar value of the dot product
     * @link https://www.mathsisfun.com/algebra/vectors-dot-product.html
     */
    public double dotProduct(Vector other) {
        return _xyz._d1    * other._xyz._d1
                + _xyz._d2 * other._xyz._d2
                + _xyz._d3 * other._xyz._d3;
    }

    /**
     * cross product between two vectors (vectorial product)
     * @param other other the right vector of U.V
     * @return the vector resulting from the cross product (Right-hand rule)
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

    /**
     * Sum two vector into a new vector where each couple of numbers
     * is summarized
     *
     * @param v right handle side operand for addition
     * @return result of add
     */
    public Vector add(Vector v) {
        return new Vector(_xyz.add(v._xyz));
    }

    /**
     *Scale (multiply) vector by a number into a new vector
     *
     * @param scalar right handle side operand for scaling
     * @return result of scale
     */
    public  Vector scale(double scalar) {
        return new Vector(_xyz.scale(scalar));

    }
}
