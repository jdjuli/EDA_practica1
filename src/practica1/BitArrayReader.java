/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package practica1;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;

/**
 *
 * @author Julian
 */
public class BitArrayReader  {

    private final byte[] data;
    private int index;
    private byte buffer;
    private byte lastBit;    
    
    public BitArrayReader(byte[] data){
        this.data = data;
        loadByteOnBuffer();
    }
    
    public int readBit(){
        int data = -1;
        if(lastBit >= 0){
            int mask = (int) Math.pow(2, 7-lastBit);
            data = buffer & mask;
            lastBit++;
        }
        if(lastBit == 0){
            loadByteOnBuffer();
            lastBit = 0;
        }
        return lastBit;
    }
    

    private void loadByteOnBuffer(){
        if(index >= data.length) throw new RuntimeException("End of array reached, no more data can be readed");
        buffer = data[index];
        index++;
    }

}
