/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package practica1;

/**
 *
 * @author Julian
 */
public class BitArray {
    private int value;
    private byte length;

    public BitArray(int value){
        this.value = value;
        this.length = (byte) ( highestBit(value) + 1 );
    }
    
    public BitArray(){}
    
    private byte highestBit(int num){
        if(num == 0) return 0;
        
        int test;
        byte i = 0;
        boolean highestBit = false;
        while( !highestBit && i < 32){
            test = (int) Math.pow(2,i);
            if( Integer.compareUnsigned( num & (~test) , test) < 0 ){
                highestBit = true;
            }else{
                i++;
            }
        }
        
        return i;
    }    
    
    /*public void pushBitLeft(int bit){
        if(bit != 0 && bit != 1) throw new RuntimeException("Only binary digits can be pushed");
        value >>= 1;
        if(bit == 1){
            value |= 0x80000000;
        }
        length += 1;
    }*/
    
    public void pushBitRight(int bit){
        if(bit != 0 && bit != 1) throw new RuntimeException("Only binary digits can be pushed");
        value <<= 1;
        if(bit == 1){
            value |= 1;
        }
        length ++;
    }

    public int getValue() {
        return value;
    }
    
    public int getBit(int position){
        if(position < 0 || position > 31) throw new RuntimeException("Range of bit position exceeded [0,31] and provided was: " +position);
        int mask = 1 << position;
        return ((value&mask )==0)? 0 : 1;
    }


    public byte getLength() {
        return length;
    } 
    
    public byte getHighestBit(){
        return highestBit(value);
    }
    
    @Override
    public BitArray clone(){
        BitArray newInstance = new BitArray();
        newInstance.length = this.length;
        newInstance.value = this.value;
        return newInstance;
    }
    
}
