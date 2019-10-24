package practica1;

/**
 * Abstact all the array operations and provides an stream-like way to read an
 * array of bytes bit per bit
 *
 * @author Julian
 */
public class BitArrayReader {

    private final byte[] data;
    private int index;
    private byte buffer;
    private byte lastBit = 7;

    public BitArrayReader(byte[] data) {
        this.data = data;
        loadByteOnBuffer();
    }

    public int readBit() {
        int data;
        if (lastBit > 0) {
            int mask = (int) Math.pow(2, lastBit);
            data = buffer & mask;
            lastBit--;
        } else {
            int mask = (int) Math.pow(2, lastBit);
            data = buffer & mask;
            loadByteOnBuffer();
            lastBit = 7;
        }
        return data;
    }

    private void loadByteOnBuffer() {
        if (index >= data.length) {
            throw new RuntimeException("End of array reached, no more data can be readed");
        }
        buffer = data[index];
        index++;
    }

}
