package com.company;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;

/**
 * @author Kateryna Basova KHNUE
 * @version 23 of June 2020
 * Classname Main
 * The main class where all actions will be executed
 *
 * 1. GLOSSARY - 10 points
 *
 *     1.1. Download a text about Harry Potter.
 *     1.2. For each distinct word in the text calculate the number of occurrence.
 *     1.3. Use RegEx.
 *     1.4. Sort in the DESC mode by the number of occurrence.
 *     1.5. Find the first 20 pairs.
 *     1.6  Find all the proper names.
 *     1.7. Count them and arrange in alphabetic order.
 *     1.8. First 20 pairs and names write into to a file test.txt.
 *     1.9. Create a fine header for the file.
 *     1.10 Use Java Collections to demonstrate your experience (Map, List).
 *
 * Show all your skills and experience. All the tricks (i.e. RegEx) will be taken into account.
 **/
public class Main {

    public static void main(String[] args) throws IOException {

        // -----------------------------------------------------------------------
        // 1.1. Download a text about Harry Potter.
        // get text from the file
        String text = new String(Files.readAllBytes(Paths.get("/home/kate/apps/java-final-task/src/harry.txt")));

        // get all words from the text
        String[] allWords = text
                .replaceAll("[\\s\\.\\?\\!,\\-\":;]+", " ")  // 1.3. Use regex
                .split("\\s+");

        // -----------------------------------------------------------------------
        // 1.2. For each distinct word in the text calculate the number of occurrence.
        // create a list of words occurences
        Map<String, Integer> WordsOccurences = new HashMap<>();

        // Check each word
        for (String word : allWords) {
            // if a word is already in HashMap, increment number of word occurrences in 1
            if (WordsOccurences.containsKey(word)) {
                int count = WordsOccurences.get(word);
                WordsOccurences.put(word, count + 1);
            } else {
                // if a word is not in HashMap, add the word to WordsOccurences with occurrence = 1
                WordsOccurences.put(word, 1);
            }
        }

        // -----------------------------------------------------------------------
        // 1.4. Sort in the DESC mode by the number of occurrence.
        // create LinkedHashMap to sort store WordsOccurences in descending order
        LinkedHashMap<String, Integer> DescendingSortedMap = new LinkedHashMap<>();

        // use stream to sort WordsOccurences and save result to DescendingSortedMap
        WordsOccurences.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .forEachOrdered(x -> DescendingSortedMap.put(x.getKey(), x.getValue()));

        // -----------------------------------------------------------------------
        // 1.5. Find the first 20 pairs.
        System.out.println("First 20 pairs:");
        // create Iterator 'items' to be able to switch to next DescendingSortedMap elements from beginning
        Iterator<Map.Entry<String, Integer>> items = DescendingSortedMap.entrySet().iterator();

        // define a path where first 20 pairs will be written
        Path path = Paths.get("/home/kate/apps/java-final-task/src/test.txt");
        // write first 20 pairs to the file test.txt
        for (int i = 0; i < 20; i++) {
            Map.Entry<String, Integer> pair = items.next(); // get next item
            System.out.format("Word: %s, occurences: %d%n", pair.getKey(), pair.getValue());
            // write occurrence pair to the file
            Files.write(path, (pair.getKey() + "\n").getBytes(), StandardOpenOption.APPEND);
        }

        // -----------------------------------------------------------------------
        // 1.6  Find all the proper names.
        // create an ArrayList to store words where first word is upper case
        List<String> properNames = new ArrayList<>();
        // check each distinct word
        for (String value : WordsOccurences.keySet()) {
            if (value.length() > 1 // word has more than 1 letter
                && Character.isUpperCase(value.charAt(0)) // if first letter is upper case
                && Character.isLowerCase(value.charAt(1))) { // if second letter is lower case

                properNames.add(value); // add element to ArrayList properNames
            }
        }

        // -----------------------------------------------------------------------
        // 1.7. Count them and arrange in alphabetic order.
        Collections.sort(properNames); // sort properNames in alphabetic order
        int properNamesAmount = properNames.size(); // count proper names
        System.out.println("Proper names amount: " + properNamesAmount);

        // -----------------------------------------------------------------------
        // 1.8. First 20 pairs and names write into to a file test.txt
        // writing first 20 names to the file test.txt
        System.out.println("First 20 names: ");
        for (int i = 0; i < 20; i++) {
            // printing the result
            System.out.println(properNames.get(i));
            // write proper name to the file
            Files.write(path, (properNames.get(i) + "\n").getBytes(), StandardOpenOption.APPEND);
        }
    }
}
