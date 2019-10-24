/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package practica1;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
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
        for (int cont = 0; cont < repeat; cont++) {
            text.append(character);
        }
    }

    /**
     * Test of encoding method, of class HuffmanTree.
     */
    @Test
    public void testEncoding() {
        System.out.println("encoding");

        StringBuilder text = new StringBuilder();
        repeatChar(text, 2, 'Z');
        repeatChar(text, 7, 'K');
        repeatChar(text, 24, 'M');
        repeatChar(text, 32, 'C');
        repeatChar(text, 37, 'U');
        repeatChar(text, 42, 'D');
        repeatChar(text, 42, 'L');
        repeatChar(text, 120, 'E');

        HuffmanTree instance = new HuffmanTree(text.toString());
        //byte[] expResult = {(byte)0b01011110, (byte)0b11111110};
        byte[] expResult = {(byte) 0b01011110, (byte) 0b11111110, (byte) 0b11110000};
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
        repeatChar(text, 2, 'Z');
        repeatChar(text, 7, 'K');
        repeatChar(text, 24, 'M');
        repeatChar(text, 32, 'C');
        repeatChar(text, 37, 'U');
        repeatChar(text, 42, 'D');
        repeatChar(text, 42, 'L');
        repeatChar(text, 120, 'E');

        HuffmanTree instance = new HuffmanTree(text.toString());
        //byte[] code = {(byte)0b01011110, (byte)0b11111110};
        byte[] code = {(byte) 0b01011110, (byte) 0b11111110, (byte) 0b11110000};
        String result = instance.decoding(code);
        assertEquals("EDCML", result);
    }

    @Test
    public void testSingleChar() {
        StringBuilder text = new StringBuilder();
        repeatChar(text, 2, 'Z');
        repeatChar(text, 7, 'K');
        repeatChar(text, 24, 'M');
        repeatChar(text, 32, 'C');
        repeatChar(text, 37, 'U');
        repeatChar(text, 42, 'D');
        repeatChar(text, 42, 'L');
        repeatChar(text, 120, 'E');

        HuffmanTree instance = new HuffmanTree(text.toString());
        String toEncode = "U";
        byte[] encoded = instance.encoding(toEncode);
        String decoded = instance.decoding(encoded);

        assertEquals(toEncode, decoded);
    }

    @Test
    public void testUniformDistribution() {
        StringBuilder text = new StringBuilder();
        repeatChar(text, 10, 'Z');
        repeatChar(text, 10, 'K');
        repeatChar(text, 10, 'M');
        repeatChar(text, 10, 'C');
        repeatChar(text, 10, 'U');
        repeatChar(text, 10, 'D');
        repeatChar(text, 10, 'L');
        repeatChar(text, 10, 'E');

        HuffmanTree instance = new HuffmanTree(text.toString());
        String toEncode = "EZKLMDCU";
        byte[] encoded = instance.encoding(toEncode);
        String decoded = instance.decoding(encoded);

        assertEquals(toEncode, decoded);
    }

    @Test
    public void testEncodingEntireWebpage() {
        String text = getWikipediaStringOfHuffmanCoding();
        HuffmanTree instance = new HuffmanTree(text);
        byte[] encoded = instance.encoding(text);
        String result = instance.decoding(encoded);

        assertEquals(text, result);
    }

    private String getWikipediaStringOfHuffmanCoding() {
        try {
            StringBuilder sb = new StringBuilder();
            URL url = new URL("https://en.wikipedia.org/wiki/Huffman_coding");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();
            InputStream is = conn.getInputStream();
            byte[] buffer = new byte[4096];
            int readed;
            while ((readed = is.read(buffer)) != -1) {
                sb.append(new String(buffer, 0, readed));
            }
            return sb.toString();
        } catch (Exception e) {
        }
        return "Cannot connect with Wikipedia...";
    }

}
