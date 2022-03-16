package geometrries;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static primitives.Util.isZero;

import primitives.Point;
import primitives.Vector;

class PlaneTest {

    /**
     * Test method for {@link Plane#getNormal(Point)}
     */
    void testConstructor() {

        // ============ Equivalence Partitions Tests ==============
        /** check if first point and tha second point are merge*/

        Point a = new Point(0, 1, 1);
        Point b = new Point(-2, 1, 0);
        Point c = new Point(5, 0, 2);

        Plane plane = new Plane(a, b, c);
        Vector normal = plane.getNormal();

        // TC01: the test check if the fun getNormal calculated correct
        assertEquals(new Vector(1, 7, 2), normal, "Error:constructor is not calculated correct");

        // TC02: the test check if the length of normal different from 1
        assertTrue(isZero(normal.length() - 1),
                   "Error: Plane getNormal not returning correct value");
    }

    @org.junit.jupiter.api.Test
    void testGetNormal() {

    }
}