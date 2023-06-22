package GC_11.model;

import GC_11.network.choices.Choice;


public class Pair {

    private Choice choice;
    private String[] params;

    public Pair(Choice choice, String[] params) {
        this.choice = choice;
        this.params = params;
    }

    public Choice getChoice() {
        return this.choice;
    }

    public String[] getParams() {
        return this.params;
    }

}