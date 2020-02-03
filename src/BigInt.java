import java.util.ArrayList;
import java.util.List;

public class BigInt {

    private int bitLenght = 256;
    private int blockSize = 32;
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

    BigInt(int[] representation) {
        // check size
        ini();
        for(int i = 0; i < this.representation.size(); i++) {
            this.representation.set(i, representation[i]);
        }
    }

    /**
     * Compares the calling BigInt to the one given in parameter
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
                i =this.representation.size();
            } else if (this.representation.get(i) < b.getRepresentation().get(i)) {
                i = this.representation.size();
            }
            i++;
        }
        return isGreater;
    }

    /**
     * Checks if two BigInt are equals
     * @param b The BigInt to compare to (BigInt)
     * @return Boolean to indicate if equals or not (true: equals; false: not equals) (boolean)
     */
    public boolean isEqual(BigInt b) {
        // check size
        boolean isEq = true;
        for(int i = 0; i < this.representation.size(); i++) {
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
        for (int i = 0; i < bitLenght / blockSize; i++) {
            representation.add(0);
        }
    }

    /*
    public BigInt add(BigInt b, BigInt mod) {
        int carry = 0;

    }
    */

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
        StringBuilder str = new StringBuilder("BigInt[");
        for (int i = 0; i < this.representation.size(); i++) {
            str.append(this.representation.toString());
            str.append(" ");
        }
        return str.append("]").toString();
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
     * Copies an integer list into the representation list
     *
     * @param toCopy The integer to copy into the representation (List<Integer>)
     */
    private void copy(List<Integer> toCopy) {
        // check size
        for (int i = 0; i < toCopy.size(); i++) {
            this.representation.set(i, toCopy.get(i));
        }
    }
}
