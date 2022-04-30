package primitives;

import static primitives.Util.isZero;

public class Vector extends Point {

    /**
     * Constructor
     * @param x coordinate value for x axis
     * @param y coordinate value for y axis
     * @param z coordinate value for z axis
     */
    public Vector(double x, double y, double z) {
        this(new Double3(x, y, z));
    }

    /**
     * Another constructor
     * @throws IllegalArgumentException if the vector is zero vector
     */
    public Vector(Double3 xyz) {
        super(xyz);
        if (this.xyz.equals(Double3.ZERO)) {
            throw new IllegalArgumentException("Vector(0,0,0) is not allowed");
        }
    }

    /**
     * @return length Squared of vector
     */
    public double lengthSquared() {
        return xyz.d1 * xyz.d1 +
               xyz.d2 * xyz.d2 +
               xyz.d3 * xyz.d3;
    }

    /**
     * @return length of vector
     */
    public double length() {
        return Math.sqrt(lengthSquared());
    }

    /**
     * Dot product between two vectors (scalar product)
     * @param other the right vector of U.V
     * @return scalar value of the dot product
     */
    public double dotProduct(Vector other) {
        return xyz.d1 * other.xyz.d1 +
               xyz.d2 * other.xyz.d2 +
               xyz.d3 * other.xyz.d3;
    }

    /**
     * Cross product between two vectors (vectorial product)
     * @param other other the right vector of U.V
     * @return the vector resulting from the cross product (Right-hand rule)
     *     link cuemath.com/geometry/cross-product/
     */
    public Vector crossProduct(Vector other) {
        double ax = xyz.d1;
        double ay = xyz.d2;
        double az = xyz.d3;

        double bx = other.xyz.d1;
        double by = other.xyz.d2;
        double bz = other.xyz.d3;

        double cx = ay * bz - az * by;
        double cy = -(ax * bz - az * bx);
        double cz = ax * by - ay * bx;

        if (cx == 0 && cy == 0 && cz == 0) {
            throw new IllegalArgumentException("parallel vectors- CROS PRODUCT");
        }
        return new Vector(cx, cy, cz);
    }

    /**
     * Scale (multiply) vector by a number into a new vector
     * @param scalar right handle side operand for scaling
     * @return result of scale
     */
    public Vector scale(double scalar) {
        if (isZero(scalar)) {
            throw new IllegalArgumentException("scale by zero not allowed");
        }
        return new Vector(xyz.scale(scalar));
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
    public String toString() {
        return super.toString();
    }

    /**
     * Sum two vector into a new vector where each couple of numbers
     * is summarized
     * @param vector right handle side operand for addition
     * @return result of add
     */
    public Vector add(Vector vector) {
        return new Vector(xyz.add(vector.xyz));
    }

    /**
     * normalize
     * @return normal vector
     */
    public Vector normalize() {
        double len = length();
        if (len == 0) {
            throw new ArithmeticException("Divide by zero!");
        }
        return new Vector(xyz.reduce((len)));
    }
}
