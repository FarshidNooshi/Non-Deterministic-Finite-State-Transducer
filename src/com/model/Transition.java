package com.model;

public record Transition(String inStateName, String outStateName, char input, char output) {

}
