/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package material.tree.binarysearchtree;

import junit.framework.TestCase;
import material.Position;

public class BinarySearchTreeSpeedTest extends TestCase {

	public void testSize() {
		LinkedBinarySearchTree <Integer> b = new LinkedBinarySearchTree <>();
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
		LinkedBinarySearchTree <Integer> b = new LinkedBinarySearchTree <>();
		
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
		LinkedBinarySearchTree <Integer> b = new LinkedBinarySearchTree <>();
		b.insert(5);
		b.insert(3);
		b.insert(6);
		b.insert(7);
		b.insert(1);
		b.insert(6);
		
		String salida = "";
		for (Position <Integer> e : b) {
			salida += e.getElement().toString();
		}
		
		assertEquals(salida, "135667");
	}

        
	public void testSpeed() {
		LinkedBinarySearchTree <Integer> b = new LinkedBinarySearchTree <>();
		
                long start = System.currentTimeMillis();
                
                for (int cont = 0; cont < 1000; cont++)
                {
                    int n = (int) (Math.random() * 20000000 - 10000000);
                    b.insert(cont);
                }

                
                long end = System.currentTimeMillis();
                long elapsedTime = end - start;
                System.out.print("Insertions: ");
                System.out.println(elapsedTime);

                start = System.currentTimeMillis();
                for (int cont = 0; cont < 1000; cont++)
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
