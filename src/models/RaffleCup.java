package models;

public class RaffleCup {
    private Die[] dice = new Die[5];

    public RaffleCup() {
        throwDice();
    }

    public void throwDice() {
        for (int index = 0; index < dice.length; index++) {
            Die die = new Die();
            die.roll();
            dice[index] = die;
        }
    }

    public Die[] getDice() {
        return dice;
    }
}
