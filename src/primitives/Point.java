package primitives;

import java.util.Objects;


public class Point {

    public static final Point ZERO = new Point(0d, 0d, 0d);
    protected final Double3 xyz;

    /**
     * Constructor
     */
    public Point(Double3 xyz) {
        this.xyz = xyz;
    }

    /**
     * Another constructor
     * @param x coordinate value for x axis
     * @param y coordinate value for y axis
     * @param z coordinate value for z axis
     */
    public Point(double x, double y, double z) {
        xyz = new Double3(x, y, z);
        //
        // this(new Double3(x,y,z));
    }

    /**
     * @return point xyz
     */
    public Double3 getXyz() {
        return xyz;
    }

    public double getX() {
        return xyz.d1;
    }

    public double getY() {
        return xyz.d2;
    }

    @Override
    public int hashCode() {
        return Objects.hash(xyz);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Point)) {
            return false;
        }
        Point point = (Point)o;
        return xyz.equals(point.xyz);
    }

    @Override
    public String toString() {
        return "Point : " + "_xyz=" + xyz;
    }


    /**
     * Sum  floating point triads with vector into a new triad where each couple of numbers
     * is summarized
     * @param vector right handle side operand for addition
     * @return result of add
     */
    public Point add(Vector vector) {
        return new Point(xyz.add(vector.xyz));
    }


    /**
     * Subtract two floating point triads into a new triad where each couple of
     * numbers is subtracted
     * @param p1 right handle side operand for addition
     * @return result of subtract
     */
    public Vector subtract(Point p1) {
        return new Vector(xyz.subtract(p1.xyz));
    }

    /**
     * Calculate the squared distance of two point
     * @return distance Squared of two point
     */
    public double distanceSquared(Point other) {
        double x1 = xyz.d1;
        double y1 = xyz.d1;
        double z1 = xyz.d1;

        double x2 = other.xyz.d1;
        double y2 = other.xyz.d1;
        double z2 = other.xyz.d1;
        return ((x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1) + (z2 - z1) * (z2 - z1));
    }


    /**
     * Calculate the distance of two point
     * @return distance=sqrt(lengthSquare
     */
    public double distance(Point p1) {
        return Math.sqrt(distanceSquared(p1));
    }
}