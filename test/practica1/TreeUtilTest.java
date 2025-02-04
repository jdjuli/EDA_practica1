/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package practica1;

import material.Position;
import material.tree.narytree.LinkedTree;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author jvelez
 */
public class TreeUtilTest {

    public TreeUtilTest() {
    }

    /**
     * Test of clone method, of class TreeUtil.
     */
    @Test
    public void testClone() {
        System.out.println("clone");

        LinkedTree<Integer> tree1 = new LinkedTree<>();
        Position<Integer> root = tree1.addRoot(3);
        Position<Integer> aux1 = tree1.add(1, root);
        Position<Integer> aux2 = tree1.add(5, root);
        Position<Integer> pos = tree1.add(7, aux2);

        LinkedTree<Integer> tree2 = new LinkedTree<>();

        TreeUtil.clone(tree1, tree2);

        assertEquals(3, tree2.root().getElement().intValue());

        StringBuilder expected1 = new StringBuilder();
        treeToString(tree1, expected1);

        StringBuilder expected2 = new StringBuilder();
        treeToString(tree2, expected2);
        assertEquals(expected1.toString(), expected2.toString());
    }

    @Test
    public void testClone2() {
        System.out.println("clone2");
        LinkedTree<Integer> tree1 = new LinkedTree<>();
        /*
                    1
            +-------+-------+
            |       |       |
            2       3       4
            +---+           +---+
            |   |           |   |
            5   6           7   8
         */
        Position<Integer> root = tree1.addRoot(1);
        Position<Integer> item_2 = tree1.add(2, root);
        Position<Integer> item_3 = tree1.add(3, root);
        Position<Integer> item_4 = tree1.add(4, root);
        Position<Integer> item_5 = tree1.add(5, item_2);
        Position<Integer> item_6 = tree1.add(6, item_2);
        Position<Integer> item_7 = tree1.add(7, item_4);
        Position<Integer> item_8 = tree1.add(8, item_4);
        //Test clone
        LinkedTree<Integer> tree2 = new LinkedTree<>();
        TreeUtil.clone(tree1, tree2);
        //Convert tree1 to string for equality test
        StringBuilder expected1 = new StringBuilder();
        treeToString(tree1, expected1);
        //Convert tree2 to string for equality test
        StringBuilder expected2 = new StringBuilder();
        treeToString(tree2, expected2);
        //Assert equality
        assertEquals(expected1.toString(), expected2.toString());
    }

    @Test
    public void testClone3() {
        System.out.println("clone3");
        LinkedTree<Integer> tree1 = new LinkedTree<>();
        /*
                                                                       a
                                                                 +-----+--+
                                                                 b     x  x
                                                     +-----+--+--+
                                                     c     x  x  d
                       +--------+--+--------+--------+           +--+
                       e        x  x        f        g           x  x
           +-----+-----+              +--+--+--+     +
           h     i     j              x  x  x  x     k
        +--+--+  +--+  +--+--+                    +--+--+--+
        x  x  x  x  x  x  x  x                    x  x  x  x
         */
        //Root
        Position<Integer> a = tree1.addRoot(1);
        //Depth = 2;
        Position<Integer> b = tree1.add(2, a);
        tree1.add(3, a);
        tree1.add(4, a);
        Position<Integer> c = tree1.add(5, b);
        tree1.add(6, b);
        tree1.add(7, b);
        Position<Integer> d = tree1.add(8, b);
        //Depth = 3
        Position<Integer> e = tree1.add(9, c);
        tree1.add(10, c);
        tree1.add(11, c);
        Position<Integer> f = tree1.add(12, c);
        Position<Integer> g = tree1.add(13, c);
        tree1.add(14, d);
        tree1.add(15, d);
        //Depth = 4
        Position<Integer> h = tree1.add(16, e);
        Position<Integer> i = tree1.add(17, e);
        Position<Integer> j = tree1.add(18, e);
        tree1.add(19, f);
        tree1.add(20, f);
        tree1.add(21, f);
        tree1.add(22, f);
        Position<Integer> k = tree1.add(23, g);
        //Depth = 5
        tree1.add(24, h);
        tree1.add(25, h);
        tree1.add(26, h);
        tree1.add(27, i);
        tree1.add(28, i);
        tree1.add(29, j);
        tree1.add(30, j);
        tree1.add(31, j);
        tree1.add(32, k);
        tree1.add(33, k);
        tree1.add(34, k);
        tree1.add(35, k);
        LinkedTree<Integer> tree2 = new LinkedTree<>();
        TreeUtil.clone(tree1, tree2);

        StringBuilder exp1 = new StringBuilder();
        StringBuilder exp2 = new StringBuilder();

        treeToString(tree1, exp1);
        treeToString(tree2, exp2);
        assertEquals(exp1.toString(), exp2.toString());
    }

    private void treeToString(LinkedTree<Integer> tree1, StringBuilder expected) {
        for (Position<Integer> i : tree1) {
            expected.append(i.getElement().intValue());
        }
    }

    /**
     * Test of rearranger method, of class TreeUtil.
     */
    @Test
    public void testRearranger() {
        System.out.println("rearranger");

        LinkedTree<Integer> tree = new LinkedTree<>();
        Position<Integer> root = tree.addRoot(3);
        Position<Integer> aux1 = tree.add(1, root);
        Position<Integer> aux2 = tree.add(5, root);
        Position<Integer> pos = tree.add(7, aux2);

        TreeUtil.rearranger(tree, pos);

        assertEquals(pos, tree.root());
        assertEquals(tree.parent(aux2), tree.root());
        assertEquals(tree.parent(tree.parent(root)), tree.root());
        assertEquals(tree.parent(tree.parent(tree.parent(aux1))), tree.root());
    }

    @Test
    public void testRearranger2() {
        /*
                    1
            +-------+-------+
            |       |       |
            2       3       4
            +---+           +---+
            |   |           |   |
            5   6           7   8
        
                |||     |||
                \|/     \|/
        
                    7
                    |
                    4
                    +---+
                    1   8
                +---+
                3   2
                    +---+
                    5   6
                       
         */
        LinkedTree<Integer> tree = new LinkedTree<>();
        Position<Integer> item_1 = tree.addRoot(1);
        Position<Integer> item_2 = tree.add(2, item_1);
        Position<Integer> item_3 = tree.add(3, item_1);
        Position<Integer> item_4 = tree.add(4, item_1);
        Position<Integer> item_5 = tree.add(5, item_2);
        Position<Integer> item_6 = tree.add(6, item_2);
        Position<Integer> item_7 = tree.add(7, item_4);
        Position<Integer> item_8 = tree.add(8, item_4);

        TreeUtil.rearranger(tree, item_7);

        assertEquals(item_7, tree.root());
        assertEquals(item_7, tree.parent(item_4));
        assertEquals(item_4, tree.parent(item_8));
        assertEquals(item_4, tree.parent(item_1));
        assertEquals(item_1, tree.parent(item_2));
        assertEquals(item_1, tree.parent(item_3));
        assertEquals(item_2, tree.parent(item_5));
        assertEquals(item_2, tree.parent(item_6));
    }
}
