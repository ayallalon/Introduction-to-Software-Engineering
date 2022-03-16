package geometrries;

import static org.junit.jupiter.api.Assertions.assertEquals;

import primitives.Point;
import primitives.Vector;

class SphereTest {
    /**
     * Test method for {@link Sphere#getNormal(Point)}
     */

    @org.junit.jupiter.api.Test
    void testGetNormal() {

        // ============ Equivalence Partitions Tests ==============
        // TC01: There is a simple single test here
        Sphere sphere = new Sphere(new Point(0, 0, 1), 1.0);
        assertEquals(new Vector(0, 0, 1), sphere.getNormal(new Point(0, 0, 2)),
                     "Error: Sphere getNormal not returning correct value");
    }
}

