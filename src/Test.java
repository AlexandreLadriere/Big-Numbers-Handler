import java.util.Arrays;

public class Test {

    private int[] a = new int[8];
    private int[] b = new int[8];
    private int[] p = new int[8];
    private int max = 2147483647; // 2^31 (int signed on 32bits)

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
        // A and B must be smaller than P for the modular subtraction
        System.out.print("A.sub_mod(B, P) = ");
        System.out.println(A.sub_mod(B, P));
        // test for simple multiplication
        System.out.print("A.mul(B) = ");
        System.out.println(A.mul(B));
        System.out.print("A.mul(B).mul(A) = ");
        System.out.println(A.mul(B).mul(A));
        System.out.print("A.mul(B).mul(A.mul(B)) = ");
        System.out.println(A.mul(B).mul(A.mul(B)));
    }

    public static void main(String[] args) {
        new Test();
    }

    private void iniVariables() {
        iniArrayToZero(a);
        iniArrayToZero(b);
        iniArrayToZero(p);
        p[3] = 2;
        p[7] = 0;
        a[5] = 0;
        a[6] = max;
        a[7] = max;
        b[5] = 0;
        b[6] = 0;
        b[7] = 2;
    }

    private void iniArrayToZero(int[] array) {
        Arrays.fill(array, 0);
    }
}
