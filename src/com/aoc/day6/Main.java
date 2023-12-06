package com.aoc.day6;

import me.tongfei.progressbar.ProgressBar;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.LongStream;

public class Main {
    private static final String FILE_NAME = "\\input.txt";
    public static void main(String[] args) {

        List<String> input = readInput();
        System.out.println(input);
        long startTime = System.currentTimeMillis();
        getAnswerPart1(input);
        long endTime = System.currentTimeMillis();
        displayExecutionTime(endTime - startTime);
        startTime = System.currentTimeMillis();
        getAnswerPart2(input);
        endTime = System.currentTimeMillis();
        displayExecutionTime(endTime - startTime);
    }

    private static void getAnswerPart1(List<String> input) {
        int answer = 1;
        List<String> time = new ArrayList<>(Arrays.asList(input.get(0).split(":")[1].trim().split(" ")));
        time.removeAll(List.of(""));
        List<String> distance = new ArrayList<>(Arrays.asList(input.get(1).split(":")[1].trim().split(" ")));
        distance.removeAll(List.of(""));
        for(int i=0;i<time.size();i++) {
            int combinations = getNumberOfWinningCombinations(Integer.parseInt(time.get(i)), Integer.parseInt(distance.get(i)));
            answer *= combinations;
        }

        System.out.println("Result part 1: " + answer);

    }

    static int getNumberOfWinningCombinations(int time, int distance) {
        //can probably use binary search instead of iterating whole array
        int combinations = 0;
        for(int i=1;i<time;i++) {
            int timeLeft = time-i;
            int distanceTravelled = i*timeLeft;
            if(distanceTravelled > distance) {
                combinations++;
            }
        }
        return combinations;
    }

    static long getNumberOfWinningCombinations(long time, long distance) {
        //can probably use binary search instead of iterating whole array
        long combinations = 0;
        for(int i=1;i<time;i++) {
            long timeLeft = time-i;
            long distanceTravelled = i*timeLeft;
            if(distanceTravelled > distance) {
                combinations++;
            }
        }
        return combinations;
    }

    private static void getAnswerPart2(List<String> input) {
        List<String> time = new ArrayList<>(Arrays.asList(input.get(0).split(":")[1].trim().split(" ")));
        time.removeAll(List.of(""));
        String singleTime = String.join("", time);
        List<String> distance = new ArrayList<>(Arrays.asList(input.get(1).split(":")[1].trim().split(" ")));
        distance.removeAll(List.of(""));
        String singleDistance = String.join("", distance);
        ///naive approach worked fine lol
        long answer = getNumberOfWinningCombinations(Long.parseLong(singleTime), Long.parseLong(singleDistance));
        System.out.println("Result part 2: " + answer );
    }

    private static void displayExecutionTime(long executionTime) {
        long hours = executionTime / (60 * 60 * 1000);
        long minutes = (executionTime % (60 * 60 * 1000)) / (60 * 1000);
        long seconds = ((executionTime % (60 * 60 * 1000)) % (60 * 1000)) / 1000;
        long milliseconds = executionTime % 1000;

        System.out.println("Execution Time: " + hours + " hours, " + minutes + " minutes, " + seconds + " seconds, " + milliseconds + " milliseconds");
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