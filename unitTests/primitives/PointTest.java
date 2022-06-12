package primitives;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PointTest {
    Point point0 = new Point(1, 1, -100);
    Point point1 = new Point(-1, 1, -99);
    Point point2 = new Point(0, 0, -100);
    Point point3 = new Point(0.5, 0, -100);

    @Test
    void testDistance() {

        double resultsquared;
        double result;

        resultsquared = point3.distanceSquared(new Point(0, 0, -100));
        System.out.println(resultsquared);
        result = point3.distance(new Point(0, 0, -100));
        System.out.println(result);
    }


    @Test
    void testAdd() {
    }

    @Test
    void testSubtract() {
        Vector p0_p1 = point1.subtract(point0);
        System.out.println("point0: " + point0);
        System.out.println("point1: " + point1);
        assertEquals(new Vector(-2d,0,1d),p0_p1,"substract not correct");
    }

    @Test
    void testSubtract2() {
        Point p1 = new Point(1, 2, 3);
        Vector result = p1.subtract(new  Point(2, 3, 4));
        if (!Point.ZERO.equals(p1.add(new Vector(-1, -2, -3))))
            fail("ERROR: Point + Vector does not work correctly");
        if (!new Vector(-1, -1, -1).equals(result))
            fail("ERROR: Point - Point does not work correctly");
    }
}