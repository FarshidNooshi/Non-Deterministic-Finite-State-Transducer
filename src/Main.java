import com.business.FiniteStateTransducer;

public class Main {
    private static String vowels = "aeiou";
    private static String consonants = "bcdfghjklmnpqrstvwxyz";
    private static String alphabet = vowels + consonants;
    private static char lambda = '\0';// lambda is a special character that represents the empty string

    public static void main(String[] args) {
        var fst = new FiniteStateTransducer();
        for (int i = 1; i < 12; i++)
            fst.addState("q" + i, false);
        fst.addState("qf", true);

        fst.addSetTransition("q1", alphabet, "q1");
        fst.addSetTransition("q1", consonants, "q2");
        fst.addSetTransition("q1", "zoxs", "q10");
        fst.addSetTransition("q1", vowels, "q3");
        fst.addTransition("q1", 'f', 'v', "q4");
        fst.addTransition("q1", 's', 's', "q5");
        fst.addTransition("q1", 'c', 'c', "q7");
        fst.addTransition("q5", 's', 's', "q6");
        fst.addTransition("q5", 's', 'h', "q6");
        fst.addTransition("q7", 'h', 'h', "q8");
        fst.addTransition("q3", 'y', 'y', "q9");
        fst.addTransition("q11", lambda, 'i', "q10");
        fst.addTransition("q10", lambda, 'e', "q9");
        fst.addTransition("q9", lambda, 's', "qf");
        fst.addTransition("q2", 'y', lambda, "q11");
        fst.addTransition("q4", 'e', lambda, "q10");
        fst.addTransition("q4", lambda, lambda, "q10");
        fst.addTransition("q6", lambda, lambda, "q10");
        fst.addTransition("q8", lambda, lambda, "q10");
        fst.addTransition("q2", lambda, 's', "qf");
        fst.addTransition("q3", lambda, 's', "qf");


        var result = fst.parseInput("spy");
        System.out.println(result.getResult());
    }
}