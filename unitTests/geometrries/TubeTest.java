package geometrries;

import static org.junit.jupiter.api.Assertions.assertEquals;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

class TubeTest {
    /**
     * Test method for {@link Tube#getNormal(Point)}
     */

    @org.junit.jupiter.api.Test
    void testGetNormal() {


        // ============ Equivalence Partitions Tests ==============
        // TC01: There is a simple single test here
        Ray ray = new Ray(new Point(1, 2, 3), new Vector(2, 3, 4));
        Tube tube = new Tube(3, ray);
        Point point = new Point(1, 1, 3);
        Vector tNormal = tube.getNormal(point);
        assertEquals(new Vector(0, -1, 0), tNormal,
                     "Error: Tube getNormal not returning correct value");
    }
}