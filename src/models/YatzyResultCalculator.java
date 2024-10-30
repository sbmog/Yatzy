package models;

public class YatzyResultCalculator {
    private int[] eyesCount = new int[6];
    private Die[] diceThrow;

    public YatzyResultCalculator(Die[] dice) {
        this.diceThrow = dice;
        doEyeCount();
    }

    public void doEyeCount() {
        for (Die die : diceThrow) {
            eyesCount[die.getDie() - 1]++;
        }
    }



    public int upperSectionScore(int eyes) {
        return eyesCount[eyes - 1] * eyes;
    }

    public int onePairScore() {
        for (int indeks = 5; indeks > -1; indeks--) {
            if (eyesCount[indeks] >= 2) {
                return (indeks + 1) * 2;
            }
        }
        return 0;
    }

    public int twoPairScore() {
        int numberOfPairs = 0;
        int sum = 0;

        for (int indeks = 5; indeks > -1; indeks--) {
            if (eyesCount[indeks] >= 2) {
                sum += (indeks + 1) * 2;
                numberOfPairs++;
            }
        }
        if (numberOfPairs >= 2) {
            return sum;
        }
        return 0;
    }

    public int threeOfAKindScore() {
        for (int indeks = 5; indeks > -1; indeks--) {
            if (eyesCount[indeks] >= 3) {
                return (indeks + 1) * 3;
            }
        }
        return 0;
    }

    public int fourOfAKindScore() {
        for (int indeks = 5; indeks > -1; indeks--) {
            if (eyesCount[indeks] >= 4) {
                return (indeks + 1) * 4;
            }
        }
        return 0;
    }

    public int smallStraightScore() {
        int sum = 0;
        for (int indeks = 0; indeks < eyesCount.length - 1; indeks++) {
            if (eyesCount[indeks] > 0) {
                sum += indeks + 1;
            }
        }
        if (sum == 15) {
            return 15;
        }
        return 0;
    }

    public int largeStraightScore() {
        int sum = 0;
        for (int indeks = 5; indeks > 0; indeks--) {
            if (eyesCount[indeks] > 0) {
                sum += indeks + 1;
            }
        }
        if (sum == 20) {
            return 20;
        }
        return 0;
    }

    public int fullHouseScore() {
        int threeOfAKindValue = 0;
        int pairValue = 0;

        for (int indeks = 5; indeks > -1; indeks--) {
            if (eyesCount[indeks] == 3) {
                threeOfAKindValue = (indeks + 1) * 3;
            }
            if (eyesCount[indeks] == 2) {
                pairValue = (indeks + 1) * 2;
            }
        }
        if (threeOfAKindValue > 0 && pairValue > 0) {
            return threeOfAKindValue + pairValue;
        }
        return 0;
    }

    public int chanceScore() {
        int sum = 0;
        for (Die die : diceThrow) {
            sum += die.getDie();
        }
        return sum;
    }

    public int yatzyScore() {
        for (int count : eyesCount) {
            if (count == 5) {
                return 50;
            }
        }
        return 0;
    }
}
