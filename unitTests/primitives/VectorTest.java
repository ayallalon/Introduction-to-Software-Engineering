package primitives;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static primitives.Util.isZero;

import org.junit.jupiter.api.Test;

class VectorTest {
    Vector v1 = new Vector(1, 2, 3);
    Vector v2 = new Vector(-2, -4, -6);
    Vector v3 = new Vector(0, 3, -2);

    /**
     * Test method for {@link Vector#lengthSquared()}
     */
    @org.junit.jupiter.api.Test
    void testLengthSquared() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: Simple test
        assertEquals(14, v1.lengthSquared(), 0.00001,
                     "ERROR: lengthSquared() wrong value");
    }

    /**
     * Test method for {@link Vector#length()}
     */
    @org.junit.jupiter.api.Test
    void testLength() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: Simple test
        assertEquals(5d, new Vector(0, 3, 4).length(),
                     "ERROR: length() wrong value");
    }

    /**
     * Test method for {@link primitives.Vector#dotProduct(primitives.Vector)}.
     */
    @Test
    void testDotProduct() {

        // =============== Boundary Values Tests ==================
        // TC1: dotProduct for orthogonal vectors
        assertEquals(0d, v1.dotProduct(v3), 0.00001,
                     "dotProduct() for orthogonal vectors is not zero");

        // ============ Equivalence Partitions Tests ==============
        // TC01: Simple dotProduct test
        assertTrue(isZero(v1.dotProduct(v2) + 28),
                   "ERROR: dotProduct() wrong value");
    }

    /**
     * Test method for {@link primitives.Vector#crossProduct(primitives.Vector)}.
     */
    @org.junit.jupiter.api.Test
    public void testCrossProduct() {


        // ============ Equivalence Partitions Tests ==============
        // TC01: Test that length of cross-product is proper (orthogonal vectors taken for simplicity)
        Vector vr = v1.crossProduct(v3);
        assertEquals(v1.length() * v3.length(), vr.length(), 0.00001,
                     "crossProduct() wrong result length");

        // TC02: Test cross-product result orthogonality to its operands
        assertTrue(isZero(vr.dotProduct(v1)),
                   "crossProduct() result is not orthogonal to 1st operand");
        assertTrue(isZero(vr.dotProduct(v3)),
                   "crossProduct() result is not orthogonal to 2nd operand");

        // =============== Boundary Values Tests ==================
        // TC1: test zero vector from cross-product of co-lined vectors
        assertThrows(IllegalArgumentException.class, () -> v1.crossProduct(v2),
                     "crossProduct() for parallel vectors does not throw an exception");
    }

    /**
     * Test method for {@link Vector#scale(double)}
     */
    @org.junit.jupiter.api.Test
    void testScale() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: Simple test
        assertEquals(new Vector(2, 4, 6),
                     v1.scale(2),
                     "Wrong vector scale");
        // =============== Boundary Values Tests ==================
        // TC1: test scaling to 0
        assertThrows(IllegalArgumentException.class, () -> v1.scale(0d),
                     "Scale by 0 must throw exception");
    }


    /**
     * Test method for {@link Vector#add(Vector)}
     */
    @org.junit.jupiter.api.Test
    void testAdd() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: Simple test
        assertEquals(new Vector(1, 1, 1),
                     new Vector(3, 5, 7).add(v2),
                     "Wrong vector add");

        // =============== Boundary Values Tests ==================
        // TC1: test adding v + (-v)
        assertThrows(IllegalArgumentException.class,
                     () -> v1.add(new Vector(-1, -2, -3)),
                     "Add v plus -v must throw exception");
    }

    /**
     * Test method for {@link Vector#normalize()}
     */
    @org.junit.jupiter.api.Test
    void testNormalize() {
        Vector v = new Vector(0, 3, 4);
        Vector n = v.normalize();
        // ============ Equivalence Partitions Tests ==============
        // TC01: Simple test
        assertFalse(v == n, "normalized() changes the vector itself");
        // TC02: Simple test
        assertEquals(1d, n.lengthSquared(), 0.00001, "wrong normalized vector length");
        // TC03: Simple test
        assertThrows(IllegalArgumentException.class, () -> v.crossProduct(n),
                     "normalized vector is not in the same direction");
        // TC04: Simple test
        assertEquals(new Vector(0, 0.6, 0.8), n, "wrong normalized vector");
    }
}