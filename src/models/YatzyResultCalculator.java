package models;

public class YatzyResultCalculator {
    private int[] eyesCount = new int[6];
    private Die[] diceThrow;

    public YatzyResultCalculator(Die[] dice) {
        this.diceThrow = dice;
        doEyeCount();
    }

//    metode, der tæller hvor mange gange hvert øjenantal er rullet i et kast
//    eyescount arrayet, bliver derfor opdateret her, hvilket bruges i samtlige metoder herunder
    public void doEyeCount() {
        for (Die die : diceThrow) {
            eyesCount[die.getDie() - 1]++;
        }
    }

//    tæller hvor mange antal der er af parameteren eyes
    public int upperSectionScore(int eyes) {
        return eyesCount[eyes - 1] * eyes;
    }

//    tjekker om der er et par, og hvis dette er tilfældet, returnerer den værdien af parret
//    den tjekker oppefra, så der vil være det største par, som bliver returneret
    public int onePairScore() {
        for (int indeks = 5; indeks > -1; indeks--) {
            if (eyesCount[indeks] >= 2) {
                return (indeks + 1) * 2;
            }
        }
        return 0;
    }

//    tjekker om der der er 2 par, og hvis dette er tilfældet, returnerer den værdien
//    tjekker oppefra igen, for at finde størst muligt
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

//    Tjekker om der er tre, af den samme. Metoden er den samme som onePairScore, bare med 3
    public int threeOfAKindScore() {
        for (int indeks = 5; indeks > -1; indeks--) {
            if (eyesCount[indeks] >= 3) {
                return (indeks + 1) * 3;
            }
        }
        return 0;
    }

//     Tjekker om der er fire, af den samme. Metoden er den samme som onePairScore, bare med 4
    public int fourOfAKindScore() {
        for (int indeks = 5; indeks > -1; indeks--) {
            if (eyesCount[indeks] >= 4) {
                return (indeks + 1) * 4;
            }
        }
        return 0;
    }

//    løber alle terningerne igennem, og bliver kun opfyldt, hvis summen er 15
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

//    løber alle terningerne igennem, og bliver kun opfyldt, hvis summen er 15
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

//    tjekker først om der er tre ens, derefter tjekker den om der er to ens i det resterende
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

//    lægger alle terningerne sammen
    public int chanceScore() {
        int sum = 0;
        for (Die die : diceThrow) {
            sum += die.getDie();
        }
        return sum;
    }

//    tjekker om alle terningerne viser det samme, hvis ja, får man 50 point
    public int yatzyScore() {
        for (int count : eyesCount) {
            if (count == 5) {
                return 50;
            }
        }
        return 0;
    }
}