import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.function.Predicate;

public class getNumbers {

    public static void main(String[] args) {
        while(true) {
            System.out.printf("Please input the function No:\n" +
                    "1 - Get even numbers\n" +
                    "2 - Get odd numbers\n" +
                    "3 - Get prime numbers\n" +
                    "4 - Get prime numbers that are bigger than 5\n" +
                    "0 - quit\n");

            Scanner in = new Scanner(System.in);
            int function = in.nextInt();
            if(function == 0) {
                break;
            } else if(function < 1 || function > 4) {
                continue;
            }

            System.out.println("Input size of the list: ");
            int size = in.nextInt();
            int[] elem = new int[size];
            System.out.println("Input elements the list: ");
            for(int i = 0; i < size; i++) {
                elem[i] = in.nextInt();
            }

            List<Integer> res = new ArrayList<>();
            switch (function) {
                case 1:
                    res = filterArray(elem, getNumbers::isEven);
                    break;
                case 2:
                    res = filterArray(elem, getNumbers::isOdd);
                    break;
                case 3:
                    res = filterArray(elem, getNumbers::isPrime);
                    break;
                case 4:
                    res = filterArray(elem, getNumbers::isPrimeBig5);
                    break;
                default:
                    break;

            }
            System.out.println(res);

        }


    }

    public static List<Integer> filterArray(int[] array, Predicate<Integer> predicate) {
        List<Integer> res = new ArrayList<>();
        for (int i = 0; i < array.length; i++) {
            if (predicate.test(array[i])) {
                res.add(array[i]);
            }
        }
        return res;
    }

    public static boolean isEven(int n) {
        return n % 2 == 0;
    }

    public static boolean isOdd(int n) {
        return n % 2 != 0;
    }

    public static boolean isPrime(int n) {
        if (n <= 1) {
            return false;
        }
        for (int i = 2; i <= Math.sqrt(n); i++) {
            if (n % i == 0) {
                return false;
            }
        }
        return true;
    }

    public static boolean isPrimeBig5(int n) {
        if (n <= 5) {
            return false;
        }
        for (int i = 2; i <= Math.sqrt(n); i++) {
            if (n % i == 0) {
                return false;
            }
        }
        return true;
    }



}
