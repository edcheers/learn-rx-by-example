package com.samples.rx.basics.domain;

public class Account {

    private String name;
    private float availableAmount;
    private int score;

    public Account() {
    }

    public Account(String name, float availableAmount, int score) {
        this.name = name;
        this.availableAmount = availableAmount;
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public float getAvailableAmount() {
        return availableAmount;
    }

    public int getScore() {
        return score;
    }

    @Override
    public String toString() {
        return "Account{" +
                "name='" + name + '\'' +
                ", availableAmount=" + availableAmount +
                ", score=" + score +
                '}';
    }
}
