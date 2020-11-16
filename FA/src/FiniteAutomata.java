import java.util.*;

public class FiniteAutomata {
    private static class TransitionParams {
        String state, symbol;
        TransitionParams(String st, String sy) {
            state = st;
            symbol = sy;
        }
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            TransitionParams that = (TransitionParams) o;
            return Objects.equals(state, that.state) &&
                    Objects.equals(symbol, that.symbol);
        }
        @Override
        public int hashCode() {
            return Objects.hash(state, symbol);
        }

        @Override
        public String toString() {
            return "d(" + state + "," + symbol + ")";
        }
    }


    private Set<String> states, alphabet, finalStates;
    private Map<TransitionParams, List<String>> transitions;
    private String initialState;

    public FiniteAutomata(Set<String> states, Set<String> alphabet, String initialState, Set<String> finalStates) {
        this.states = states;
        this.alphabet = alphabet;
        this.finalStates = finalStates;
        this.transitions = new HashMap<>();
        this.initialState = initialState;
    }

    public FiniteAutomata() {
        this.transitions = new HashMap<>();
    }

    void addTransition(String state, String symbol, String resState) {
        TransitionParams params = new TransitionParams(state, symbol);
        if(transitions.containsKey(params)) {
            transitions.get(params).add(resState);
        } else {
            List<String> resStates = new ArrayList<>();
            resStates.add(resState);
            transitions.put(params, resStates);
        }
    }

    public boolean checkIfDFA() {
        for(List<String> resultStates : transitions.values()) {
            if(resultStates.size() > 1)
                return false;
        }
        return true;
    }

    public boolean checkSequence(Queue<String> seq) {
        String currentState = initialState;
        while(!finalStates.contains(currentState)) {
            if(!alphabet.contains(seq.peek()))
                throw new RuntimeException("Sequence not from alphabet!");
            List<String> resState = transitions.get(new TransitionParams(currentState, seq.peek()));
            if(resState != null) {
                currentState = resState.get(0);
                seq.remove();
            }
        }
        return seq.size() == 0;
    }

    @Override
    public String toString() {
        StringBuilder trans = new StringBuilder();
        for(Map.Entry<TransitionParams, List<String>> entry : transitions.entrySet()) {
            for(String res : entry.getValue()) {
                trans.append("d").append(entry.getKey()).append(" = ").append(res).append("\n");
            }
        }
        return "FiniteAutomata{" +
                "states=" + states +
                ", alphabet=" + alphabet +
                ", transitions=" + trans +
                ", initialState='" + initialState + '\'' +
                ", finalStates=" + finalStates +
                '}';
    }

    public Set<String> getStates() {
        return states;
    }

    public void setStates(Set<String> states) {
        this.states = states;
    }

    public Set<String> getAlphabet() {
        return alphabet;
    }

    public void setAlphabet(Set<String> alphabet) {
        this.alphabet = alphabet;
    }

    public Set<String> getFinalStates() {
        return finalStates;
    }

    public void setFinalStates(Set<String> finalStates) {
        this.finalStates = finalStates;
    }

    public Map<TransitionParams, List<String>> getTransitions() {
        return transitions;
    }

    public void setTransitions(Map<TransitionParams, List<String>> transitions) {
        this.transitions = transitions;
    }

    public String getInitialState() {
        return initialState;
    }

    public void setInitialState(String initialState) {
        this.initialState = initialState;
    }
}
