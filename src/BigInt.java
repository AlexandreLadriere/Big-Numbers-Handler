import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BigInt {

    // TODO: Make a constant file
    private int bitLength = 256;
    private int blockSize = 32;
    private int base = 0x80000000; // 2**31
    // TODO: add masks
    private List<Integer> representation = new ArrayList<>();

    /**
     * Creates a BigInt object
     *
     * @param representation Representation of the BigInt object (List<Integer>)
     */
    BigInt(List<Integer> representation) {
        // check size
        ini();
        this.copy(representation);
    }

    /**
     * Creates a BigInt object
     *
     * @param representation Representation of the BigInt object (List<Integer>)
     * @param bitLength      Length of the BigInt in bits (int)
     */
    BigInt(List<Integer> representation, int bitLength) {
        // check size
        // check bitlength multiplicity (int dividing blocksize)
        this.bitLength = bitLength;
        ini();
        this.copy(representation);
    }

    /**
     * Creates a BigInt object
     *
     * @param representation Representation of the BigInt object (int[])
     */
    BigInt(int[] representation) {
        // check size
        ini();
        this.copy(representation);
    }

    /**
     * Creates a BigInt object
     *
     * @param representation Representation of the BigInt object (int[])
     * @param bitLength      Length of the BigInt in bits (int)
     */
    BigInt(int[] representation, int bitLength) {
        // check size
        // check bitlength multiplicity (int dividing blocksize)
        this.bitLength = bitLength;
        ini();
        this.copy(representation);
    }

    /**
     * Creates a BigInt object
     */
    BigInt() {
        ini();
    }

    /**
     * Creates a BigInt object
     *
     * @param bitLength Length of the BigInt in bits (int)
     */
    BigInt(int bitLength) {
        // check bitlength multiplicity (int dividing blocksize)
        this.bitLength = bitLength;
        ini();
    }

    /**
     * Compares the calling BigInt to the one given in parameter
     *
     * @param b BigInt to compare (BigInt)
     * @return Result of the comparison (true: this >= b, false: b > this) (boolean)
     */
    public boolean isGreater(BigInt b) {
        //check size
        boolean isGreater = false;
        int i = 0;
        while (i < this.representation.size()) {
            if (this.representation.get(i) > b.getRepresentation().get(i)) {
                isGreater = true;
                i = this.representation.size();
            } else if (this.representation.get(i) < b.getRepresentation().get(i)) {
                i = this.representation.size();
            }
            i++;
        }
        return isGreater;
    }

    /**
     * Checks if two BigInt are equals
     *
     * @param b The BigInt to compare to (BigInt)
     * @return Boolean to indicate if equals or not (true: equals; false: not equals) (boolean)
     */
    public boolean isEqual(BigInt b) {
        // check size
        if (this.bitLength != b.getBitLength()) {
            return false;
        }
        boolean isEq = true;
        for (int i = 0; i < this.representation.size(); i++) {
            if (!this.representation.get(i).equals(b.getRepresentation().get(i))) {
                isEq = false;
                break;
            }
        }
        return isEq;
    }

    /**
     * Initializes the representation to 0
     */
    private void ini() {
        for (int i = 0; i < this.bitLength / this.blockSize; i++) {
            this.representation.add(0);
        }
    }

    /**
     * Multiplies the calling BigInt with the given BigInt
     *
     * @param b BigInt to multiply with (BigInt)
     * @return The result of the multiplication (BigInt)
     */
    public BigInt mul(BigInt b) {
        // check same size
        BigInt result = new BigInt(b.getBitLength() * 2);
        BigInt tmp = new BigInt(b.getBitLength() * 2);
        int[] tmpArray = new int[result.getRepresentation().size()];
        Arrays.fill(tmpArray, 0);
        int shift = blockSize - 1;
        long tmpMul;
        long resMask = 0x7FFFFFFFL;
        for (int i = this.representation.size() - 1; i >= 0; i--) {
            for (int j = this.representation.size() - 1; j >= 0; j--) {
                Arrays.fill(tmpArray, 0);
                tmpMul = (long) this.representation.get(j) * (long) b.getRepresentation().get(i);
                long tmp2 = (tmpMul >> shift);
                int leftBlock = (int) (tmp2);
                int rightBlock = (int) (tmpMul & resMask);
                tmpArray[i + j + 1] = rightBlock;
                tmpArray[i + j] = leftBlock;
                tmp.setRepresentation(tmpArray);
                result = result.add(tmp);
            }
        }
        return result;
    }

    /**
     * Modular addition with another BigInt
     *
     * @param b   BigInt to add (BigInt)
     * @param mod Modular (BigInt)
     * @return The result of the modular addition (BigInt)
     */
    public BigInt add_mod(BigInt b, BigInt mod) {
        // check modulo size
        // this and b must be mod mod
        BigInt result = this.add(b);
        if (!result.isGreater(mod)) {
            return result;
        } else {
            return result.sub(mod);
        }
    }

    /**
     * Adds a BigInt to the calling BigInt object (without mod)
     *
     * @param b BigInt object to add (BigInt)
     * @return the result of the addition (BigInt)
     */
    private BigInt add(BigInt b) {
        int carry = 0;
        long tmpRes; // intermediate result
        long resMask = 0x7FFFFFFFL;
        int[] resultArray = new int[this.representation.size()];
        BigInt result = new BigInt(this.bitLength); // use another constructor
        for (int i = this.representation.size() - 1; i >= 0; i--) {
            tmpRes = (long) this.representation.get(i) + (long) b.getRepresentation().get(i) + (long) carry;
            carry = (int) (tmpRes >> blockSize - 1); // get the carry and transform it to a int
            resultArray[i] = (int) (tmpRes & resMask);
        }
        result.setRepresentation(resultArray);
        return result;
    }

    /**
     * Modular subtraction between BigInt objects (this minus b mod mod)
     *
     * @param b   The BigInt you want to subtract (BigInt)
     * @param mod the modulus (BigInt)
     * @return Result of the modular subtraction (BigInt)
     */
    public BigInt sub_mod(BigInt b, BigInt mod) {
        BigInt result;
        if (this.isGreater(b)) { // a verifier
            result = this.sub(b);
        } else {
            result = this.add(mod);
            result = result.sub(b);
        }
        return result;
    }

    /**
     * Classic subtraction 32bits-words by 32bits-word
     *
     * @param b BigInt to subtract (BigInt)
     * @return the result of the classic subtraction (BigInt)
     */
    private BigInt sub(BigInt b) {
        // check size
        BigInt result = new BigInt();
        int[] resultRepresentation = new int[this.representation.size()];
        int carry = 1;
        for (int i = this.representation.size() - 1; i > 0; i--) {
            int ai = this.representation.get(i);
            int bi = b.getRepresentation().get(i);
            if (ai >= bi) { //a[i] >= b[i]
                resultRepresentation[i] = ai - bi;
            } else {
                resultRepresentation[i] = ai + base - bi;
                b.getRepresentation().set(i - 1, b.getRepresentation().get(i - 1) + carry);
            }
        }
        result.setRepresentation(resultRepresentation);
        return result;
    }

    /**
     * Copies a BigInt object into the calling BigInt
     *
     * @param toCopy The BigInt object to copy into the current BigInt (BigInt)
     */
    public void copy(BigInt toCopy) {
        //check size
        for (int i = 0; i < toCopy.getRepresentation().size(); i++) {
            this.representation.set(i, toCopy.representation.get(i));
        }
    }

    /**
     * Returns a string representation of the object
     *
     * @return String representation of the object (String)
     */
    public String toString() {
        return "BigInt" + this.representation.toString();
    }

    /**
     * Gets the bitLength of the BigInt
     *
     * @return bitLength og the BigInt (int)
     */
    public int getBitLength() {
        return bitLength;
    }

    /**
     * Gets the representation
     *
     * @return The object representation (List<Integer>)
     */
    public List<Integer> getRepresentation() {
        return representation;
    }

    /**
     * Sets the representation
     *
     * @param representation New representation (List<Integer>)
     */
    public void setRepresentation(List<Integer> representation) {
        copy(representation);
    }

    /**
     * Sets the representation
     *
     * @param representation New representation (int[])
     */
    public void setRepresentation(int[] representation) {
        copy(representation);
    }

    /**
     * Copies an integer list into the representation list
     *
     * @param toCopy The integer list to copy into the representation (List<Integer>)
     */
    private void copy(List<Integer> toCopy) {
        // check size
        for (int i = 0; i < toCopy.size(); i++) {
            this.representation.set(i, toCopy.get(i));
        }
    }

    /**
     * Copies an integer array into the representation list
     *
     * @param toCopy The integer array to copy into the representation (int[])
     */
    private void copy(int[] toCopy) {
        // check size
        for (int i = 0; i < toCopy.length; i++) {
            this.representation.set(i, toCopy[i]);
        }
    }
}
