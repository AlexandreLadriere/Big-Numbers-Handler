// see: https://github.com/AlexandreLadriere/Big-Numbers-Handler

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BigInt {

    private int bitLength = 256; // Length of the BigInt in bits
    private int blockSize = 32; // Length of each block used to represent the BigInt, in bits
    private int base = 0x80000000; // 2**31 (signed int)
    private long resMask = 0x7FFFFFFFL; // mask used to get the 32 LSB in a long
    private List<Integer> representation = new ArrayList<>(); // List representation of the BigInt

    /**
     * Creates a BigInt object
     *
     * @param representation Representation of the BigInt object (List<Integer>)
     */
    BigInt(List<Integer> representation) {
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
        try {
            if (bitLength % blockSize == 0) {
                this.bitLength = bitLength;
                ini();
                this.copy(representation);
            } else {
                throw new Exception("Constructor: BigInteger can not be created. bitLength parameter is not correct. " +
                        "It must be a multiple of 32.");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Creates a BigInt object
     *
     * @param representation Representation of the BigInt object (int[])
     */
    BigInt(int[] representation) {
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
        try {
            if (bitLength % blockSize == 0) {
                this.bitLength = bitLength;
                ini();
                this.copy(representation);
            } else {
                throw new Exception("Constructor: BigInteger can not be created. bitLength parameter is not correct. " +
                        "It must be a multiple of 32.");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
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
        try {
            if (bitLength % blockSize == 0) {
                this.bitLength = bitLength;
                ini();
            } else {
                throw new Exception("Constructor: BigInteger can not be created. bitLength parameter is not correct. " +
                        "It must be a multiple of 32.");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Compares the calling BigInt to the one given in parameter
     *
     * @param b BigInt to compare (BigInt)
     * @return Result of the comparison (true: this >= b, false: b > this) (boolean)
     */
    public boolean isGreater(BigInt b) {
        boolean isGreater = false;
        if (this.bitLength != b.getBitLength()) {
            return isGreater;
        }
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

    // faire un getK()
    public BigInt mul_montgomery(BigInt b, BigInt mod, BigInt r, BigInt v, int k) {
        BigInt result = new BigInt(this.bitLength);
        BigInt s; // new BigInt(this.bitLength + b.getBitLength())
        BigInt sBis;
        BigInt t;
        BigInt m;
        BigInt u = new BigInt(this.bitLength);
        int[] u_tmpArray = new int[u.getRepresentation().size()];
        Arrays.fill(u_tmpArray, 0);

        sBis = this.mul(b); // s = a x b
        s = sBis.castTo512bits();
        System.out.println("sBis = " + sBis.toString());
        System.out.println("s = " + s.toString());

        BigInt t_tmp = s.mul(v); // t_tmp = s.v
        System.out.println("t_tmp = " + t_tmp.toString());
        t = t_tmp.modulusR(k); // t = t_tmp mod r
        System.out.println("t = " + t.toString());
        BigInt t_time_n = t.mul(mod);
        System.out.println("t.mul(mod) = " + t_time_n.toString());
        System.out.println("s = " + s.toString());
        m = t_time_n.add(s); // m = s + t.n
        System.out.println("m = " + m.toString());

        String bigIntBinStr = "";
        // transform m to bit string
        for (int i = 0; i < m.getRepresentation().size(); i++) {
            bigIntBinStr += convertIntToBinString(m.getRepresentation().get(i));
        }
        // removing last k bits
        String newBigIntBinStr = bigIntBinStr.substring(0, bigIntBinStr.length() - k);
        // adding k 0 at the beginning
        for (int i = 0; i < bigIntBinStr.length() - k; i++) {
            newBigIntBinStr = "0" + newBigIntBinStr;
        }
        // convert the previous string to BigInt object
        for (int i = 0; i < u_tmpArray.length; i++) {
            u_tmpArray[i] = Integer.parseInt(newBigIntBinStr.substring(i * (blockSize - 1), (i + 1) * (this.blockSize - 1)), 2);
        }
        u.setRepresentation(u_tmpArray);
        System.out.println("u = " + u.toString());

        if (u.isGreater(mod) || u.isEqual(mod)) {
            result = u.sub(mod);
            System.out.println("u <= n");
        } else {
            result = u;
            System.out.println("u < n");
        }

        return result;
    }

    /**
     * Computes the result of calling BigInt modulus 2^k (with 0 <= k <= 256)
     *
     * @param k the power of 2 of the modulus (Integer)
     * @return Result of calling BigInt modulus 2^k (BigInt)
     */
    public BigInt modulusR(int k) {
        BigInt result = new BigInt();
        int realBitNumberShift = k + (k / this.blockSize); // integers are on 31 bits, he first one is for the sign
        int blockNumber = realBitNumberShift / this.blockSize; // number of blocks
        int remainingBits = realBitNumberShift % this.blockSize; // number of remaining bits
        int[] res_tmpArray = new int[result.getRepresentation().size()];
        Arrays.fill(res_tmpArray, 0);
        for (int i = this.getRepresentation().size() - 1; i >= this.getRepresentation().size() - blockNumber; i--) {
            res_tmpArray[i] = this.getRepresentation().get(i);
        }
        int r_tmp = this.getRepresentation().get(this.getRepresentation().size() - blockNumber - 1);
        int r_tmpL = r_tmp << (this.blockSize - remainingBits - 1);
        int r_tmpR = r_tmpL >>> (this.blockSize - remainingBits); // unsigned shift operator
        res_tmpArray[result.getRepresentation().size() - blockNumber - 1] = r_tmpR;
        result.setRepresentation(res_tmpArray);
        return result;
    }

    /**
     * Multiplies the calling BigInt with the given BigInt (they can have a different bit size)
     *
     * @param b BigInt to multiply with (BigInt)
     * @return The result of the multiplication (BigInt)
     */
    public BigInt mul(BigInt b) {
        BigInt result = new BigInt(b.getBitLength() + this.bitLength);
        BigInt tmp = new BigInt(b.getBitLength() + this.bitLength);
        int[] tmpArray = new int[result.getRepresentation().size()];
        Arrays.fill(tmpArray, 0);
        int shift = blockSize - 1;
        long tmpMul;
        for (int i = b.getRepresentation().size() - 1; i >= 0; i--) {
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
        if (!result.isGreater(mod) || result.isEqual(mod)) {
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
    public BigInt add(BigInt b) {
        int carry = 0;
        long tmpRes; // intermediate result
        int[] resultArray = new int[this.representation.size()];
        BigInt result = new BigInt(this.bitLength); // use another constructor
        int stop = 0;
        int start = 0;
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
        if (this.isGreater(b) || this.isEqual(b)) {
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
    public BigInt sub(BigInt b) {
        BigInt result = new BigInt();
        try {
            if (this.representation.size() != b.getRepresentation().size()) {
                throw new Exception("sub: A and B must have the same size");
            } else {
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
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Extends the length of the calling BigInt by 2
     */
    public BigInt extendLengthByTwo() {
        BigInt result = new BigInt(2 * this.bitLength);
        int[] tmpArray = new int[this.getRepresentation().size() * 2];
        Arrays.fill(tmpArray, 0);
        for (int i = 0; i < this.getRepresentation().size(); i++) {
            tmpArray[i] = this.getRepresentation().get(i);
        }
        int[] tmpArrayInv = reverse(tmpArray);
        result.setRepresentation(tmpArrayInv);
        return result;
    }

    /**
     * Sets the bit length of the calling BigInt object
     *
     * @param bitLength new bit length (int)
     */
    private void setBitLength(int bitLength) {
        this.bitLength = bitLength;
    }

    /**
     * Reverses an array ([1, 2, 3] will become [3, 2, 1])
     *
     * @param a The integer array to reverse (int[])
     * @return A new array which is the inverted array
     */
    public int[] reverse(int[] a) {
        int n = a.length;
        int[] b = new int[n];
        int j = n;
        for (int value : a) {
            b[j - 1] = value;
            j = j - 1;
        }
        return b;
    }

    /**
     * Copies a BigInt object into the calling BigInt
     *
     * @param toCopy The BigInt object to copy into the current BigInt (BigInt)
     */
    public void copy(BigInt toCopy) {
        try {
            for (int i = 0; i < toCopy.getRepresentation().size(); i++) {
                this.representation.set(i, toCopy.representation.get(i));
            }
        } catch (Exception e) {
            System.out.println("copy: A must have at least the same size as B");
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
        try {
            for (int i = 0; i < toCopy.size(); i++) {
                this.representation.set(i, toCopy.get(i));
            }
        } catch (Exception e) {
            System.out.println("copy: A must have at least the same size as B");
        }
    }

    /**
     * Copies an integer array into the representation list
     *
     * @param toCopy The integer array to copy into the representation (int[])
     */
    private void copy(int[] toCopy) {
        try {
            for (int i = 0; i < toCopy.length; i++) {
                this.representation.set(i, toCopy[i]);
            }
        } catch (Exception e) {
            System.out.println("copy: A must have at least the same size as B");
        }
    }

    /**
     * Converts an integer into a bin string of size blockSize - 1
     *
     * @param a the integer to convert (int)
     * @return the string of the binary transformation of the integer (String)
     */
    private String convertIntToBinString(int a) {
        StringBuilder result = new StringBuilder(Integer.toBinaryString(a));
        if (result.length() < blockSize - 1) {
            for (int i = 0; i < (blockSize - 1) - result.length(); i++) {
                result.insert(0, "0");
            }
        }
        return result.toString();
    }

    /**
     * Truncates the calling BigInt to 512bits
     * Avoid using this function !!
     *
     * @return A new BigInt on 512 bits
     */
    private BigInt castTo512bits() {
        BigInt result = new BigInt(512);
        int[] rep = new int[16];
        for (int i = 0; i < result.getRepresentation().size(); i++) {
            rep[rep.length - 1 - i] = this.getRepresentation().get(this.getRepresentation().size() - 1 - i);
        }
        result.setRepresentation(rep);
        return result;
    }
}
