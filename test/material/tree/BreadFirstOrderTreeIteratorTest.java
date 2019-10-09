/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package material.tree;

import static junit.framework.TestCase.assertEquals;
import material.Position;
import material.tree.narytree.LinkedTree;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author jvelez
 */
public class BreadFirstOrderTreeIteratorTest {
    
    public BreadFirstOrderTreeIteratorTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of hasNext method, of class PosOrderTreeIterator.
     */
    @Test
    public void testIterator() {
        System.out.println("testIterator");
        LinkedTree<String> t = new LinkedTree<>();
        Position<String> a = t.addRoot("A");
        Position<String> b = t.add("B", a);
        Position<String> c = t.add("C", a);
        Position<String> d = t.add("D", a);
        Position<String> e = t.add("E", c);
        Position<String> f = t.add("F", c);
        Position<String> g = t.add("G", c);
        Position<String> h = t.add("H", d);
        Position<String> i = t.add("I", f);
        Position<String> j = t.add("J", f);

        String salida = "";
        BreadthFirstTreeIterator <String> it = new BreadthFirstTreeIterator<>(t);
        while (it.hasNext()) {
            salida += it.next().getElement();
        }

        assertEquals("ABCDEFGHIJ",salida);
    }

}
