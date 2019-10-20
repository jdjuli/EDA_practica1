package practica1;

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
        Heap <Integer> instance = new Heap<>();

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
    public void testHeap2(){
        Heap<Integer> heap = new Heap();
        assertTrue(heap.isEmpty());
        Integer[] numbers = new Integer[10];
        Random rand = new Random(System.currentTimeMillis());
        for(int i = 0; i < 10; i++){
            int r = rand.nextInt(100);
            heap.add(r);
            numbers[i] = r;
        }
        
        Arrays.sort(numbers);
        
        for(int i = 9 ; i >= 0 ; i--){
            if(!numbers[i].equals(heap.top())){
                System.out.println(i);
            }
            assertEquals(numbers[i],heap.remove());
        }
    }
}
