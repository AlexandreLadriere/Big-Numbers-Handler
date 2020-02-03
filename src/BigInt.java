import java.util.ArrayList;
import java.util.List;

public class BigInt {

    private int bitLenght = 256;
    private int blockSize = 32;
    private List<Integer> representation = new ArrayList<>();

    /**
     * Creates a BigInt object
     * @param representation Representation of the BigInt object (List<Integer>)
     */
    BigInt(List<Integer> representation) {
        ini();
        this.copy(representation);
    }

    /**
     * Initializes the representation to 0
     */
    private void ini() {
        for(int i = 0; i < bitLenght/blockSize; i++) {
            representation.set(i, 0);
        }
    }

    // public add()

    /**
     * Copies a BigInt object into the calling BigInt
     * @param toCopy The BigInt object to copy into the current BigInt (BigInt)
     */
    public void copy(BigInt toCopy) {
        for(int i = 0; i < toCopy.representation.size(); i++) {
            this.representation.set(i, toCopy.representation.get(i));
        }
    }

    /**
     * Returns a string representation of the object
     * @return String representation of the object (String)
     */
    public String toString() {
        StringBuilder str = new StringBuilder("BigInt[");
        for(int i = 0; i < this.representation.size(); i++) {
            str.append(this.representation.toString());
            str.append(" ");
        }
        return str.append("]").toString();
    }

    /**
     * Gets the representation
     * @return The object representation (List<Integer>)
     */
    public List<Integer> getRepresentation() {
        return representation;
    }

    /**
     * Sets the representation
     * @param representation New representation (List<Integer>)
     */
    public void setRepresentation(List<Integer> representation) {
        copy(representation);
    }

    /**
     * Copies an integer list into the representation list
     * @param toCopy The integer to copy into the representation (List<Integer>)
     */
    private void copy(List<Integer> toCopy) {
        for(int i = 0; i < toCopy.size(); i++) {
            this.representation.set(i, toCopy.get(i));
        }
    }
}
