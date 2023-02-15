public class Main {
    public static void main(String[] args) {
        int x = 7;
        int y = 4;
        int r1, r2;

        Calculator cal = new Calculator();
        r1 = cal.add(x, y);
        r2 = cal.minus(x, y);

        System.out.printf("x + y = %d\n", r1);
        System.out.printf("x - y = %d\n", r2);
    }
}