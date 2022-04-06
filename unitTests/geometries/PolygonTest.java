package geometries;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import primitives.Point;

class PolygonTest {

    /**
     * Test method for {@link Polygon#Polygon(primitives.Point...)}.
     */

    @org.junit.jupiter.api.Test
    public void testConstructor() {
        // ============ Equivalence Partitions Tests ==============

        // TC01: Correct concave quadrangular with vertices in correct order
        try {
            new Polygon(new primitives.Point(0, 0, 1),
                        new primitives.Point(1, 0, 0),
                        new primitives.Point(0, 1, 0),
                        new primitives.Point(-1, 1, 1));
        } catch (IllegalArgumentException e) {
            fail("Failed constructing a correct polygon");
        }

        // TC02: Wrong vertices order
        assertThrows(IllegalArgumentException.class, //
                     () -> new Polygon(new primitives.Point(0, 0, 1),
                                       new primitives.Point(0, 1, 0),
                                       new primitives.Point(1, 0, 0),
                                       new primitives.Point(-1, 1, 1)), //
                     "Constructed a polygon with wrong order of vertices");

        // TC03: Not in the same plane
        assertThrows(IllegalArgumentException.class, //
                     () -> new Polygon(new primitives.Point(0, 0, 1),
                                       new primitives.Point(1, 0, 0),
                                       new primitives.Point(0, 1, 0),
                                       new primitives.Point(0, 2, 2)), //
                     "Constructed a polygon with vertices that are not in the same plane");

        // TC04: Concave quadrangular
        assertThrows(IllegalArgumentException.class, //
                     () -> new Polygon(new primitives.Point(0, 0, 1),
                                       new primitives.Point(1, 0, 0),
                                       new primitives.Point(0, 1, 0),
                                       new primitives.Point(0.5, 0.25, 0.5)), //
                     "Constructed a concave polygon");

        // =============== Boundary Values Tests ==================

        // TC5: Vertex on a side of a quadrangular
        assertThrows(IllegalArgumentException.class, //
                     () -> new Polygon(new primitives.Point(0, 0, 1),
                                       new primitives.Point(1, 0, 0),
                                       new primitives.Point(0, 1, 0),
                                       new primitives.Point(0, 0.5, 0.5)),
                     "Constructed a polygon with vertix on a side");

        // TC6: Last point = first point
        assertThrows(IllegalArgumentException.class, //
                     () -> new Polygon(new primitives.Point(0, 0, 1),
                                       new primitives.Point(1, 0, 0),
                                       new primitives.Point(0, 1, 0),
                                       new primitives.Point(0, 0, 1)),
                     "Constructed a polygon with vertice on a side");

        // TC7: Co-located points
        assertThrows(IllegalArgumentException.class, //
                     () -> new Polygon(new primitives.Point(0, 0, 1),
                                       new primitives.Point(1, 0, 0),
                                       new primitives.Point(0, 1, 0),
                                       new primitives.Point(0, 1, 0)),
                     "Constructed a polygon with vertice on a side");
    }

    /**
     * Test method for {@link Polygon#getNormal(Point)}.
     */
    @org.junit.jupiter.api.Test
    public void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: There is a simple single test here
        Polygon pl = new Polygon(new primitives.Point(0, 0, 1),
                                 new primitives.Point(1, 0, 0),
                                 new primitives.Point(0, 1, 0),
                                 new primitives.Point(-1, 1, 1));
        double sqrt3 = Math.sqrt(1d / 3);
        assertEquals(new primitives.Vector(sqrt3, sqrt3, sqrt3),
                     pl.getNormal(new primitives.Point(0, 0, 1)), "Bad normal to triangle");
    }

}