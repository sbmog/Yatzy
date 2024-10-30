package models;

import java.util.Random;

public class Die {
    private int eyes = 0;
    private final Random random = new Random();

    public Die() {
        roll();
    }

//    laver selve terning rullet for en terning
    public void roll() {
        this.eyes = (int) (Math.random() * 6 + 1);
    }

    public int getDie() {
        return eyes;
    }
}
