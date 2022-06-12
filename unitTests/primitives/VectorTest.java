package primitives;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class VectorTest {

    @Test
    void testLengthSquared() {
        Vector v1 = new Vector(1.0, 1.0, 1.0);
        Vector v2 = new Vector(-1.0, -1.0, -1.5);

        v1 = v1.add(v2);
        assertEquals(new Vector(0.0, 0.0, -0.5), v1);

        v2 = v2.add(v1);
        assertEquals(new Vector(-1.0, -1.0, -2.0), v2);
    }

    @Test
    void testDotProduct() {

    }

    @Test
    void testCrossProduct() {
    }

    @Test
    void testNormalize() {
    }
}