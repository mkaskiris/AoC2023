package com.aoc.day3;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    private static final String FILE_NAME = "\\input.txt";

    public static void main(String[] args) {

        List<String> input = readInput();
//        System.out.println(input);
        getAnswerPart1(input);
        getAnswerPart2(input);
    }

    private static void getAnswerPart1(List<String> input) {
        /*
        split rows into threes (make a queue)
        check second item for symbols
         */
        List<String> queue = new ArrayList<>();
        List<String> answer = new ArrayList<>();
        for(String s: input) {
            queue.add(s);
            if(queue.size() == 3) {
                String[] middle = queue.get(1).split("");
                for(int i=0; i<middle.length; i++) {
                    if(isASymbol(middle[i])){
                        //look above
                        answer.addAll(getAdjacentNumbers(queue, i, 0));
                        //look below
                        answer.addAll(getAdjacentNumbers(queue, i,2 ));
                        //look horizontally
                        answer.addAll(getAdjacentNumbers(queue, i, 1));
                    }
                }
                queue.remove(0);
            }
        }
        int sum = answer.stream().mapToInt(Integer::parseInt).sum();
        System.out.println("Adjacent Numbers: " + answer);
        System.out.println("Result part 1: " + sum);
    }

    private static List<String> getAdjacentNumbers(List<String> q, int index, int line) {
        //3 lines
        //index = index of symbol
        //line to check
        List<String> adjNumbers = new ArrayList<>();
        String[] lineToCheck = q.get(line).split("");

        StringBuilder number = new StringBuilder();
        //number at index of symbol
        if(isInt(lineToCheck[index])) {
            int tempIndex= index;
            number = new StringBuilder(lineToCheck[tempIndex]);
            //check right
            while(tempIndex + 1 < lineToCheck.length && isInt(lineToCheck[tempIndex + 1])) {
                number.append(lineToCheck[tempIndex + 1]);
                tempIndex++;
            }
            //check left
            tempIndex = index;
            while(tempIndex - 1 >= 0 &&  isInt(lineToCheck[tempIndex - 1])) {
                number.insert(0, lineToCheck[tempIndex - 1]);
                tempIndex--;
            }

            adjNumbers.add(number.toString());
            return adjNumbers;
        }
        //check left of symbol
        if(index-1 >= 0 && isInt(lineToCheck[index-1])) {
            int tempIndex =  index - 1;
            while(tempIndex >= 0 && isInt(lineToCheck[tempIndex])) {
                number.insert(0, lineToCheck[tempIndex]);
                tempIndex--;
            }
            adjNumbers.add(number.toString());
            number = new StringBuilder();
        }

        //check right of symbol
        if(index+1 <= lineToCheck.length && isInt(lineToCheck[index+1])) {
            int tempIndex =  index + 1;
            while(tempIndex < lineToCheck.length && isInt(lineToCheck[tempIndex])) {
                number.append(lineToCheck[tempIndex]);
                tempIndex++;
            }
            adjNumbers.add(number.toString());
        }

        return adjNumbers;
    }

    private static boolean isASymbol(String s) {
        try{
            return !s.equals(".") && !isInt(s);
        }catch(Exception e){
            return false;
        }
    }

    private static boolean isInt(String s) {
        try{
            Integer.parseInt(s);
            return true;
        }catch(NumberFormatException e) {
            return false;
        }
    }

    private static void getAnswerPart2(List<String> input) {

        List<String> queue = new ArrayList<>();
        List<String> adjNumbers = new ArrayList<>();
        List<Long> gearRatios = new ArrayList<>();

        for(String s: input) {
            queue.add(s);
            if(queue.size() == 3) {
//
                String[] middle = queue.get(1).split("");
                for(int i=0; i<middle.length; i++) {
                    if(isASymbol(middle[i])){
                        //look above
                        adjNumbers.addAll(getAdjacentNumbers(queue, i, 0));
                        //look below
                        adjNumbers.addAll(getAdjacentNumbers(queue, i,2 ));
                        //look horizontally
                        adjNumbers.addAll(getAdjacentNumbers(queue, i, 1));
                    }
                    if(adjNumbers.size() == 2) {
                        long gearRatio = adjNumbers.stream()
                                .mapToLong(Long::parseLong) // Convert each string to long
                                .reduce(1, (a, b) -> a * b);
                        gearRatios.add(gearRatio);
                    }
                    adjNumbers.clear();
                }
                queue.remove(0);
            }
        }
        long answer = gearRatios.stream().reduce(0L, Long::sum);
        System.out.println("Result part 2: " + answer);
    }

    private boolean isGear(String s){
        return s.equals("*");
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