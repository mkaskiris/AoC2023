package com.aoc.day1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) {

        List<String> input = readInput();

        int sum = 0;
        int sumPt2=0;
        for(String s: input){
            sum += findDigit(s);
            sumPt2 += findDigitsPt2(s);
        }
        System.out.println(sum);
        System.out.println(sumPt2);
    }

    private static int findDigit(String input) {
        List<String> digits = Arrays.stream(input.split("")).toList();
        List<String> integers = new ArrayList<>();
        for(String s: digits) {
            if(isInt(s)) {
                integers.add(s);
            }
        }
        return sum(integers);
    }

    private static int findDigitsPt2(String input) {
        Pattern pattern = Pattern.compile("(?=(one|two|three|four|five|six|seven|eight|nine|1|2|3|4|5|6|7|8|9))");

        Matcher matcher = pattern.matcher(input);
        List<String> matches = new ArrayList<>();

        while (matcher.find()) {
            matches.add(matcher.group(1));
        }
        List<String> integers = new ArrayList<>();
        for(String s: matches) {
            integers.add(inputMapper(s));
        }
        return sum(integers);
    }

    private static int sum(List<String> integers) {
        if(!integers.isEmpty()){
            String number = integers.get(0) + integers.get(integers.size() - 1);
            return Integer.parseInt(number);
        }else{
            return 0;
        }
    }

    private static String inputMapper(String s){
        return switch (s) {
            case "one" -> "1";
            case "two" -> "2";
            case "three" -> "3";
            case "four" -> "4";
            case "five" -> "5";
            case "six" -> "6";
            case "seven" -> "7";
            case "eight" -> "8";
            case "nine" -> "9";
            default -> s;
        };
    }

    private static boolean isInt(String s) {
        try{
            Integer.parseInt(s);
            return true;
        }catch(NumberFormatException e) {
            return false;
        }
    }

    private static String getFilePath() {
        String packageName = Main.class.getPackage().getName();
        packageName = packageName.replace(".", "\\");
        String fileName = "\\input.txt";
        return System.getProperty("user.dir") + "\\src\\" + packageName + fileName;
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