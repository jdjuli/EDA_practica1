package practica1;

/**
 * Represents an array of 32 bits and provides methods to manipulate them,
 * abstracting the mask and logical operations.
 *
 * @author Julian
 */
public class BitArray {

    /**
     * Represents the actual value of the array
     */
    private int value;
    /**
     * Real length of the bit array (number of bits used)
     */
    private byte length;

    public BitArray(int value) {
        this.value = value;
        this.length = (byte) (highestBit(value) + 1);
    }

    public BitArray() {
    }

    /**
     * Returns the position of the most significant 1 in the array
     *
     * @param num
     * @return
     */
    private byte highestBit(int num) {
        if (num == 0) {
            return 0;
        }

        int test;
        byte i = 0;
        boolean highestBit = false;
        while (!highestBit && i < 32) {
            test = (int) Math.pow(2, i);
            if (Integer.compareUnsigned(num & (~test), test) < 0) {
                highestBit = true;
            } else {
                i++;
            }
        }

        return i;
    }

    /**
     * Pushes a single bit on the array
     *
     * @param bit
     */
    public void pushBitRight(int bit) {
        if (bit != 0 && bit != 1) {
            throw new RuntimeException("Only binary digits can be pushed");
        }
        value <<= 1;
        if (bit == 1) {
            value |= 1;
        }
        length++;
    }

    public int getValue() {
        return value;
    }

    /**
     * Returns the value of the bit located on the position passed as the only
     * argument.
     *
     * @param position Index between 0 and 31 representing the required bit
     * @return
     */
    public int getBit(int position) {
        if (position < 0 || position > 31) {
            throw new RuntimeException("Range of bit position exceeded [0,31] and provided was: " + position);
        }
        int mask = 1 << position;
        return ((value & mask) == 0) ? 0 : 1;
    }

    public byte getLength() {
        return length;
    }

    public byte getHighestBit() {
        return highestBit(value);
    }

    @Override
    public BitArray clone() {
        BitArray newInstance = new BitArray();
        newInstance.length = this.length;
        newInstance.value = this.value;
        return newInstance;
    }

}
