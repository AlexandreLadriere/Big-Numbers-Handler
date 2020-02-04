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
        // A and B must be smaller than P for the modular addition
        System.out.print("A.sub_mod(B, P) = ");
        System.out.println(A.sub_mod(B, P));
    }

    public static void main(String[] args) {
        new Test();
    }

    private void iniVariables() {
        iniArrayToZero(a);
        iniArrayToZero(b);
        iniArrayToZero(p);
        p[5] = 2;
        a[6] = 1;
        b[6] = 0;
        p[6] = 0;
        a[7] = 0;
        b[7] = 1;
        p[7] = 0;
        //2147483647
    }

    private void iniArrayToZero(int[] array) {
        Arrays.fill(array, 0);
    }
}
