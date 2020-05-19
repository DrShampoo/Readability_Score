package readability;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        StringBuilder sb = new StringBuilder();
        sb.append(scanner.nextLine());
        if (sb.length() <= 100)
            System.out.println("EASY");
        else
            System.out.println("HARD");
    }
}
