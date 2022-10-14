import org.junit.Test;

public class NumberTest {
    @Test
    public void insideCircle() {
        System.out.println("\n The first experiment");
        for (int i = 0; i < 5; i++)
            System.out.print(i + " ");
    }

    @Test
    public void outsideCircle() {
        System.out.println("\n The second experiment");
        int bind = 0;
        int j;
        for (j = 0; j < 5; j++)
            System.out.print(bind++ + " ");
        System.out.println("\n Out the Circle,the bind is " + bind);
        System.out.println("Out the Circle,the j is " + j);

    }

    @Test
    public void whileCircle() {
        System.out.println("\n The third experiment");
        int k1 = 0;
        int k2 = 0;
        System.out.println("before the add by itself,the 'k' is ");
        while (k1 < 5) {
            System.out.print(k1 + " ");
            k1++;
        }
        System.out.println("\n after the add by itself,the 'k' is ");
        while (k2 < 5) {
            k2++;
            System.out.print(k2 + " ");
        }
    }

    @Test
    public void doWhileCircle() {
        System.out.println("\n The fourth experiment \n The do-while circle");
        int l = 0;
        do {
            System.out.print(l++ + " ");
        } while (l < 5);
    }

    @Test
    public void addSelfInWhile() {
        int k, n = 0;
        for (int i = 10; i <= 30; i++) {
            k = i;
            while (k > 0) {
                if (k % 10 == 2) {
                    n++;
                }
                k /= 10;
            }
        }
        System.out.println("\nThe addSelfInWhile consequence is " + n);
    }

    @Test
    public void cutOutSeek() {
        System.out.println("\nThe cut out and seek result is:");
        StringBuilder sb = new StringBuilder("An animal can run");
        String cutOut = sb.substring(0, 14);
        System.out.println(cutOut + "\n" + cutOut.indexOf("a", 4) + "  " + cutOut.indexOf("can", 3));
    }
}
