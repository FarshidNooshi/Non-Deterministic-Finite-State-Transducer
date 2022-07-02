package com.model;

import java.util.ArrayList;

/**
 * this class represents a state in a finite state transducer
 * @author: Farshid Nooshi
 * @version: 1.0
 */
public class State {
    private final String name;
    private final ArrayList<Transition> transitions;
    private final boolean isFinal;

    public State(String name, boolean isFinal) {
        this.name = name;
        this.isFinal = isFinal;
        this.transitions = new ArrayList<Transition>();
    }

    public State(String name, ArrayList<Transition> transitions, boolean isFinal) {
        this.name = name;
        this.transitions = transitions;
        this.isFinal = isFinal;
    }

    public String getName() {
        return name;
    }

    public ArrayList<Transition> getTransitions() {
        return transitions;
    }

    public boolean isFinal() {
        return isFinal;
    }
}
