/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package material.tree.binarytree;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import junit.framework.TestCase;
import material.Position;
import org.junit.Test;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;

/**
 *
 * @author J. Vélez, A. Duarte, J. Sánchez-Oro
 */
public class LinkedBinaryTreeTest {
    

    /**
     * Test of isEmpty method, of class LinkedBinaryTree.
     */
    @Test
    public void testIsEmpty() {
        LinkedBinaryTree<String> t = new LinkedBinaryTree<>();
        assertTrue(t.isEmpty());
        Position<String> p = t.addRoot("+");
        t.insertLeft(p, "2");
        Position<String> h = t.insertRight(p, "*");
        t.insertLeft(h, "3");
        t.insertRight(h, "5");
        assertFalse(t.isEmpty());
    }

    /**
     * Test of isInternal method, of class LinkedBinaryTree.
     */
    @Test
    public void testIsInternal() {
        LinkedBinaryTree<String> t = new LinkedBinaryTree<>();
        Position<String> p = t.addRoot("+");
        t.insertLeft(p, "2");
        Position<String> h = t.insertRight(p, "*");
        t.insertLeft(h, "3");
        Position<String> n5 = t.insertRight(h, "5");
        assertTrue(t.isInternal(h));
        assertFalse(t.isInternal(n5));
    }

    /**
     * Test of isLeaf method, of class LinkedBinaryTree.
     */
    @Test
    public void testIsLeaf() {
        LinkedBinaryTree<String> t = new LinkedBinaryTree<>();
        Position<String> p = t.addRoot("+");
        t.insertLeft(p, "2");
        Position<String> h = t.insertRight(p, "*");
        t.insertLeft(h, "3");
        Position<String> n5 = t.insertRight(h, "5");
        assertFalse(t.isLeaf(h));
        assertTrue(t.isLeaf(n5));
    }

    /**
     * Test of isRoot method, of class LinkedBinaryTree.
     */
    @Test
    public void testIsRoot() {
        LinkedBinaryTree<String> t = new LinkedBinaryTree<>();
        Position<String> p = t.addRoot("+");
        t.insertLeft(p, "2");
        Position<String> h = t.insertRight(p, "*");
        t.insertLeft(h, "3");
        Position<String> n5 = t.insertRight(h, "5");
        assertTrue(t.isRoot(p));
        assertFalse(t.isRoot(h));
    }

    /**
     * Test of hasLeft method, of class LinkedBinaryTree.
     */
    @Test
    public void testHasLeft() {
        LinkedBinaryTree<String> t = new LinkedBinaryTree<>();
        Position<String> p = t.addRoot("+");
        t.insertLeft(p, "2");
        Position<String> h = t.insertRight(p, "*");
        assertTrue(t.hasLeft(p));
        assertFalse(t.hasLeft(h));
    }

    /**
     * Test of hasRight method, of class LinkedBinaryTree.
     */
    @Test
    public void testHasRight() {
        LinkedBinaryTree<String> t = new LinkedBinaryTree<>();
        Position<String> p = t.addRoot("+");
        t.insertLeft(p, "2");
        Position<String> h = t.insertRight(p, "*");
        assertTrue(t.hasRight(p));
        assertFalse(t.hasRight(h));
    }

    /**
     * Test of root method, of class LinkedBinaryTree.
     */
    @Test
    public void testRoot() {
        LinkedBinaryTree<String> t = new LinkedBinaryTree<>();
        Position<String> p = t.addRoot("+");
        t.insertLeft(p, "2");
        Position<String> h = t.insertRight(p, "*");
        t.insertLeft(h, "3");
        Position<String> n5 = t.insertRight(h, "5");
        assertEquals(t.root(), p);
    }

