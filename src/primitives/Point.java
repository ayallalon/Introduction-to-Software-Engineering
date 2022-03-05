package primitives;

import java.util.Objects;


public class Point {

    final  Double3 _xyz;

    /**
     * constructor
     * @param xyz
     */
    public Point(Double3 xyz) {

        _xyz = xyz;
    }

    /**
     *another constructor
     * @param x coordinate value for x axis
     * @param y coordinate value for y axis
     * @param z coordinate value for z axis
     */
    public Point(double x, double y, double z) {
        _xyz = new Double3(x,y,z);
       //
        // this(new Double3(x,y,z));
    }


    @Override
    public String toString() {
        return "Point : " +
                "_xyz=" + _xyz ;
    }

    /**
     *
     * @return point xyz
     */
    public Double3 getXyz() {
        return _xyz;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Point)) return false;
        Point point = (Point) o;
        return _xyz.equals(point._xyz);
    }

    @Override
    public int hashCode() {
        return Objects.hash(_xyz);
    }

    /**
     *calculate the squared distance of two point
     *
     * @param other
     * @return distance Squared of two point
     */
    public  double distanceSuared(Point other){
        double x1 = _xyz._d1;
        double y1 = _xyz._d1;
        double z1 = _xyz._d1;

        double x2 = other._xyz._d1;
        double y2 = other._xyz._d1;
        double z2 = other._xyz._d1;
        return ((x2  - x1 ) * (x2  - x1 ) + ( y2 - y1 ) * ( y2 - y1 ) + ( z2 - z1 ) * ( z2 - z1 ));


    }

    /**
     * Sum  floating point triads with vector into a new triad where each couple of numbers
     * is summarized
     *
     * @param vector right handle side operand for addition
     * @return result of add
     */
    public Point add(Vector vector) {
        return new Point(_xyz.add(vector._xyz));
    }


    /**
     * Subtract two floating point triads into a new triad where each couple of
     * numbers is subtracted
     *
     * @param p1 right handle side operand for addition
     * @return result of subtract
     */
    public Vector subtract(Point p1)
    {
        return  new Vector(_xyz.subtract(p1._xyz));
    }


    /**
     *calculate the distance of two point
     *
     *
     * @param p1
     * @return distance=sqrt(lengthSquare
     */
    public double distance(Point p1)
    {
        return  Math.sqrt(distanceSuared(p1));
    }
}
