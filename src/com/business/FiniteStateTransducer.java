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

    public State getStateByName(String name) {
        for (State state : states) {
            if (state.getName().equals(name)) {
                return state;
            }
        }
        return null;
    }

    public void addState(String name, boolean isFinal) {
        this.states.add(new State(name, isFinal));
    }

    public void addTransition(String inStateName, char input, char output, String outStateName) {
        for (State state : this.states) {
            if (state.getName().equals(inStateName)) {
                state.getTransitions().add(new Transition(inStateName, outStateName, input, output));
            }
        }
    }

    public void addSetTransition(String inStateName, String input, String outStateName) {
        for (State state : this.states) {
            if (state.getName().equals(inStateName)) {
                for (char c : input.toCharArray()) {
                    state.getTransitions().add(new Transition(inStateName, outStateName, c, c));
                }
            }
        }
    }

    public void addEpsilonTransition(String inStateName, String outStateName) {
        for (State state : this.states) {
            if (state.getName().equals(inStateName)) {
                state.getTransitions().add(new Transition(inStateName, outStateName, '\0', '\0'));
            }
        }
    }

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
