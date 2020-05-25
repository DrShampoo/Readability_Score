package readability;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {

    public static String readFile (String fileName) throws IOException {
        return new String(Files.readAllBytes(Paths.get(fileName)));
    }

    public static int sentencesNumbers (String line) {
        return line.split("[.!?]").length;
    }

    public static int charactersNumbers (String line) {
        return line.replaceAll(" ","").toCharArray().length;
    }

    public static int wordsNumbers (String line) {
        return line.split(" ").length;
    }

    public static double scoreCalculated (int words, int sentences, int characters) {
        return (4.71 * ((double) characters / (double) words)) + (0.5 * ((double) words / (double) sentences)) - 21.43;
    }

    public static String ageGrade (double score) {
        Map<Double, String> emptyMap = new HashMap<>();
        emptyMap.put(1.0, "5-6");
        emptyMap.put(2.0, "6-7");
        emptyMap.put(3.0, "7-9");
        emptyMap.put(4.0, "9-10");
        emptyMap.put(5.0, "10-11");
        emptyMap.put(6.0, "11-12");
        emptyMap.put(7.0, "12-13");
        emptyMap.put(8.0, "13-14");
        emptyMap.put(9.0, "14-15");
        emptyMap.put(10.0, "15-16");
        emptyMap.put(11.0, "16-17");
        emptyMap.put(12.0, "17-18");
        emptyMap.put(13.0, "18-24");
        emptyMap.put(14.0, "24+");
        return emptyMap.get(Math.ceil(score));
    }

    public static void printResult(int words, int sentences, int characters, double score, String age){
        System.out.println(String.format("Words: %d\nSentences: %d\nCharacters: %d\nThe score is: %.2f\n" +
                "This text should be understood by %s year olds.", words, sentences, characters, score, age));
    }

    public static void main(String[] args) throws IOException {
        String line = readFile(args[0]).replace("[\\t\\n]","");
        int words = wordsNumbers(line);
        int sentences = sentencesNumbers(line);
        int characters = charactersNumbers(line);
        double score = scoreCalculated(words, sentences, characters);
        String age = ageGrade(score);
        printResult(words, sentences, characters, score, age);

    }
}
