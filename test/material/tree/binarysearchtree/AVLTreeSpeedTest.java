/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package material.tree.binarysearchtree;

import java.util.Iterator;
import java.util.TreeSet;
import junit.framework.TestCase;
import material.Position;

public class AVLTreeSpeedTest extends TestCase {

	public void testSize() {
		AVLTree <Integer> b = new AVLTree <>();
		assertEquals(b.size(), 0);
		b.insert(5);
		assertEquals(b.size(), 1);		
		b.insert(10);
		assertEquals(b.size(), 2);
		
		for (int cont = 0; cont < 25; cont++)
			b.insert(cont);
	
		assertEquals(b.size(), 27);		
	}

	
	public void testFind() {
		AVLTree <Integer> b = new AVLTree <>();
		
		for (int cont = 0; cont < 25; cont+=2)
			b.insert(cont);

		b.insert(17);

		Position <Integer> p = b.find(17);
		assertEquals(p.getElement().intValue(),17);
		p = b.find(2);
		assertEquals(p.getElement().intValue(),2);
		p = b.find(13);
		assertEquals(p,null);
	}

	
	public void testInsert() {
		AVLTree <Integer> b = new AVLTree <>();
		TreeSet<Integer> expected = new TreeSet<>();
                
		b.insert(100);expected.add(100);
		b.insert(29);expected.add(29);
		b.insert(71);expected.add(71);
		b.insert(82);expected.add(82);
		b.insert(48);expected.add(48);
		b.insert(39);expected.add(39);
		b.insert(101);expected.add(101);
		b.insert(22);expected.add(22);
		b.insert(46);expected.add(46);
		b.insert(17);expected.add(17);
		b.insert(3);expected.add(3);
		b.insert(20);expected.add(20);
		b.insert(25);expected.add(25);
		b.insert(10);expected.add(10);
		
                Iterator <Integer> it = expected.iterator();
		for (Position<Integer> e : b) {
			assertEquals(it.next(),e.getElement());
		}

	}

	
	public void testRemove() {
		AVLTree <Integer> b = new AVLTree <>();
		TreeSet<Integer> expected = new TreeSet<>();

                Position<Integer> p1 = b.insert(100);
		Position<Integer> p2 = b.insert(29);
		b.insert(71);expected.add(71);
		b.insert(82);expected.add(82);
		b.insert(48);expected.add(48);
		b.insert(39);expected.add(39);
		b.insert(101);expected.add(101);
		b.insert(22);expected.add(22);
		b.insert(46);expected.add(46);
		b.insert(17);expected.add(17);
		b.insert(3);expected.add(3);
		b.insert(20);expected.add(20);
		b.insert(25);expected.add(25);
		b.insert(10);expected.add(10);
		
		b.remove(p1);
		b.remove(p2);
		
                Iterator <Integer> it = expected.iterator();
		for (Position<Integer> e : b) {
			assertEquals(it.next(),e.getElement());
		}
	}

        
	public void testSpeed() {
		AVLTree <Integer> b = new AVLTree <>();
		
                long start = System.currentTimeMillis();
                
                for (int cont = 0; cont < 10000; cont++)
                {
                    int n = (int) (Math.random() * 20000000 - 10000000);
                    b.insert(cont);
                }
                
                long end = System.currentTimeMillis();
                long elapsedTime = end - start;
                System.out.print("Insertions: ");
                System.out.println(elapsedTime);

                start = System.currentTimeMillis();
                for (int cont = 0; cont < 10000; cont++)
                {
                    int n = (int)(Math.random() * 20000000 - 10000000);
                    b.find(cont);
                }                                
                end = System.currentTimeMillis();
                elapsedTime = end - start;
                System.out.print("Searchs: ");                
                System.out.println(elapsedTime);
                
                assertEquals(elapsedTime > 0, true);
	}
}
