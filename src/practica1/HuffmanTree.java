package practica1;

import java.util.Comparator;
import java.util.HashMap;
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

    /**
     * End Of Text character used to stop the decoding process when the end of
     * the String is reached
     */
    private static final char ETX = 0x03; //End of text

    /**
     * Map of each character and its frequency of apparition
     */
    private Map<Character, Integer> frequencyTable;

    /**
     * Tree that determines the codes assigned to each char, it's used both for
     * encoding and decoding
     */
    private BinaryTree<HuffmanNode> codeTree;

    /**
     * Map that associates the sequence of bits to its corresponding character.
     * It's main use is make the encoding process more efficient.
     */
    private HashMap<Character, BitArray> encodingTable;

    /**
     * Creates the Huffman tree.
     *
     * @param text initial String to initialize the data structures. Further
     * calls to {@link #encoding(java.lang.String)} cannot contain a character
     * that is not present on this String
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
     *
     * @param text String with the message to be encoded
     * @return byte array containing the concatenation of the binary codes of
     * the characters of the String provided
     */
    byte[] encoding(String text) {
        BitArrayWriter baw = new BitArrayWriter();
        for (char c : text.toCharArray()) {
            BitArray code;
            code = encodingTable.get(c);
            if (code != null) {
                baw.write(code);
            } else {
                throw new RuntimeException("String cannot be encoded with this instance of HuffmanTree, check that the instance has been initialized with it and try again");
            }
        }
        baw.write(encodingTable.get(ETX));
        byte[] ret = baw.toArray();
        return ret;
    }

    /**
     * Decodes a binary array into a text using a Huffman tree.
     *
     * @param code Array of bytes that contains the concatenation of the
     * character codes
     * @return The original String message coded into the array passed as
     * parameter.
     */
    String decoding(byte[] code) {
        BitArrayReader bar = new BitArrayReader(code);
        Position<HuffmanNode> position;
        Character readedChar;
        StringBuilder returnBuffer = new StringBuilder();
        do {
            position = codeTree.root();
            while (!codeTree.isLeaf(position)) {
                int readed = bar.readBit();
                if (readed == 0) {
                    position = codeTree.left(position);
                } else {
                    position = codeTree.right(position);
                }
            }
            readedChar = position.getElement().getCharacter();
            returnBuffer.append(readedChar);
        } while (readedChar != ETX);
        returnBuffer.setLength(returnBuffer.length() - 1); //remove the ETX char
        return returnBuffer.toString();
    }

    /**
     * Fills the hash map that contains pairs of each character and the number
     * of times it has appeared
     *
     * @param text String containing the characters
     */
    private void buildFrequencyTable(String text) {
        //Add the ETX char to the frequency table
        frequencyTable.put(ETX, 1);
        for (Character c : text.toCharArray()) {
            if (!frequencyTable.containsKey(c)) {
                frequencyTable.put(c, 1);
            } else {
                frequencyTable.replace(c, frequencyTable.get(c) + 1);
            }
        }
    }

    /**
     * Builds the symbols tree used for encoding and decoding
     */
    private void buildCodeTree() {
        PriorityQueue<LinkedBinaryTree<HuffmanNode>> priorityq = new PriorityQueue<>(new BinaryTreeComparator());
        for (Map.Entry<Character, Integer> e : frequencyTable.entrySet()) {
            LinkedBinaryTree auxTree = new LinkedBinaryTree();
            auxTree.addRoot(new HuffmanNode(e.getValue(), e.getKey()));
            priorityq.offer(auxTree);
        }

        while (priorityq.size() > 1) {
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

    /**
     * This method fills a hash table with pairs of character and its binary
     * encoded representation.
     */
    private void buildEncodingTable() {
        if (codeTree.isEmpty()) {
            throw new RuntimeException("Cannot extract a encoding table from an empty tree");
        }
        Stack<Pair<Position<HuffmanNode>, BitArray>> positions = new Stack<>();

        positions.push(new Pair(codeTree.root(), new BitArray()));
        while (!positions.isEmpty()) {
            Pair<Position<HuffmanNode>, BitArray> pos = positions.pop();

            if (codeTree.hasLeft(pos.getFirst())) {
                BitArray newCode = pos.getSecond().clone();
                newCode.pushBitRight(0);
                //int newCode = pos.getSecond()<<1;
                positions.push(new Pair(codeTree.left(pos.getFirst()), newCode));
            }
            if (codeTree.hasRight(pos.getFirst())) {
                BitArray newCode = pos.getSecond().clone();
                newCode.pushBitRight(1);
                //int newCode = (pos.getSecond()<<1) | 1;
                positions.push(new Pair(codeTree.right(pos.getFirst()), newCode));
            }
            if (codeTree.isLeaf(pos.getFirst())) {
                encodingTable.put(pos.getFirst().getElement().getCharacter(), pos.getSecond());
            }
        }
    }

    /**
     * This class represents a node of the tree of codes used in Huffman coding.
     * It can be used as a character node and also as a internal node, setting
     * the character attribute to null.
     */
    private static class HuffmanNode implements Comparable<HuffmanNode> {

        private int value;
        private char character;

        public HuffmanNode(int value, char character) {
            this(value);
            this.character = character;
        }

        public HuffmanNode(int value) {
            this.value = value;
        }

        /**
         * The comparision between two HuffmanNode's its made by comparing their
         * corresponding values with the natural comparator (ascending order)
         *
         * @param o
         * @return
         */
        @Override
        public int compareTo(HuffmanNode o) {
            if (o == null) {
                throw new RuntimeException("Cannot compare with null");
            }
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

    /**
     * Auxiliar class that makes possible comparing LinkedBinaryTree's by the
     * value of their root nodes.
     */
    private static class BinaryTreeComparator implements Comparator<LinkedBinaryTree> {

        @Override
        public int compare(LinkedBinaryTree o1, LinkedBinaryTree o2) {
            if (o1.isEmpty() || o2.isEmpty()) {
                throw new RuntimeException("Empty trees cannot be compared");
            }
            try {
                Comparable c1 = (Comparable) o1.root().getElement();
                Comparable c2 = (Comparable) o2.root().getElement();
                return c1.compareTo(c2);
            } catch (ClassCastException cce) {
                throw new RuntimeException("The trees must contain Comparable values");
            }
        }
    }

}
