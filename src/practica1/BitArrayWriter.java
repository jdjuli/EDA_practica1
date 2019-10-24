package practica1;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Abstact all the array operations required to compose an array of bytes bit by
 * bit. Use {@link #write(practica1.BitArray)} to push bits onto the array and
 * the class itself will take care of splitting them in bytes. Use
 * {@link #toArray()} to retrieve the array containing all the bits written.
 *
 * @author Julian
 */
public class BitArrayWriter {

    public Queue<Byte> queue;
    private byte buffer;
    private byte lastBit;

    public BitArrayWriter() {
        queue = new LinkedList<>();
    }

    public byte[] toArray() {
        //add last byte to the queue
        buffer <<= 7 - lastBit;
        queue.add(buffer);

        byte[] ret = new byte[queue.size()];
        for (int i = 0; i < ret.length; i++) {
            ret[i] = queue.remove();
        }
        return ret;
    }

    public void write(BitArray bits) {
        int highBit = bits.getLength();
        while (highBit > 0) {
            this.buffer |= bits.getBit(highBit - 1);
            if (lastBit < 7) {

                this.buffer <<= 1;
                lastBit++;
            } else {
                this.queue.offer(this.buffer);
                this.buffer = 0;
                lastBit = 0;
            }
            highBit--;
        }
    }

}
