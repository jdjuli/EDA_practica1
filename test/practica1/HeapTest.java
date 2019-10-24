package practica1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author jvelez
 */
public class HeapTest {

    public HeapTest() {
    }

    /**
     * Test of add method, of class Heap.
     */
    @Test
    public void testHeap() {
        System.out.println("add");
        Heap<Integer> instance = new Heap<>();

        assertTrue(instance.isEmpty());

        instance.add(5);
        assertFalse(instance.isEmpty());

        instance.add(2);
        instance.add(3);
        instance.add(7);
        instance.add(1);
        instance.add(6);

        assertFalse(instance.isEmpty());

        assertEquals(7, instance.remove().intValue());
        assertFalse(instance.isEmpty());
        assertEquals(6, instance.remove().intValue());
        assertFalse(instance.isEmpty());
        assertEquals(5, instance.remove().intValue());
        assertFalse(instance.isEmpty());
        assertEquals(3, instance.remove().intValue());
        assertFalse(instance.isEmpty());
        assertEquals(2, instance.remove().intValue());
        assertFalse(instance.isEmpty());
        assertEquals(1, instance.remove().intValue());
        assertTrue(instance.isEmpty());
    }

    @Test
    public void testHeapRandom() {
        final int TEST_SIZE = 1000000;

        Heap<Integer> heap = new Heap();
        assertTrue(heap.isEmpty());
        Integer[] numbers = new Integer[TEST_SIZE];
        Random rand = new Random(System.currentTimeMillis());

        for (int i = 0; i < TEST_SIZE; i++) {
            int r = rand.nextInt();
            heap.add(r);
            numbers[i] = r;
        }

        Arrays.sort(numbers);

        for (int i = TEST_SIZE - 1; i >= 0; i--) {
            assertEquals(numbers[i], heap.remove());
        }
    }

    @Test
    public void testHeapRandomReducedDomain() {
        final int TEST_SIZE = 1000000;

        Heap<Integer> heap = new Heap();
        assertTrue(heap.isEmpty());
        Integer[] numbers = new Integer[TEST_SIZE];
        Random rand = new Random(System.currentTimeMillis());

        for (int i = 0; i < TEST_SIZE; i++) {
            int r = rand.nextInt(25);
            heap.add(r);
            numbers[i] = r;
        }

        Arrays.sort(numbers);

        for (int i = TEST_SIZE - 1; i >= 0; i--) {
            assertEquals(numbers[i], heap.remove());
        }
    }

    @Test
    public void testHeapMultipleAdditionAndRemoval() {
        Heap<Integer> heap = new Heap();
        ArrayList<Integer> numbers = new ArrayList<>();

        testAdditionAndRemoval(heap, numbers, 100000, 50000);
        testAdditionAndRemoval(heap, numbers, 0, 25000);
        testAdditionAndRemoval(heap, numbers, 75000, 100000);
    }

    private void testAdditionAndRemoval(Heap heap, ArrayList<Integer> numbers, int addition, int removal) {
        Random rand = new Random(System.currentTimeMillis());
        for (int i = 0; i < addition; i++) {
            int r = rand.nextInt();
            heap.add(r);
            numbers.add(r);
        }
        numbers.sort(Comparator.naturalOrder());
        for (int i = 0; i < removal; i++) {
            assertEquals(numbers.remove(numbers.size() - 1), heap.remove());
        }
    }
}
