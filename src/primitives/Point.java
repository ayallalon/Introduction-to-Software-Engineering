package primitives;

import java.util.Objects;
import  java.math.MathContext;

import static java.lang.Math.max;
import static java.lang.Math.pow;

public class Point {

    final  Double3 _xyz;

    /**
     * primary constractor for point
     * @param xyz
     */
    public Point(Double3 xyz) {
        _xyz = xyz;
    }

    /**
     *
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
     *
     * @param other
     * @return
     */
    public  double distanceSuared(Point other){
        double x1 = _xyz._d1;
        double y1 = _xyz._d1;
        double z1 = _xyz._d1;


        double x2 = other._xyz._d1;
        double y2 = other._xyz._d1;
        double z2 = other._xyz._d1;
        return ((x2  - x1 ) * (x2  - x1 ) + ( y2 - y1 )* ( y2 - y1 ) + ( z2 - z1 )*( z2 - z1 ));


    }

    /**
     *
     * @param vector
     * @return
     */
    public Point add(Vector vector) {
        return new Point(_xyz.add(vector._xyz));
    }



    public Vector subtract(Point p1)
    {
        return  new Vector(_xyz.subtract(p1._xyz));
    }


    /**
     *
     * @param other
     * @return d=sqrt(lengthSquare
     */
    public double distance(Point other)
    {
        return  Math.sqrt(distanceSuared(other));
    }
}
