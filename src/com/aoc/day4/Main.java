package com.aoc.day4;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Main {
    private static final String FILE_NAME = "\\input.txt";

    public static void main(String[] args) {

        List<String> input = readInput();
        System.out.println(input);
        getAnswerPart1(input);
        getAnswerPart2(input);
    }

    private static void getAnswerPart1(List<String> input) {
        int totalPoints = 0;
        int matches;
        double cardPoints;
        for(String s: input) {
            matches = 0;
            cardPoints = 0;

            List<Integer> winningNumbers = getNumbers(s, 0);
            List<Integer> currentNumbers = getNumbers(s, 1);

            for(int i: winningNumbers) {
                if(currentNumbers.contains(i)) {
                    matches++;
                }
            }

            if(matches > 0) {
                cardPoints = Math.pow(2, matches - 1);
            }

            totalPoints+= (int) cardPoints;
        }
        System.out.println("Result part 1: " + totalPoints);
    }

    private static List<Integer> getNumbers(String s, int side) {
        return Arrays.stream(s.split(":")[1]
                        .split("\\|")[side]
                        .trim()
                        .split("\\s+"))
                .map(Integer::parseInt)
                .toList();
    }

    private static void getAnswerPart2(List<String> input) {
        Map<String, Integer> cardMap = createCardMap(input);
        int matches;
        for(int i=0;i<input.size(); i++) {
            matches = 0;

            List<Integer> winningNumbers = getNumbers(input.get(i), 0);
            List<Integer> currentNumbers = getNumbers(input.get(i), 1);

            for(int number: winningNumbers) {
                if(currentNumbers.contains(number)) {
                    matches++;
                }
            }
            //if prev was 1 and got 2 matches
            //+ 1 to next 2
            //if prev was 2 and got 4 matches
            //+2 to next 4

            for(int k=0;k<matches; k++) {
                String key = String.valueOf(k+i+2);
                int previousValue = cardMap.get(String.valueOf(i+1));
                cardMap.merge(key, previousValue, Integer::sum);
            }
        }
        int sum = cardMap.values().stream().mapToInt(Integer::intValue).sum();
        System.out.println("Result part 2: " + sum);
    }

    private static Map<String, Integer> createCardMap(List<String> input) {
        Map<String, Integer> cardMap = new HashMap<>();
        for(int i=0;i<input.size(); i++) {
            cardMap.put(String.valueOf(i + 1), 1);
        }
        return cardMap;
    }

    private static String getFilePath() {
        String packageName = Main.class.getPackage().getName().replace(".", "\\");
        return System.getProperty("user.dir") + "\\src\\" + packageName + FILE_NAME;
    }
    private static List<String> readInput() {

        List<String> lines = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(getFilePath()))) {
            String line;
            while ((line = br.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lines;
    }
}