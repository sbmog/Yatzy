package models;

public class ResultStorage {
    private int[] upperSection = new int[6];
    private int[] lowerSection = new int[9];

    private int bonusSum = 0;
    private int upperSectionSum = 0;

    private int totalScore = 0;

//    udregner summen af den øverste section, og returnerer summen
    public int getUpperSectionSum() {
        upperSectionSum = 0;

        for (int upperSectionResult : upperSection) {
            upperSectionSum += upperSectionResult;
        }
        return upperSectionSum;
    }

//    tjekker om øverste sektions sum er 63 eller mere, hvis ja, returnerers bonusværdien på 50
    public int bonusUnlocked() {
        if (upperSectionSum >= 63) {
            return bonusSum = 50;
        }
        return bonusSum;
    }

//    udregner total for hele spillet
    public int getTotalScore() {
        int lowerSectionSum = 0;
        for (int lowerSectionResult : lowerSection) {
            lowerSectionSum += lowerSectionResult;
        }
        return upperSectionSum + bonusSum + lowerSectionSum;
    }

//    gemmer værdien fra runden i det tilsvarende resultat Array
    public void setLowerSection(int result, int indeks) {
        this.lowerSection[indeks] = result;
    }

//    gemmer værdien fra runden i det tilsvarende resultat Array
    public void setUpperSection(int result, int indeks) {
        this.upperSection[indeks] = result;
    }
}