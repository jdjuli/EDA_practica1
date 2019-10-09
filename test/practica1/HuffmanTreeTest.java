/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package practica1;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author jvelez
 */
public class HuffmanTreeTest {
    
    public HuffmanTreeTest() {
    }

    private void repeatChar(StringBuilder text, int repeat, char character) {
        for (int cont = 0; cont < repeat; cont++)
            text.append(character);
    }

    /**
     * Test of encoding method, of class HuffmanTree.
     */
    @Test
    public void testEncoding() {
        System.out.println("encoding");

        StringBuilder text = new StringBuilder();        
        repeatChar(text,2,'Z');
        repeatChar(text,7,'K');
        repeatChar(text,24,'M');
        repeatChar(text,32,'C');
        repeatChar(text,37,'U');
        repeatChar(text,42,'D');
        repeatChar(text,42,'L');
        repeatChar(text,120,'E');
        
        HuffmanTree instance = new HuffmanTree(text.toString());
        byte[] expResult = {(byte)0b01011110, (byte)0b11111110};
        byte[] result = instance.encoding("EDCML");
        assertArrayEquals(expResult, result);
    }

    /**
     * Test of decoding method, of class HuffmanTree.
     */
    @Test
    public void testDecoding() {
        System.out.println("decoding");

        StringBuilder text = new StringBuilder();        
        repeatChar(text,2,'Z');
        repeatChar(text,7,'K');
        repeatChar(text,24,'M');
        repeatChar(text,32,'C');
        repeatChar(text,37,'U');
        repeatChar(text,42,'D');
        repeatChar(text,42,'L');
        repeatChar(text,120,'E');
        
        HuffmanTree instance = new HuffmanTree(text.toString());
        byte[] code = {(byte)0b01011110, (byte)0b11111110};
        String result = instance.decoding(code);
        assertEquals("EDCML", result);
    }
    
}
