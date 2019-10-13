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
        if(buffer != 0){
            buffer <<= (highestBit(buffer));
            queue.add(buffer);
        }
        byte[] ret = new byte[queue.size()];
        for(int i = 0; i<ret.length ; i++){
            ret[i] = queue.remove();
        }
        return ret;
    }
    
    public void write(int bits){
        int highBit = highestBit(bits);
        int mask;
        while(highBit >= 0){
            if(lastBit < 7){
                mask = (int) Math.pow(2, highBit);
                this.buffer |= ((bits&mask)==0)? 0 : 1;
                this.buffer <<= 1;
                lastBit++; 
            }else{
                mask = (int) Math.pow(2, highBit);
                this.buffer |= ((bits&mask)==0)? 0 : 1;
                this.queue.offer(this.buffer);
                this.buffer = 0;
                lastBit = 0;
            }
            highBit--;
        }
    }
    
    private int highestBit(int num){
        if(num == 0) return 0;
        
        int test;
        int i = 0;
        boolean highestBit = false;
        while( !highestBit && i < 32){
            test = (int) Math.pow(2,i);
            if( (num & (~test)) < test ){
                highestBit = true;
            }else{
                i++;
            }
        }
        
        return i;
    }    
}
