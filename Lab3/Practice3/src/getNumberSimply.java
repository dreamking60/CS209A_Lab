import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.function.Predicate;

public class getNumberSimply {

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

            Predicate<Integer> isEven = s -> s % 2 == 0;
            Predicate<Integer> isOdd = s -> s %2 != 0;
            Predicate<Integer> isPrime = s -> {
                if(s <= 1) {
                    return false;
                }
                for(int i = 2; i <= Math.sqrt(s); i++) {
                    if(s % i == 0) {
                        return false;
                    }
                }
                return true;
            };

            List<Integer> res = new ArrayList<>();
            switch (function) {
                case 1:
                    res = filterArray(elem, isEven);
                    break;
                case 2:
                    res = filterArray(elem, isOdd);
                    break;
                case 3:
                    res = filterArray(elem, isPrime);
                    break;
                case 4:
                    res = filterArray(elem, s -> s > 5 && isPrime.test(s));
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

}
