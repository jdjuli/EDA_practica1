package practica1;

import java.util.LinkedList;
import java.util.Queue;
import material.Position;

/**
 *
 * @author jvelez
 * @param <T>
 */
public class Heap <T extends Comparable> extends ArrayBinaryTree<T> {
    
    int size;
    
    public Heap(){
        super();
    }
    
    /**
     * Adds an element to the heap.
     * @param element 
     */
    public void add(T element) {
        ArrayBinaryTree<T>.Node<T> node = new ArrayBinaryTree<T>.Node<>(this, element, size);
        array.add(size,node);
        adjustBottomUp(node);
        size++;
    }
            
    
    /**
     * Returns the top element of the heap.
     * @return 
     */
    public T top() {
        return root().getElement();        
    }

    /**
     * Removes the top element of the heap and returns it.
     * @return 
     */
    public T remove() {
        if(isEmpty()) throw new RuntimeException("Heap is empty");
        Position<T> root = root();
        Position<T> replacement = getLastPosition();
        T value = root.getElement();
        swap(root,replacement);
        remove(replacement);
        if(!isEmpty()){
            adjustTopDown();
        }
        size--;
        return value;
    }

    /**
    * 
    * @return True if the heap is empty.
    */
    //boolean isEmpty() {
    @Override
    public boolean isEmpty() {
        return super.isEmpty();
    }
    
    private void adjustTopDown(){
        Queue<Position<T>> queue = new LinkedList<>();
        queue.add(root());
        while(!queue.isEmpty() && !isLeaf(queue.peek()) ){
            Position<T> main = queue.remove();
            Position<T> left = hasLeft(main) ? left(main) : null;
            Position<T> right = hasRight(main) ? right(main) : null;
            Position<T> toSwap = null;
            if(left!=null && right != null){
                if( canSwap(main,right) ){
                    if( canSwap(main,left) ){
                        toSwap = max(left,right);
                    }else{
                        toSwap = right;
                    }
                }else if( canSwap(main,right) ){
                    toSwap = left;
                }
            }else if(right == null && canSwap(main,left)){
                toSwap = left;
            }
            if(toSwap != null){
                swap(main,toSwap);
                queue.add(toSwap);
            }
        }
    }
    
    private void adjustBottomUp(Position<T> source){
        Queue<Position<T>> queue = new LinkedList<>();
        queue.add(source);
        while(!queue.isEmpty() && !isRoot(queue.peek())){
            Position<T> main = queue.remove();
            Position<T> parent = parent(main);
            if(canSwap(parent,main)){
                swap(parent,main);
                queue.add(parent);
            }
        }
    }
    
    /**
     * Tests if (p1 <= p2) and items of both positions must be swapped
     * @param p1
     * @param p2
     * @return 
     */
    private boolean canSwap(Position<T> p1, Position<T> p2){
        return p1.getElement().compareTo(p2.getElement()) <= 0;
    }
    
    private Position<T> max(Position<T> p1, Position<T> p2){
        return p1.getElement().compareTo(p2.getElement()) > 0 ? p1 : p2;
    }
    
    private Position<T> getLastPosition(){
        if(isEmpty()) throw new RuntimeException("Heap is empty");
        return array.get(size-1);
    }
}
