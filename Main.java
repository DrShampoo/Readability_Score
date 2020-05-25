package readability;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String[] array = scanner.nextLine().split("[.?!]");
        int number = 0;
        for (int i = 0; i < array.length; i++) {
            number += array[i].split(" ").length;
        }
        if (number/array.length > 10)
            System.out.println("HARD");
        else
            System.out.println("EASY");
    }
}
