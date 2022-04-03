package primitives;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.LinkedList;
import java.util.List;
import org.junit.jupiter.api.Test;

class RayTest {

    @Test
    void testFindClosestPoint() {

        // ============ Equivalence Partitions Tests ==============

        Ray ray = new Ray(new Point(0, 0, 10), new Vector(1, 10, -100));

        List<Point> list = new LinkedList<Point>();
        list.add(new Point(1, 1, -100));
        list.add(new Point(-1, 1, -99));
        list.add(new Point(0, 2, -10));
        list.add(new Point(0.5, 0, -100));

        assertEquals(list.get(2), ray.findClosestPoint(list),
                     "testFindClosestPoint => doesn't work properly");

        // =============== Boundary Values Tests ==================

        // TC01: the list is empty.

        List<Point> list2 = null;
        assertNull(ray.findClosestPoint(list2), "testFindClosestPoint => doesn't work properly");

        // TC02: the closest point is the first one.

        List<Point> list3 = new LinkedList<Point>();
        list3.add(new Point(0, 2, -10));
        list3.add(new Point(-1, 1, -99));
        list3.add(new Point(1, 1, -100));
        list3.add(new Point(0.5, 0, -100));

        assertEquals(list3.get(0), ray.findClosestPoint(list3),
                     "testFindClosestPoint => doesn't work properly");


        // TC03: the closest pont is the last one.

        List<Point> list4 = new LinkedList<Point>();
        list4.add(new Point(1, 1, -100));
        list4.add(new Point(0.5, 0, -100));
        list4.add(new Point(-1, 1, -99));
        list4.add(new Point(0, 2, -10));

        assertEquals(list4.get(3), ray.findClosestPoint(list4),
                     "testFindClosestPoint => doesn't work properly");
    }
}