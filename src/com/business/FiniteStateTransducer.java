package com.business;

import com.model.State;
import com.model.Transition;
import com.util.ReturnType;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * this class represents a finite state transducer (FST) in the Theory of languages and machines
 */
public class FiniteStateTransducer {
    private ArrayList<State> states;

    public FiniteStateTransducer() {
        this.states = new ArrayList<State>();
    }

    /**
     * This method gets the states of the FST by looking for it's name in the states arraylist
     *
     * @param name the name of the state we are looking for
     * @return the state if found, null otherwise
     */
    public State getStateByName(String name) {
        for (State state : states) {
            if (state.getName().equals(name)) {
                return state;
            }
        }
        return null;
    }

    /**
     * This method adds a state to the FST
     *
     * @param name    the name of the state we are adding
     * @param isFinal true if the state is a final state, false otherwise
     */
    public void addState(String name, boolean isFinal) {
        this.states.add(new State(name, isFinal));
    }

    /**
     * This method adds a Transition to the FST
     *
     * @param inStateName  name of the state where the transition starts
     * @param input        the input character of the transition
     * @param output       the output character of the transition
     * @param outStateName name of the state where the transition ends
     */
    public void addTransition(String inStateName, char input, char output, String outStateName) {
        for (State state : this.states) {
            if (state.getName().equals(inStateName)) {
                state.getTransitions().add(new Transition(inStateName, outStateName, input, output));
            }
        }
    }

    /**
     * This method adds transitions from given in,out states to the FST
     * which their input and output characters are the same
     * @param inStateName   name of the state where the transition starts
     * @param input         the characters of the transitions where the input and output are the same for all transitions
     * @param outStateName  name of the state where the transition ends
     */
    public void addSetTransition(String inStateName, String input, String outStateName) {
        for (State state : this.states) {
            if (state.getName().equals(inStateName)) {
                for (char c : input.toCharArray()) {
                    state.getTransitions().add(new Transition(inStateName, outStateName, c, c));
                }
            }
        }
    }

    /**
     * This method parses the input string and returns the output string of the FST by following the transitions
     * if the input string is not accepted by the FST, it returns "FAIL" as output.
     * @param input the input string we want to parse
     * @return the output string/strings of the FST
     */
    public ReturnType parseInput(String input) {
        var result = new HashSet<String>();
        var currentStates = new ArrayList<MiddleState>();
        currentStates.add(new MiddleState(this.getStateByName("q0"), ""));

        for (char c : input.toCharArray()) {
            var nextStates = new ArrayList<MiddleState>();
            for (MiddleState middleState : currentStates) {
                for (Transition transition : middleState.state.getTransitions()) {
                    if (transition.input() == c) {
                        if (transition.output() != '\0') {
                            nextStates.add(new MiddleState(this.getStateByName(transition.outStateName()),
                                    middleState.output + transition.output()));
                        } else {
                            nextStates.add(new MiddleState(this.getStateByName(transition.outStateName()),
                                    middleState.output));
                        }
                    }
                }
            }
            currentStates = nextStates;
        }
        for (MiddleState middleState : currentStates) {
            if (middleState.state.isFinal()) {
                result.add(middleState.output);
            }
        }
        return new ReturnType(result);
    }

    private record MiddleState(State state, String output) {
    }
}
