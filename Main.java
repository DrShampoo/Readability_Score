package readability;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    private static int words;
    private static int sentences;
    private static int characters;
    private static int syllables;
    private static int polysyllables;
    private static double countLevel;

    public static String readFile (String fileName) throws IOException {
        return new String(Files.readAllBytes(Paths.get(fileName)));
    }

    public static void sentencesNumbers (String line) {
        sentences = line.split("[.!?]").length;
    }

    public static void charactersNumbers (String line) {
        characters = line.replaceAll(" ","").toCharArray().length;
    }

    public static void wordsNumbers (String line) {
        words = line.split(" ").length;
    }

    public static void scoreAri () {
        double indexAi = (4.71 * ((double) characters / (double) words)) + (0.5 * ((double) words / (double) sentences)) - 21.43;
        countLevel += ageGrade(indexAi);
        System.out.println(String.format("Automated Readability Index: %.2f (about %d year olds).", indexAi, ageGrade(indexAi)));
    }
    public static void scoreFk () {
        double indexFk = (0.39 * ((double) words / (double) sentences)) + 11.8 * ((double) syllables / (double) words) - 15.59;
        countLevel += ageGrade(indexFk);
        System.out.println(String.format("Flesch–Kincaid readability tests: %.2f (about %d year olds).", indexFk, ageGrade(indexFk)));
    }
    public static void scoreSmog () {
        double indexSmog = 1.043 * Math.sqrt((double) polysyllables * (30 / (double) sentences)) + 3.1291;
        countLevel += ageGrade(indexSmog);
        System.out.println(String.format("Simple Measure of Gobbledygook: %.2f (about %d year olds).", indexSmog, ageGrade(indexSmog)));
    }
    public static void scoreCl() {
        double indexCl = 0.0588 * (100 * ((double) characters / (double) words)) - 0.296 * (100 * ((double) sentences / (double) words)) - 15.8;
        countLevel += ageGrade(indexCl);
        System.out.println(String.format("Coleman–Liau index: %.2f (about %d year olds).", indexCl, ageGrade(indexCl)));
    }

    public static int countSyllables (String word) {
        ArrayList<String> tokens = new ArrayList<>();
        String regexp = "[^aeiouy]*[aeiouy]+[^aeiouy]*";
        Pattern p = Pattern.compile(regexp);
        Matcher m = p.matcher(word.toLowerCase().replaceAll("[.,!?]", ""));
        while (m.find()) {
            tokens.add(m.group());
        }
        if (tokens.size() > 1 && tokens.get(tokens.size() - 1).equals("e"))
            return tokens.size() - 1;
        if (tokens.size() == 0)
            return 1;
        return tokens.size();
    }

    public static void countLine(String line) {
        for (String word : line.split(" ")) {
            int count = countSyllables(word);
            if (count > 2)
                polysyllables += 1;
            syllables += count;
        }
    }

    public static int ageGrade (Double score) {
        Map<Integer, Integer> emptyMap = new HashMap<>();
        emptyMap.put(1, 6);
        emptyMap.put(2, 7);
        emptyMap.put(3, 9);
        emptyMap.put(4, 10);
        emptyMap.put(5, 11);
        emptyMap.put(6, 12);
        emptyMap.put(7, 13);
        emptyMap.put(8, 14);
        emptyMap.put(9, 15);
        emptyMap.put(10, 16);
        emptyMap.put(11, 17);
        emptyMap.put(12, 18);
        emptyMap.put(13, 24);
        emptyMap.put(14, 24);
        return emptyMap.get((int)Math.round(score));
    }

    public static String printResult(){
        Scanner scanner = new Scanner(System.in);
        System.out.println(String.format("Words: %d\nSentences: %d\nCharacters: %d\nSyllables: %d\nPolysyllables: %d\n" +
                "Enter the score you want to calculate (ARI, FK, SMOG, CL, all):", words, sentences, characters, syllables, polysyllables));
        return scanner.nextLine();
    }



    public static void main(String[] args) throws IOException {
        String line = readFile(args[0]).replace("[\\t\\n]","");
        wordsNumbers(line);
        sentencesNumbers(line);
        charactersNumbers(line);
        countLine(line);
        String score = printResult();
        switch (score) {
            case "ARI":
                scoreAri();
                break;
            case "FK":
                scoreFk();
                break;
            case "SMOG":
                scoreSmog();
                break;
            case "CL":
                scoreCl();
                break;
            case "all":
                scoreAri();
                scoreFk();
                scoreSmog();
                scoreCl();
                System.out.println(String.format("This text should be understood in average by %.2f year olds.", countLevel/4));

        }

    }
}
