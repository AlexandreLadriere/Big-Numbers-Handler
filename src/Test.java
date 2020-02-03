import java.util.Arrays;

public class Test {

    int[] a = new int[8];
    int[] b = new int[8];
    int[] p = new int[8];

    public Test() {
        iniVariables();
        BigInt A = new BigInt(a);
        BigInt B = new BigInt(b);

        System.out.println(A.isGreater(B));
        System.out.println(A.isEqual(B));
    }

    public static void main(String[] args) {
        new Test();
    }

    private void iniVariables() {
        iniArrayToZero(a);
        iniArrayToZero(b);
        a[6] = 3;
        b[6] = 3;
        a[7] = 7;
        b[7] = 7;
    }

    private void iniArrayToZero(int[] array) {
        Arrays.fill(array, 0);
    }
}
