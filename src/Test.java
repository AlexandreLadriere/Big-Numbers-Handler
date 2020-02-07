// see: https://github.com/AlexandreLadriere/Big-Numbers-Handler

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

        // testMontgomery();
    }

    public static void main(String[] args) {
        new Test();
    }

    private void testMontgomery() {
        System.out.println("\n MONTGOMERY TEST");
        iniArrayToZero(a);
        iniArrayToZero(b);
        iniArrayToZero(p);
        a[7] = 19;
        b[7] = 3;
        p[7] = 9;

        BigInt A = new BigInt();
        A.setRepresentation(a);
        BigInt B = new BigInt();
        B.setRepresentation(b);
        BigInt P = new BigInt();
        P.setRepresentation(p);

        int k = 4;

        BigInt I = new BigInt();
        int[] i = new int[8];
        iniArrayToZero(i);
        i[7] = 1;
        I.setRepresentation(i);

        BigInt R = new BigInt();
        int[] r = new int[8];
        iniArrayToZero(r);
        r[7] = 16;
        R.setRepresentation(r);

        BigInt V = new BigInt();
        int[] v = new int[8];
        iniArrayToZero(v);
        v[7] = 7;
        V.setRepresentation(v);

        System.out.println("A = " + A.toString());
        System.out.println("B = " + B.toString());
        System.out.println("P = " + P.toString());
        System.out.println("R = " + R.toString());
        System.out.println("V = " + V.toString());
        System.out.println("k = " + k);

        int[] rsquare = new int[16];
        rsquare[15] = 4;
        BigInt RSquare = new BigInt(512);
        RSquare.setRepresentation(rsquare);
        // R square must be pre-calculated and be modulus P
        // for R = 16, R ^ 2 mod P = 4

        BigInt phiA = A.mul_montgomery(RSquare, P, R, V, k);
        System.out.println("phiA = " + phiA.toString());
        BigInt phiB = B.mul_montgomery(RSquare, P, R, V, k);
        System.out.println("phiB = " + phiB);

        BigInt Tmp = phiA.mul_montgomery(phiB, P, R, V, k );
        System.out.println("MulMontgomery(phiA, phiB) = " + Tmp.toString());

        BigInt Final = Tmp.mul_montgomery(I, P, R, V, k);
        System.out.print("Final Montgomery = ");
        System.out.println(Final.toString());
    }

    private void iniVariables() {
        iniArrayToZero(a);
        iniArrayToZero(b);
        iniArrayToZero(p);
        p[0] = max;
        p[1] = max;
        a[1] = max;
        b[1] = max-1;
        a[7] = max;
        b[7] = max;
    }

    private void iniArrayToZero(int[] array) {
        Arrays.fill(array, 0);
    }
}
