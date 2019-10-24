package practica1;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

/**
 *
 * @author jvelez
 * @param <T>
 */
public class Heap<T extends Comparable> {

    private final ArrayList<T> array;

    public Heap() {
        array = new ArrayList<>();
    }

    /**
     * Adds an element to the heap.
     *
     * @param element
     */
    public void add(T element) {
        array.add(element);
        adjustBottomUp(array.size() - 1);
    }

    /**
     * Returns the top element of the heap.
     *
     * @return
     */
    public T top() {
        if (array.isEmpty()) {
            throw new RuntimeException("Heap is empty");
        }
        return array.get(0);
    }

    /**
     * Removes the top element of the heap and returns it.
     *
     * @return
     */
    public T remove() {
        if (isEmpty()) {
            throw new RuntimeException("Heap is empty");
        }
        T value = array.get(0);
        swap(0, array.size() - 1);
        array.remove(array.size() - 1);
        if (!isEmpty()) {
            adjustTopDown();
        }
        return value;
    }

    /**
     *
     * @return True if the heap is empty.
     */
    public boolean isEmpty() {
        return array.isEmpty();
    }

    private void adjustTopDown() {
        Queue<Integer> queue = new LinkedList<>();
        queue.add(0);
        while (!queue.isEmpty() && !isLeaf(queue.peek())) {
            int main = queue.remove();
            int left = leftChild(main);
            int right = rightChild(main);
            int toSwap = -1;

            if (hasLeftChild(main) && hasRightChild(main)) {
                if (canSwap(main, left) || canSwap(main, right)) {
                    toSwap = max(left, right);
                }
            } else if (!hasRightChild(main) && canSwap(main, left)) {
                toSwap = left;
            }

            if (toSwap != -1) {
                swap(main, toSwap);
                queue.add(toSwap);
            }
        }
    }

    private void adjustBottomUp(int source) {
        Queue<Integer> queue = new LinkedList<>();
        queue.add(source);
        while (!queue.isEmpty() && (queue.peek() != 0)) {
            int main = queue.remove();
            int parent = parent(main);
            if (canSwap(parent, main)) {
                swap(parent, main);
                queue.add(parent);
            }
        }
    }

    /**
     * Tests if (p1 <= p2) and items of both positions must be swapped @param p1
     * @param p2 @return
     *
     */
    private boolean canSwap(int p1, int p2) {
        return array.get(p1).compareTo(array.get(p2)) <= 0;
    }

    private void swap(int p1, int p2) {
        T aux = array.get(p1);
        array.set(p1, array.get(p2));
        array.set(p2, aux);
    }

    private boolean isLeaf(int index) {
        if (index < array.size()) {
            return !hasLeftChild(index) && !hasRightChild(index);
        }
        throw new RuntimeException("Fix me");
    }

    private boolean hasRightChild(int index) {
        int rightPosition = rightChild(index);
        return rightPosition < array.size() && array.get(rightPosition) != null;
    }

    private boolean hasLeftChild(int index) {
        int leftPosition = leftChild(index);
        return leftPosition < array.size() && array.get(leftPosition) != null;
    }

    private int leftChild(int index) {
        return index * 2 + 1;
    }

    private int rightChild(int index) {
        return index * 2 + 2;
    }

    private int parent(int index) {
        if (index % 2 == 0) { // Is right child
            return (index - 2) / 2;
        } else { // Is left child
            return (index - 1) / 2;
        }
    }

    private int max(int p1, int p2) {
        return array.get(p1).compareTo(array.get(p2)) > 0 ? p1 : p2;
    }

}
