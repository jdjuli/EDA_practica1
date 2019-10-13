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
    private byte lastBit = 7;    
    
    public BitArrayReader(byte[] data){
        this.data = data;
        loadByteOnBuffer();
    }
    
    public int readBit(){
        int data = -1;
        if(lastBit > 0){
            int mask = (int) Math.pow(2, lastBit);
            data = buffer & mask;
            lastBit--;
        }else{
            int mask = (int) Math.pow(2, lastBit);
            data = buffer & mask;
            loadByteOnBuffer();
            lastBit = 7;
        }
        return data;
    }
    

    private void loadByteOnBuffer(){
        if(index >= data.length) throw new RuntimeException("End of array reached, no more data can be readed");
        buffer = data[index];
        index++;
    }

}
