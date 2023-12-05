package com.aoc.day2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    private static final String FILE_NAME = "\\input.txt";
    static int red = 12;
    static int green = 13;
    static int blue = 14;
    static int maxRed = 0;
    static int maxGreen = 0;
    static int maxBlue = 0;

    public static void main(String[] args) {

        List<String> input = readInput();

        getAnswerPart1(input);
        getAnswerPart2(input);
    }

    private static void getAnswerPart1(List<String> input) {
        int gameNumber = 0;
        int answer = 0;
        boolean add;

        for (String s : input) {
            add = true;
            gameNumber ++;
            for (String i : getDrawsForGame(s)) {
                for (String j : getSpecificDraw(i)) {
                    if(!validNumberDrawnOfColor(j)) {
                        add = false;
                        break;
                    }
                }
            }
            if(add) {
                answer += gameNumber;
            }
        }
        System.out.println("Result part 1: " + answer);
    }

    private static void getAnswerPart2(List<String> input) {
        List<Integer> powerOfCubes = new ArrayList<>();
        for (String s : input) {
            maxRed = 0;
            maxBlue = 0;
            maxGreen = 0;
            for (String i : getDrawsForGame(s)) {
                for (String j : getSpecificDraw(i)) {
                    getMinimumNumberOfCubes(j);
                }
            }
            powerOfCubes.add(maxBlue * maxGreen * maxRed);
        }
        System.out.println("Result part 2: " + powerOfCubes.stream().mapToInt(Integer::intValue).sum());
    }

    private static List<String> getDrawsForGame(String s) {
        return Arrays.asList(s.split(":")[1].split(";"));
    }

    private static List<String> getSpecificDraw(String s) {
        return Arrays.asList(s.split(","));
    }

    private static boolean validNumberDrawnOfColor(String input) {
        if(input.contains("red")) {
            return getNumberDrawn(input) <= red;
        }else if(input.contains("blue")) {
            return getNumberDrawn(input) <= blue;
        }else if(input.contains("green")) {
            return getNumberDrawn(input) <= green;
        }
        return true;
    }

    private static int getNumberDrawn(String input) {
        return Integer.parseInt(Arrays.asList(input.trim().split(" ")).get(0));
    }

    private static void getMinimumNumberOfCubes(String input) {
        if(input.contains("red")) {
            maxRed = Math.max(getNumberDrawn(input), maxRed);
        }else if(input.contains("blue")) {
            maxBlue = Math.max(getNumberDrawn(input), maxBlue);
        }else if(input.contains("green")) {
            maxGreen = Math.max(getNumberDrawn(input), maxGreen);        }
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