    /**
     * Test of left method, of class LinkedBinaryTree.
     */
    @Test
    public void testLeft() {
        LinkedBinaryTree<String> t = new LinkedBinaryTree<>();
        Position<String> p = t.addRoot("+");
        Position<String> n2 = t.insertLeft(p, "2");
        Position<String> h = t.insertRight(p, "*");
        t.insertLeft(h, "3");
        Position<String> n5 = t.insertRight(h, "5");
        assertEquals(t.left(p), n2);
    }
    
    /**
     * Test of left method, of class LinkedBinaryTree.
     */
    @Test(expected = RuntimeException.class)
    public void testLeftException() {
        LinkedBinaryTree<String> t = new LinkedBinaryTree<>();
        Position<String> p = t.addRoot("+");
        Position<String> n2 = t.insertLeft(p, "2");
        Position<String> h = t.insertRight(p, "*");
        Position<String> n5 = t.insertRight(h, "5");
        t.left(h);
    }

    /**
     * Test of right method, of class LinkedBinaryTree.
     */
    @Test
    public void testRight() {
        LinkedBinaryTree<String> t = new LinkedBinaryTree<>();
        Position<String> p = t.addRoot("+");
        Position<String> n2 = t.insertLeft(p, "2");
        Position<String> h = t.insertRight(p, "*");
        t.insertLeft(h, "3");
        Position<String> n5 = t.insertRight(h, "5");
        assertEquals(t.right(p), h);
    }
    
    /**
     * Test of right method, of class LinkedBinaryTree.
     */
    @Test
    public void testRightException() {
       LinkedBinaryTree<String> t = new LinkedBinaryTree<>();
        Position<String> p = t.addRoot("+");
        Position<String> n2 = t.insertLeft(p, "2");
        Position<String> h = t.insertRight(p, "*");
        Position<String> n5 = t.insertLeft(h, "5");
        t.left(h);
    }

    /**
     * Test of parent method, of class LinkedBinaryTree.
     */
    @Test
    public void testParent() {
        LinkedBinaryTree<String> t = new LinkedBinaryTree<>();
        Position<String> p = t.addRoot("+");
        Position<String> n2 = t.insertLeft(p, "2");
        Position<String> h = t.insertRight(p, "*");
        t.insertLeft(h, "3");
        Position<String> n5 = t.insertRight(h, "5");
        assertEquals(t.parent(n2), p);
    }
    
    /**
     * Test of parent method, of class LinkedBinaryTree.
     */
    @Test(expected = RuntimeException.class)
    public void testParentException() {
        LinkedBinaryTree<String> t = new LinkedBinaryTree<>();
        Position<String> p = t.addRoot("+");
        Position<String> n2 = t.insertLeft(p, "2");
        Position<String> h = t.insertRight(p, "*");
        t.insertLeft(h, "3");
        Position<String> n5 = t.insertRight(h, "5");
        t.parent(p);
    }

    /**
     * Test of children method, of class LinkedBinaryTree.
     */
    @Test
    public void testChildren() {
        LinkedBinaryTree<String> t = new LinkedBinaryTree<>();
        Position<String> p = t.addRoot("+");
        Position<String> n2 = t.insertLeft(p, "2");
        Position<String> h = t.insertRight(p, "*");
        t.insertLeft(h, "3");
        Position<String> n5 = t.insertRight(h, "5");
        List<Position<String>> myChildren = new ArrayList<>();
        myChildren.add(n2);
        myChildren.add(h);
        Iterator<Position<String>> myIt = myChildren.iterator();
        for (Position<String> child : t.children(p)) {
            Position<String> next = myIt.next();
            assertEquals(child, next);
        }
    }

    /**
     * Test of iterator method, of class LinkedBinaryTree.
     */
    @Test
    public void testIterator() {
        LinkedBinaryTree<String> t = new LinkedBinaryTree<>();
        Position<String> p = t.addRoot("+");
        t.insertLeft(p, "2");
        t.insertRight(p, "3");
        String salida = "";
        for (Position<String> e : t) {
            salida += e.getElement();
        }
        assertEquals("2+3",salida);
    }

