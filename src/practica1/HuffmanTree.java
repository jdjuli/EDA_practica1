package practica1;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.PriorityQueue;
import material.Position;
import material.tree.binarytree.BinaryTree;
import material.tree.binarytree.LinkedBinaryTree;
import material.utils.Pair;
import java.util.Stack;

/**
 *
 * @author jvelez
 */
public class HuffmanTree {
    
    
    private Map<Character,Integer> frequencyTable;
    private BinaryTree<ComparablePair<Character,Integer>> codeTree;
    
    private HashMap<Character,Integer> encodingTable;            
    
    
    public static void main(String[] args){
        HuffmanTree ht = new HuffmanTree("aaabbbbccd");
        System.out.println("Todo OK");
    }
    
    
    /**
     * Creates the Huffman tree.
     * @param text 
     */
    public HuffmanTree(String text) {
        frequencyTable = new HashMap<>();
        encodingTable = new HashMap<>();
        buildFrequencyTable(text);
        buildCodeTree();
        buildEncodingTable();
    }
    
    /**
     * Encodes a text into a binary array using a Huffman tree.
     * @param text
     * @return 
     */
    byte [] encoding(String text) {
        throw new RuntimeException("Not implemented yet");
    }
    
    /**
     * Decodes a binary array into a text using a Huffman tree.
     * @param code
     * @return 
     */
    String decoding(byte [] code) {
        throw new RuntimeException("Not implemented yet");
    }

    private void buildFrequencyTable(String text) {
        for(Character c : text.toCharArray()){
            if(!frequencyTable.containsKey(c)){
                frequencyTable.put(c, 1);
            }else{
                frequencyTable.replace(c, frequencyTable.get(c) + 1);
            }
        }
    }
    
    private void buildCodeTree(){
        PriorityQueue<LinkedBinaryTree<ComparablePair<Character,Integer>>> priorityq = new PriorityQueue<>(1, new BinaryTreeComparator());
        for(Map.Entry<Character,Integer> e : frequencyTable.entrySet()){
            LinkedBinaryTree auxTree = new LinkedBinaryTree();
            auxTree.addRoot(new ComparablePair<>(e.getKey(),e.getValue()));
            priorityq.add(auxTree);
        }
        
        while(priorityq.size() > 1){
            LinkedBinaryTree<ComparablePair<Character,Integer>> t1 = priorityq.poll();
            LinkedBinaryTree<ComparablePair<Character,Integer>> t2 = priorityq.poll();
            LinkedBinaryTree<ComparablePair<Character,Integer>> merge = new LinkedBinaryTree<>();
            int sum = t1.root().getElement().getSecond();
            sum += t2.root().getElement().getSecond();
            ComparablePair<Character,Integer> cp = new ComparablePair<>(null,sum);
            merge.addRoot(cp);
            Position<ComparablePair<Character,Integer>> root = merge.root();
            merge.attachLeft(root, t1);
            merge.attachRight(root, t2);
            priorityq.add(merge);
        }  
        
        codeTree = priorityq.poll();
    }
    
    private void buildEncodingTable(){
        if(codeTree.isEmpty()) throw new RuntimeException("Cannot extract a encoding table from an empty tree");
        Stack<Pair<Position<ComparablePair<Character,Integer>>,Integer>> positions = new Stack<>();
        
        positions.push(new Pair(codeTree.root(),0));
        while(!positions.isEmpty()){
            Pair<Position<ComparablePair<Character,Integer>>,Integer> pos = positions.pop();
            
            if(codeTree.hasLeft(pos.getFirst())){
                int newCode = pos.getSecond()<<1;
                positions.push(new Pair(codeTree.left(pos.getFirst()),newCode) );
            }
            if(codeTree.hasRight(pos.getFirst())){
                int newCode = (pos.getSecond()<<1) | 1;
                positions.push(new Pair(codeTree.right(pos.getFirst()),newCode) );
            }
            if(codeTree.isLeaf(pos.getFirst())){    
                encodingTable.put(pos.getFirst().getElement().getFirst(), pos.getSecond());
            }
        }      
    }
    
    /**
        Returns the concatenations of 1's and 0's contained as ints inside a Stack in the same order they have been pushed
    */
    private int stackToInt(Stack<Integer> s){
        Iterator<Integer> iter = s.iterator();
        int value = 0;
        while(iter.hasNext()){
            int next = iter.next();
            if(next == 0 || next == 1){
                value |= next;
                value <<=  1;
            }
        }
        return value;       
    }
    
    private static class BinaryTreeComparator implements Comparator<BinaryTree>{

        @Override
        public int compare(BinaryTree o1, BinaryTree o2) {
            if(o1.isEmpty() || o2.isEmpty()) throw new RuntimeException("Empty trees cannot be compared");
            try{
                Comparable c1 = (Comparable) o1.root().getElement();
                Comparable c2 = (Comparable) o1.root().getElement();
                return c1.compareTo(c2);
            }catch(ClassCastException cce){
                throw new RuntimeException("The trees must contain Comparable values");
            }
        }
    }
}
