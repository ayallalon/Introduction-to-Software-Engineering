package primitives;


import static org.junit.jupiter.api.Assertions.assertEquals;


class PointTest {

    Point p1 = new Point(1, 2, 3);

    /**
     * Test method for {@link Point#distanceSquared(Point)}
     */
    @org.junit.jupiter.api.Test
    void testDistanceSquared() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: Simple test
        Point p2 = new Point(3, 4, 5);

        assertEquals(12, p1.distanceSquared(p2), "ERROR: wrong distance squared.");
    }

    /**
     * Test method for {@link Point#add(Vector) }
     */
    @org.junit.jupiter.api.Test
    void testAdd() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: Simple test
        assertEquals(new Point(0, 0, 0), p1.add(new Vector(-1, -2, -3)),
                     "ERROR: Point + Vector does not work correctly");
    }

    /**
     * Test method for {@link Point#subtract(Point) }
     */
    @org.junit.jupiter.api.Test
    void testSubtract() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: Simple test
        assertEquals(new Vector(1, 1, 1), new Point(2, 3, 4).subtract(p1),
                     "ERROR: Point - Vector does not work correctly");
    }

    /**
     * Test method for {@link Point#distance(Point) }
     */
    @org.junit.jupiter.api.Test
    void testDistance() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: Simple test
        Point p2 = new Point(3, 4, 5);
        assertEquals(Math.sqrt(12), p1.distance(p2), "ERROR: wrong distance between points.");
    }
}