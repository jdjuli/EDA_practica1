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
public class BitArrayWriter  {

    public Queue<Byte> queue;
    private byte buffer;
    private byte lastBit;    
    
    public BitArrayWriter(){
        if(queue != null) throw new RuntimeException("Just testing...");
        queue = new LinkedList<>();
    }

    public byte[] toArray(){
        if(buffer != 0 || lastBit != 0){
            //int hiBit = highestBit(buffer);
            buffer <<= 7 - lastBit;
            queue.add(buffer);
        }
        byte[] ret = new byte[queue.size()];
        for(int i = 0; i<ret.length ; i++){
            ret[i] = queue.remove();
        }
        return ret;
    }
    
    public void write(BitArray bits){
        int highBit = bits.getLength();
        while(highBit > 0){
            this.buffer |= bits.getBit(highBit-1);
            if(lastBit < 7){
                
                this.buffer <<= 1;
                lastBit++; 
            }else{
                this.queue.offer(this.buffer);
                this.buffer = 0;
                lastBit = 0;
            }
            highBit--;
        }
    }
    
}
