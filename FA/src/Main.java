import javafx.util.Pair;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        FiniteAutomata fa = readFA("src/FA.in");
        Scanner scanner = new Scanner(System.in);
        while(true) {
            System.out.println("Enter what you want:\n0. Exit\n1. States\n2. Alphabet\n3. Transitions\n4. Initial state\n5. Final states\n6. Check if DFA\n");
            switch (scanner.nextLine()) {
                case "0":
                    return;
                case "1":
                    System.out.println(fa.getStates());
                    break;
                case "2":
                    System.out.println(fa.getAlphabet());
                    break;
                case "3":
                    System.out.println(fa.getTransitions());
                    break;
                case "4":
                    System.out.println(fa.getInitialState());
                    break;
                case "5":
                    System.out.println(fa.getFinalStates());
                    break;
                case "6":
                    System.out.println("Is DFA:" + fa.checkIfDFA());
                    break;
            }
        }
    }

    static FiniteAutomata readFA(String inFile) {
        try {
            FiniteAutomata fa = new FiniteAutomata();
            Scanner in = new Scanner(new File(inFile));
            in.nextLine(); // jump after comment

            Set<String> states;
            Set<String> alphabet;
            Set<String> finalStates;
            String initialState = "";

            states = Set.of(in.nextLine().split(" "));
            alphabet = Set.of(in.nextLine().split(" "));
            while(in.hasNextLine()) {
                String[] trans = in.nextLine().split(" ");
                if(trans.length == 1) {
                    initialState = trans[0];
                    break;
                }
                fa.addTransition(trans[0], trans[1], trans[2]);
            }
            finalStates = Set.of(in.nextLine().split(" "));
            fa.setStates(states);
            fa.setAlphabet(alphabet);
            fa.setFinalStates(finalStates);
            fa.setInitialState(initialState);
            return fa;
        } catch (IOException e) {
            e.printStackTrace();
            return new FiniteAutomata();
        }

    }
}