    /**
     * Test of replace method, of class LinkedBinaryTree.
     */
    @Test
    public void testReplace() {
        LinkedBinaryTree<String> t = new LinkedBinaryTree<>();
        Position<String> p = t.addRoot("+");
        Position<String> n2 = t.insertLeft(p, "2");
        Position<String> h = t.insertRight(p, "*");
        t.insertLeft(h, "3");
        Position<String> n5 = t.insertRight(h, "5");
        t.replace(h, "+");
        assertEquals(h.getElement(), "+");
    }

    /**
     * Test of sibling method, of class LinkedBinaryTree.
     */
    @Test
    public void testSibling() {
        LinkedBinaryTree<String> t = new LinkedBinaryTree<>();
        Position<String> p = t.addRoot("+");
        Position<String> n2 = t.insertLeft(p, "2");
        Position<String> h = t.insertRight(p, "*");
        t.insertLeft(h, "3");
        Position<String> n5 = t.insertRight(h, "5");
        assertEquals(t.sibling(n2),h);
    }
    
    /**
     * Test of sibling method, of class LinkedBinaryTree.
     */
    @Test(expected = RuntimeException.class)
    public void testSiblingException() {
        LinkedBinaryTree<String> t = new LinkedBinaryTree<>();
        Position<String> p = t.addRoot("+");
        Position<String> n2 = t.insertLeft(p, "2");
        Position<String> h = t.insertRight(p, "*");
        t.insertLeft(h, "3");
        Position<String> n5 = t.insertRight(h, "5");
        t.sibling(p);
    }

    /**
     * Test of addRoot method, of class LinkedBinaryTree.
     */
    @Test
    public void testAddRoot() {
        LinkedBinaryTree<String> t = new LinkedBinaryTree<>();
        Position<String> p = t.addRoot("+");
        assertEquals(t.root(), p);
    }

    /**
     * Test of insertLeft method, of class LinkedBinaryTree.
     */
    @Test
    public void testInsertLeft() {
        LinkedBinaryTree<String> t = new LinkedBinaryTree<>();
        Position<String> p = t.addRoot("+");
        Position<String> n2 = t.insertLeft(p, "2");
        assertEquals(t.left(p), n2);
    }

    /**
     * Test of insertRight method, of class LinkedBinaryTree.
     */
    @Test
    public void testInsertRight() {
        LinkedBinaryTree<String> t = new LinkedBinaryTree<>();
        Position<String> p = t.addRoot("+");
        Position<String> n2 = t.insertLeft(p, "2");
        Position<String> h = t.insertRight(p, "*");
        assertEquals(t.right(p), h);
    }

    /**
     * Test of remove method, of class LinkedBinaryTree.
     */
    @Test
    public void testRemove() {
        LinkedBinaryTree<String> t = new LinkedBinaryTree<>();
        Position<String> p = t.addRoot("+");
        Position<String> q = t.insertLeft(p, "2");
        Position<String> h = t.insertRight(p, "*");
        t.insertLeft(h, "3");
        t.insertRight(h, "5");
        t.remove(q);
        assertFalse(t.hasLeft(p));
    }
    
    /**
     * Test of remove method, of class LinkedBinaryTree.
     */
    @Test(expected = RuntimeException.class)
    public void testRemoveException() {
        LinkedBinaryTree<String> t = new LinkedBinaryTree<>();
        Position<String> p = t.addRoot("+");
        Position<String> q = t.insertLeft(p, "2");
        Position<String> h = t.insertRight(p, "*");
        t.insertLeft(h, "3");
        t.insertRight(h, "5");
        t.remove(h);
    }

