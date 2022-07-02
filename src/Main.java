import com.business.FiniteStateTransducer;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;

public class Main {

    private static String vowels = "aeiou";
    private static String consonants = "bcdfghjklmnpqrstvwxyz";
    private static String temp = "bdghjklmnpqrtvwxz";
    private static String alphabet = vowels + consonants;
    private static char lambda = '\0';// lambda is a special character that represents the empty string

    public static void main(String[] args) {
        var fst = new FiniteStateTransducer();
        for (int i = 1; i <= 10; i++)
            fst.addState("q" + i, false);
        fst.addState("qf", true);

        fst.addSetTransition("q1", alphabet, "q1");
        fst.addSetTransition("q1", vowels, "q2");
        fst.addSetTransition("q1", "s", "q3");
        fst.addSetTransition("q1", "c", "q8");
        fst.addTransition("q1", 'f', 'f', "q9");
        fst.addSetTransition("q1", temp, "q10");
        fst.addTransition("q2", 'f', lambda, "q4");
        fst.addSetTransition("q2", "oszx", "q6");
        fst.addSetTransition("q2", "abcdeghijklmnpqrtuvwy", "q7");
        fst.addTransition("q8", 'y', lambda, "q5");
        fst.addTransition("q3", 'y', lambda, "q5");
        fst.addTransition("q3", 'f', lambda, "q4");
        fst.addTransition("q2", 'y', lambda, "q5");
        fst.addTransition("q8", 'f', lambda, "q4");
        fst.addSetTransition("q3", "zxosh", "q6");
        fst.addSetTransition("q3", "abcdegijklmnpqrtuvw", "q7");
        fst.addSetTransition("q6", "ohxz", "q6");
        fst.addSetTransition("q9", "ef", "q4");
        fst.addSetTransition("q8", "abcdegijklmnpqrstuvw", "q7");
        fst.addTransition("q10", 'y', lambda, "q5");
        fst.addSetTransition("q10", "oszx", "q6");
        fst.addTransition("q10", 'f', 'v', "q6");
        fst.addSetTransition("q10", "abcdeghijklmnpqrtuvw", "q7");
        fst.addTransition("q10", 'y', lambda, "q5");
        fst.addSetTransition("q9", "abcdghijklmnpqrtuvw", "q7");
        fst.addTransition("q4", lambda, 'v', "q6");
        fst.addTransition("q5", lambda, 'i', "q6");
        fst.addTransition("q6", lambda, 'e', "q7");
        fst.addTransition("q7", lambda, 's', "qf");

        try {
            File myObj = new File("src/test.txt");
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                var list = new ArrayList<>(fst.parseInput(data).getResult());
                if (list.size() > 0) {
                    System.out.println(list.stream().max(Comparator.comparingInt(String::length)).get());
                } else {
                    System.out.println("No result");
                }
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}