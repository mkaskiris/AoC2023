package com.aoc.day5;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import me.tongfei.progressbar.ProgressBar;
import me.tongfei.progressbar.ProgressBarStyle;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicLong;


public class Main {
    private static final String FILE_NAME = "\\input.txt";
    public static void main(String[] args) {

        List<String> input = readInput();
        System.out.println(input);
        getAnswerPart1(input);
        getAnswerPart2(input);
    }

    private static void getAnswerPart1(List<String> input) {
        String[] seeds;
        long answer = Long.MAX_VALUE;
        List<List<String>> allMaps = getLists();

        seeds = input.get(0).split(": ")[1].split(" ");

        int mapToAddIndex = -1;
        for(int i = 2; i<input.size(); i++) {
            if(input.get(i).contains("map")) {
                mapToAddIndex++;
                continue;
            }
            allMaps.get(mapToAddIndex).add(input.get(i));
        }
        for(String s: seeds) {
            long result = Long.parseLong(s);
            for(List<String> map: allMaps){
                result = mappingFunction(result, map);
            }
            answer = Math.min(answer, result);
        }

        System.out.println("Result part 1: " + answer);
    }

    private static List<List<String>> getLists() {
        List<List<String>> temp = new ArrayList<>();
        for(int i=0;i<7;i++){
            temp.add(new ArrayList<>());
        }
        return temp;
    }

    private static long mappingFunction(long start, List<String> map) {
        for(String s: map) {
            if(!s.isEmpty() && (start >= Long.parseLong(s.split(" ")[1]) //check seed bigger than source
                        && start <= (Long.parseLong(s.split(" ")[1]) + Long.parseLong(s.split(" ")[2])))) { //check seed less than source + range

                    return Long.parseLong(s.split(" ")[0]) + start - Long.parseLong(s.split(" ")[1]); //map destination
            }
        }
        return start;
    }

    private static void getAnswerPart2(List<String> input) {
        String[] seeds;
        long answer = Long.MAX_VALUE;
        List<List<String>> allMaps = getLists();

        seeds = input.get(0).split(": ")[1].split(" ");

        int mapToAddIndex = -1;
        for(int i = 2; i<input.size(); i++) {
            if(input.get(i).contains("map")) {
                mapToAddIndex++;
                continue;
            }
            allMaps.get(mapToAddIndex).add(input.get(i));
        }

        for(int j=0; j< seeds.length; j+=2){
            long number = Long.parseLong(seeds[j]);
            while(number < (Long.parseLong(seeds[j]) + Long.parseLong(seeds[j+1]))) {
                long result = number;
                for(List<String> map: allMaps){
                    result = mappingFunction(result, map);
                }
                answer = Math.min(answer, result);
                number++;
            }
        }
        System.out.println("Result part 2: " + answer);
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