    /**
     * Test of attach method, of class LinkedBinaryTree.
     */
    @Test
    public void testAttach() {
        LinkedBinaryTree<String> t = new LinkedBinaryTree<>();
        Position<String> p = t.addRoot("+");
        Position<String> n2 = t.insertLeft(p, "2");
        Position<String> h = t.insertRight(p, "*");
        t.insertLeft(h, "3");
        Position<String> n5 = t.insertRight(h, "5");
        LinkedBinaryTree<String> t1 = new LinkedBinaryTree<>();
        t1.addRoot("*");
        t1.insertLeft(t1.root(), "3");
        t1.insertRight(t1.root(), "9");
        LinkedBinaryTree<String> t2 = new LinkedBinaryTree<>();
        t2.addRoot("-");
        t2.insertLeft(t2.root(), "1");
        t2.insertRight(t2.root(), "7");
        t.attachLeft(n5, t1);
        t.attachRight(n5, t2);
        assertEquals(t.left(n5).getElement(), "*");
        assertEquals(t.right(n5).getElement(), "-");
    }
    
    /**
     * Test of attach method to a non empty node, of class LinkedBinaryTree.
     */
    @Test(expected = RuntimeException.class)
    public void testAttachException1() {
        LinkedBinaryTree<String> t = new LinkedBinaryTree<>();
        Position<String> p = t.addRoot("+");
        Position<String> n2 = t.insertLeft(p, "2");
        Position<String> h = t.insertRight(p, "*");
        t.insertLeft(h, "3");
        Position<String> n5 = t.insertRight(h, "5");
        LinkedBinaryTree<String> t1 = new LinkedBinaryTree<>();
        t1.addRoot("*");
        t1.insertLeft(t1.root(), "3");
        t1.insertRight(t1.root(), "9");
        LinkedBinaryTree<String> t2 = new LinkedBinaryTree<>();
        t2.addRoot("-");
        t2.insertLeft(t2.root(), "1");
        t2.insertRight(t2.root(), "7");
        t.attachLeft(h, t1);
        t.attachRight(h, t2);
    }
    
    /**
     * Test of attach method, of class LinkedBinaryTree.
     */
    @Test(expected = RuntimeException.class)
    public void testAttachException2() {
        LinkedBinaryTree<String> t = new LinkedBinaryTree<>();
        Position<String> p = t.addRoot("+");
        Position<String> n2 = t.insertLeft(p, "2");
        Position<String> h = t.insertRight(p, "*");
        t.insertLeft(h, "3");
        Position<String> n5 = t.insertLeft(h, "5");

        LinkedBinaryTree<String> t1 = new LinkedBinaryTree<>();
        t1.addRoot("*");
        t1.insertLeft(t1.root(), "3");
        t1.insertRight(t1.root(), "9");
        t.attachLeft(h, t1);
    }

    /**
     * Test of swapElements method, of class LinkedBinaryTree.
     */
    @Test
    public void testSwap() {
        LinkedBinaryTree<String> t = new LinkedBinaryTree<>();
        Position<String> p = t.addRoot("+");
        Position<String> p1 = t.insertLeft(p, "2");
        Position<String> p2 = t.insertRight(p, "3");
        t.swap(p1, p2);
        String salida = "";
        for (Position<String> e : t) {
            salida += e.getElement();
        }
        assertEquals(salida, "3+2");
    }

    /**
     * Test of subTree method, of class LinkedBinaryTree.
     */
    @Test
    public void testSubTree() {
        LinkedBinaryTree<String> t = new LinkedBinaryTree<>();
        Position<String> p = t.addRoot("+");
        Position<String> n2 = t.insertLeft(p, "2");
        Position<String> h = t.insertRight(p, "*");
        Position<String> n3 = t.insertLeft(h, "3");
        Position<String> n5 = t.insertRight(h, "5");
        LinkedBinaryTree<String> t2 =t.subTree(h);
        assertEquals(t2.root(), h);
        assertEquals(t2.left(t2.root()), n3);
        assertEquals(t2.right(t2.root()), n5);
        
    }
    
}
