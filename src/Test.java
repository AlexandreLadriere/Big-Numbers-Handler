import java.util.Arrays;

public class Test {

    int[] a = new int[8];
    int[] b = new int[8];
    int[] p = new int[8];

    public Test() {
        iniVariables();
        BigInt A = new BigInt(a);
        BigInt B = new BigInt(b);
        BigInt P = new BigInt(p);

        System.out.print("A = ");
        System.out.println(A.toString());
        System.out.print("B = ");
        System.out.println(B.toString());
        System.out.print("P = ");
        System.out.println(P.toString());
        System.out.print("A.isGreater(B) = ");
        System.out.println(A.isGreater(B));
        System.out.print("A.isEqual(B) = ");
        System.out.println(A.isEqual(B));
        // A and B must be smaller than P for the modular addition
        System.out.print("A.add_mod(B, P) = ");
        System.out.println(A.add_mod(B, P));
    }

    public static void main(String[] args) {
        new Test();
    }

    private void iniVariables() {
        iniArrayToZero(a);
        iniArrayToZero(b);
        iniArrayToZero(p);
        a[6] = 10;
        b[6] = 15;
        p[6] = 20;
        a[7] = 35;
        b[7] = 30;
        p[7] = 40;
    }

    private void iniArrayToZero(int[] array) {
        Arrays.fill(array, 0);
    }
}
