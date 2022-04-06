package geometries;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.*;

class TubeTest {
    /**
     * Test method for {@link Tube#getNormal(Point)}
     */

    @Test
    void testGetNormal() {

        // ============ Equivalence Partitions Tests ==============
        // TC01: There is a simple single test here
        Tube tube = new Tube(1.0, new Ray(new Point(0, 0, 1), new Vector(0, -1, 0)));

        Vector normal = tube.getNormal(new Point(0, 0.5, 2)).normalize();

        double dotProduct = normal.dotProduct(tube.getRay().getDir());
        // TC02: normal is not orthogonal to the tube
        assertEquals(0d, dotProduct, "normal is not orthogonal to the tube");

        boolean firstnormal = new Vector(0, 0, 1).equals(normal);
        boolean secondtnormal = new Vector(0, 0, -1).equals(normal);
        // TC03: Bad normal to tube
        assertTrue(firstnormal || secondtnormal, "Bad normal to tube");
       // TC04: Bad normal to tube
        assertEquals(new Vector(0, 0, 1), normal, "Bad normal to tube");

        // =============== Boundary Values Tests ==================
        Tube t = new Tube(2.0, new Ray(new Point(1,1,1), new Vector(0,0,1)));
        assertEquals(new Vector(1,1,0).normalize(),t.getNormal(new Point(2,2,2)));
    }

}