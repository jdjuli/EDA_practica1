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
    private static final char ETX = 0x03; //End of text
    
    private Map<Character,Integer> frequencyTable;
    private BinaryTree<HuffmanNode> codeTree;
    
    private HashMap<Character,BitArray> encodingTable;            
    
    
    public static void main(String[] args){
        PriorityQueue<LinkedBinaryTree<HuffmanNode>> pq = new PriorityQueue<>(new BinaryTreeComparator());
        LinkedBinaryTree<HuffmanNode> t = new LinkedBinaryTree<>();
        t.addRoot(new HuffmanNode(3,'a'));
        pq.add(t);
        t = new LinkedBinaryTree<>();
        t.addRoot(new HuffmanNode(1,'z'));
        pq.add(t);
        t = new LinkedBinaryTree<>();
        t.addRoot(new HuffmanNode(2,'m'));
        pq.add(t);        
        
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
        BitArrayWriter baw = new BitArrayWriter();
        for(char c : text.toCharArray()){
            BitArray code;
            code = encodingTable.get(c);
            if(code != null){
                baw.write(code);
            }else{
                throw new RuntimeException("String cannot be encoded with this instance of HuffmanTree, check that the instance has been initialized with it and try again");
            }
        }
        baw.write(encodingTable.get(ETX));
        byte[] ret = baw.toArray();
        return ret;
    }
    
    /**
     * Decodes a binary array into a text using a Huffman tree.
     * @param code
     * @return 
     */
    String decoding(byte [] code) {
        BitArrayReader bar = new BitArrayReader(code);
        Position<HuffmanNode> position;
        Character readedChar;
        StringBuffer returnBuffer = new StringBuffer();
        do{
            position = codeTree.root();
            while( !codeTree.isLeaf(position) ){
                int readed = bar.readBit();
                if(readed == 0){
                    position = codeTree.left(position);
                }else{
                    position = codeTree.right(position);
                }
            }
            readedChar = position.getElement().getCharacter();
            returnBuffer.append(readedChar);
        }while( readedChar != ETX );
        returnBuffer.setLength(returnBuffer.length()-1); //remove the ETX char
        return returnBuffer.toString();
    }

    private void buildFrequencyTable(String text) {
        frequencyTable.put(ETX,1);
        for(Character c : text.toCharArray()){
            if(!frequencyTable.containsKey(c)){
                frequencyTable.put(c, 1);
            }else{
                frequencyTable.replace(c, frequencyTable.get(c) + 1);
            }
        }
        //Add the ETX char to the frequency table
    }
    
    private void buildCodeTree(){
        PriorityQueue<LinkedBinaryTree<HuffmanNode>> priorityq = new PriorityQueue<>(new BinaryTreeComparator());
        for(Map.Entry<Character,Integer> e : frequencyTable.entrySet()){
            LinkedBinaryTree auxTree = new LinkedBinaryTree();
            auxTree.addRoot(new HuffmanNode(e.getValue(),e.getKey()));
            priorityq.offer(auxTree);
        }
        
        while(priorityq.size() > 1){
            LinkedBinaryTree<HuffmanNode> t1 = priorityq.remove();
            LinkedBinaryTree<HuffmanNode> t2 = priorityq.remove();
            LinkedBinaryTree<HuffmanNode> merge = new LinkedBinaryTree<>();
            int sum = t1.root().getElement().getValue();
            sum += t2.root().getElement().getValue();
            HuffmanNode cp = new HuffmanNode(sum);
            merge.addRoot(cp);
            Position<HuffmanNode> root = merge.root();
            merge.attachLeft(root, t1);
            merge.attachRight(root, t2);
            priorityq.offer(merge);
        }  
        
        codeTree = priorityq.remove();  
    }
    
    private void buildEncodingTable(){
        if(codeTree.isEmpty()) throw new RuntimeException("Cannot extract a encoding table from an empty tree");
        Stack<Pair<Position<HuffmanNode>,BitArray>> positions = new Stack<>();
        
        positions.push(new Pair(codeTree.root(),new BitArray()));
        while(!positions.isEmpty()){
            Pair<Position<HuffmanNode>,BitArray> pos = positions.pop();
            
            if(codeTree.hasLeft(pos.getFirst())){
                BitArray newCode = pos.getSecond().clone();
                newCode.pushBitRight(0);
                //int newCode = pos.getSecond()<<1;
                positions.push(new Pair(codeTree.left(pos.getFirst()),newCode) );
            }
            if(codeTree.hasRight(pos.getFirst())){
                BitArray newCode = pos.getSecond().clone();
                newCode.pushBitRight(1);
                //int newCode = (pos.getSecond()<<1) | 1;
                positions.push(new Pair(codeTree.right(pos.getFirst()),newCode) );
            }
            if(codeTree.isLeaf(pos.getFirst())){    
                encodingTable.put(pos.getFirst().getElement().getCharacter(), pos.getSecond());
            }
        }      
    }
    
    private static class HuffmanNode implements Comparable<HuffmanNode>{

        int value;
        char character;

        public HuffmanNode(int value, char character) {
            this(value);
            this.character = character;
        }
        
        public HuffmanNode(int value) {
            this.value = value;
        }
        
        @Override
        public int compareTo(HuffmanNode o) {
            if(o == null) throw new RuntimeException("Cannot compare with null");
            return Integer.compare(getValue(), o.getValue());
        }

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }

        public char getCharacter() {
            return character;
        }

        public void setCharacter(char character) {
            this.character = character;
        }
        
    }
    
    
    private static class BinaryTreeComparator implements Comparator<LinkedBinaryTree>{
        @Override
        public int compare(LinkedBinaryTree o1, LinkedBinaryTree o2) {
            if(o1.isEmpty() || o2.isEmpty()) throw new RuntimeException("Empty trees cannot be compared");
            try{
                Comparable c1 = (Comparable) o1.root().getElement();
                Comparable c2 = (Comparable) o2.root().getElement();
                return c1.compareTo(c2);
            }catch(ClassCastException cce){
                throw new RuntimeException("The trees must contain Comparable values");
            }
        }
    }
    
}
