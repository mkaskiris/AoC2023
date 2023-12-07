package com.aoc.day7;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

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
        int answer = 0;
        Map<String, Integer> cardsInHand = new HashMap<>(); //map of how many of each card are in hand
        Map<String, Integer> unsortedMap = new LinkedHashMap<>();
        Map<String, Integer> cardPowerMap = getCardPowerMap();
        List<String> bids = new ArrayList<>();

        for(String s: input) {
            String[] hand = s.split(" ")[0].split("");
            for(String item: hand) {
                if(cardsInHand.containsKey(item)) {
                    cardsInHand.merge(item, 1, Integer::sum);
                }else{
                    cardsInHand.put(item, 1);
                }
            }
            unsortedMap.put(s, getHandStrength(cardsInHand));
            cardsInHand.clear();
        }
        List<String> keyMap;
        for(int i=1;i<8;i++) {
            int finalI = i;
            keyMap = unsortedMap.entrySet().stream()
                    .filter(item -> item.getValue().equals(finalI))
                    .map(Map.Entry::getKey)
                    .collect(Collectors.toList());

            keyMap.sort((item1, item2) -> {
                for (int j = 0; j < 5; j++) {
                    char char1 = item1.charAt(j);
                    char char2 = item2.charAt(j);

                    int value1 = cardPowerMap.get(String.valueOf(char1));
                    int value2 = cardPowerMap.get(String.valueOf(char2));

                    if (value1 != value2) {
                        return Integer.compare(value1, value2);
                    }
                }
                // If the common prefix is equal, shorter string comes first
                return Integer.compare(item1.length(), item2.length());
            });

            bids.addAll(keyMap);
        }
        for(int i=0; i<bids.size();i++) {
            answer += Integer.parseInt(bids.get(i).split(" ")[1]) * (i+1);
        }
        System.out.println("Result part 1: " + answer);
    }

    private static Map<String, Integer> getCardPowerMap() {
        Map<String, Integer> map = new HashMap<>();
        List<String> cardTypes = List.of("2","3","4","5","6","7","8","9","T","J","Q","K","A");
        int power = 1;
        for(String s: cardTypes) {
            map.put(s, power);
            power++;
        }
        return map;
    }

    static int getHandStrength(Map<String,Integer> hand) {
        if(hand.size() == 1) {
//            System.out.println("Five of a kind");
            return 7;
        }
        else if(hand.size() == 2) {
            if(hand.containsValue(4)) {
//                System.out.println("Four of a kind");
                return 6;
            }else {
//                System.out.println("Full house");
                return 5;
            }
        }
        else if(hand.size() == 3) {
            if(hand.containsValue(3)) {
//                System.out.println("Three of a kind");
                return 4;
            }else{
//                System.out.println("Two pair");
                return 3;
            }
        }
        else if(hand.size() == 4) {
//            System.out.println("One pair");
            return 2;
        }
        else {
//            System.out.println("High card");
            return 1;
        }
    }

    private static void getAnswerPart2(List<String> input) {
        int answer = 0;
        Map<String, Integer> cardsInHand = new HashMap<>(); //map of how many of each card are in hand
        Map<String, Integer> unsortedMap = new LinkedHashMap<>();
        Map<String, Integer> cardPowerMap = getCardPowerMapPt2();
        System.out.println(cardPowerMap);
        List<String> bids = new ArrayList<>();


        for(String s: input) {
            String[] hand = s.split(" ")[0].split("");
            for(String item: hand) {
                if(cardsInHand.containsKey(item)) {
                    cardsInHand.merge(item, 1, Integer::sum);
                }else{
                    cardsInHand.put(item, 1);
                }
            }
            unsortedMap.put(s, getHandStrengthPt2(cardsInHand));
            cardsInHand.clear();
        }
        List<String> keyMap;
        for(int i=1;i<8;i++) {
            int finalI = i;
            keyMap = unsortedMap.entrySet().stream()
                    .filter(item -> item.getValue().equals(finalI))
                    .map(Map.Entry::getKey)
                    .collect(Collectors.toList());

            keyMap.sort((item1, item2) -> {
                for (int j = 0; j < 5; j++) {
                    char char1 = item1.charAt(j);
                    char char2 = item2.charAt(j);

                    int value1 = cardPowerMap.get(String.valueOf(char1));
                    int value2 = cardPowerMap.get(String.valueOf(char2));

                    if (value1 != value2) {
                        return Integer.compare(value1, value2);
                    }
                }
                // If the common prefix is equal, shorter string comes first
                return Integer.compare(item1.length(), item2.length());
            });

            bids.addAll(keyMap);
        }
        System.out.println("Results map" + unsortedMap);
        System.out.println("sorted results " + bids);
        for(int i=0; i<bids.size();i++) {
            answer += Integer.parseInt(bids.get(i).split(" ")[1]) * (i+1);
        }
        System.out.println("Result part 2: " + answer );
    }

    private static Map<String, Integer> getCardPowerMapPt2() {
        Map<String, Integer> map = new HashMap<>();
        List<String> cardTypes = List.of("J","2","3","4","5","6","7","8","9","T","Q","K","A");
        int power = 1;
        for(String s: cardTypes) {
            map.put(s, power);
            power++;
        }
        return map;
    }

    static int getHandStrengthPt2(Map<String,Integer> hand) {
        if(hand.size() == 1) {
//            System.out.println("Five of a kind");
            return 7;
        }
        else if(hand.size() == 2) {
            //JJJJ A -> 7
            //AAAA J -> 7
            //AAAA K -> 6
            if(hand.containsValue(4)) {
//                System.out.println("Four of a kind");
                return hand.containsKey("J") ? 7 : 6;
            }else {
                //JJJ AA -> 7
                // JJ AAA -> 7
                // AAA KK -> 5
//                System.out.println("Full house");
                return hand.containsKey("J") ? 7 : 5;
            }
        }
        else if(hand.size() == 3) {
            //JJJ A K -> 6pt
            //J 111 2 -> 6pt
            //111 2 3 -> 4pt
            if(hand.containsValue(3)) {
//                System.out.println("Three of a kind");
                return hand.containsKey("J") ? 6 : 4;
            }else{
                //JJ AA K -> 6pt
                //J 11 22 -> 5pt
                //11 22 3 -> 3pt
//                System.out.println("Two pair");
                return hand.containsKey("J") ? hand.get("J") == 2 ? 6 : 5: 3;
            }
        }
        else if(hand.size() == 4) {
//            System.out.println("One pair");
            return hand.containsKey("J") ? 4 : 2;
            //J K AA 1
            //JJ A K 1
        }
        else {
//            System.out.println("High card");
            return hand.containsKey("J") ? 2 : 1;
        }
    }
    /*nothing = 1pt
    pair = 2
    2 pair = 3
    3 of a kind = 4
    FH = 5
    4 of a kind = 6
    5 of a kind = 7
    */

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