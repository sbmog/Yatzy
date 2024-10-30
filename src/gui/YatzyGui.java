package gui;

import javafx.application.Application;
import javafx.event.Event;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import models.RaffleCup;
import models.ResultStorage;
import models.YatzyResultCalculator;

import java.util.List;

public class YatzyGui extends Application {
    private Label[] diceLabels = new Label[5];
    private CheckBox[] diceCheckBoxes = new CheckBox[5];
    private RaffleCup raffleCup = new RaffleCup();

    private TextField[] upperSectionScoreTextField = new TextField[6];
    private TextField[] lowerSectionScoreTextField = new TextField[9];
    private TextField sumTextField = new TextField();
    private TextField bonusTextField = new TextField();
    private TextField totalTextField = new TextField();

    private int rollCount = 3;
    private Label rollCountLabel = new Label("Antal slag tilbage: " + rollCount);

    private ResultStorage resultStorage = new ResultStorage();

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Yatzy");

//        opret pane til den øverste del
        GridPane dicePane = new GridPane();
        dicePane.setAlignment(Pos.CENTER);
        dicePane.setHgap(10);
        dicePane.setVgap(10);
//        sæt stilen til pane, der bliver der sat en kant på, som er sort og tilføjet padding i kanterne
        dicePane.setStyle("-fx-border-color: black; -fx-border-width: 1px; -fx-padding: 10;");

//        Dicelabels og checkbox hertil bliver tilføjet, placeres íft. Indekset
        for (int indeks = 0; indeks < 5; indeks++) {
            diceLabels[indeks] = new Label("" + 0);
//            stilen sættes til label, skriftstr, kantfarve (mørke grå), padding, bredde, allingment og afrundedekanter
            diceLabels[indeks].setStyle("-fx-font-size: 18; -fx-border-color: darkGray; -fx-padding: 10; -fx-min-width: 50; -fx-alignment: center; -fx-border-radius: 10;");
            dicePane.add(diceLabels[indeks], indeks, 0);

            diceCheckBoxes[indeks] = new CheckBox("Hold");
            dicePane.add(diceCheckBoxes[indeks], indeks, 1);
        }

//        tilføj rollcount label
        dicePane.add(rollCountLabel, 0, 2, 2, 1);

//        tilføj roll button og der kaldes på handling, hvis knappen bliver trykket på
        Button rollButton = new Button("Kast terningerne");
        dicePane.add(rollButton, 3, 2, 2, 1);
        rollButton.setOnAction(event -> rollDice());

//        __________
//        opret pane til den nederste del
        GridPane scorePane = new GridPane();
        scorePane.setVgap(5);
        scorePane.setHgap(5);
        scorePane.setPadding(new Insets(10));
//        sæt stilen til pane, der bliver der sat en kant på, som er sort og tilføjet padding i kanterne
        scorePane.setStyle("-fx-border-color: black; -fx-border-width: 1px; -fx-padding: 10;");

//        array, som holder string til labels for øverste del
        String[] stringsForTheUpperSectionScoreLabels = {"1'ere", "2'ere", "3'ere", "4'ere", "5'ere", "6'ere"};
//        forloop, som tilføjer labels og textfield
        for (int indeks = 0; indeks < stringsForTheUpperSectionScoreLabels.length; indeks++) {
            Label upperSectionScoreLabels = new Label(stringsForTheUpperSectionScoreLabels[indeks]);
            scorePane.add(upperSectionScoreLabels, 0, indeks);

            upperSectionScoreTextField[indeks] = new TextField();
            upperSectionScoreTextField[indeks].setEditable(false);
            upperSectionScoreTextField[indeks].setPrefWidth(50);
            scorePane.add(upperSectionScoreTextField[indeks], 1, indeks);

//            hvis man trykker på textfeltet vil metoden chooseFieldAction komme i brug
            int i = indeks;
            upperSectionScoreTextField[indeks].setUserData("open");
            upperSectionScoreTextField[indeks].setOnMouseClicked(event -> this.chooseFieldAction(event, i, "upper"));
        }

//        label og textfelt til sum bliver tilføjet
        Label sumLabel = new Label("Sum");
        scorePane.add(sumLabel, 2, 6);

        sumTextField.setEditable(false);
        sumTextField.setPrefWidth(50);
        scorePane.add(sumTextField, 3, 6);

//        label og textfelt til bonus bliver tilføjet
        Label bonusLabel = new Label("Bonus");
        scorePane.add(bonusLabel, 2, 7);

        bonusTextField.setEditable(false);
        bonusTextField.setPrefWidth(50);
        scorePane.add(bonusTextField, 3, 7);

//        array, som holder string til labels for nederste del
        String[] stringsForTheLowerSectionScoreLabels = {"Et par", "To par", "3 ens", "4 ens", "Lille straight", "Store straight", "Fuldt hus", "Chance", "Yatzy"};
//        forloop, der tilføjer labels og textfelter
        for (int indeks = 0; indeks < stringsForTheLowerSectionScoreLabels.length; indeks++) {
            Label lowerSectionScoreLabels = new Label(stringsForTheLowerSectionScoreLabels[indeks]);
            scorePane.add(lowerSectionScoreLabels, 0, (indeks + 8));

            lowerSectionScoreTextField[indeks] = new TextField();
            lowerSectionScoreTextField[indeks].setEditable(false);
            lowerSectionScoreTextField[indeks].setPrefWidth(50);
            scorePane.add(lowerSectionScoreTextField[indeks], 1, (indeks + 8));

//            hvis man trykker på textfeltet vil metoden chooseFieldAction komme i brug
            int i = indeks;
            lowerSectionScoreTextField[indeks].setUserData("open");
            lowerSectionScoreTextField[indeks].setOnMouseClicked(event -> this.chooseFieldAction(event, i, "lower"));
        }

//        label og textfelt til total tilføjes
        Label totalLabel = new Label("Total");
        scorePane.add(totalLabel, 2, 17);

        totalTextField.setEditable(false);
        totalTextField.setPrefWidth(50);
        scorePane.add(totalTextField, 3, 17);

//        de to panes bliver tilføjet til en vertical box, som er scenen
        VBox mainLayout = new VBox(10, dicePane, scorePane);
        mainLayout.setPadding(new Insets(15));
        Scene scene = new Scene(mainLayout);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

//    metode til når der bliver trykket på rollButton. terningerne bliver kastet
    private void rollDice() {
//        knappen vil kun udløse nedenstående, hvis man har flere kast tilbage
        if (rollCount > 0) {
//            alle dicelabels bliver løbet igennem og updateret, så frem chackboxen ikke er valgt
            for (int indeks = 0; indeks < diceLabels.length; indeks++) {
                if (!diceCheckBoxes[indeks].isSelected()) {
                    raffleCup.getDice()[indeks].roll();
                    int value = raffleCup.getDice()[indeks].getDie();
                    diceLabels[indeks].setText("" + value);
                }
            }
//              metoden updateResult bliver kaldt
            updateResult();
//            rollcount bliver updateret, både variablen og selve label
            rollCount--;
            rollCountLabel.setText("Antal slag tilbage: " + rollCount);
        } else {
//            hvis rollcount er nul, og der ikke er flere slag tilbage, vil der komme en alert frem, som ikke gør det muligt, at slå igen
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Kast terninger");
            alert.setHeaderText("Ingen kast tilbage");
            alert.setContentText("Vælg et felt at gemme dit kast i");
            alert.show();
        }
    }

//    metode, der updatere vores textfelter med mulige resultater for runden
    private void updateResult() {
        YatzyResultCalculator resultCalculator = new YatzyResultCalculator(raffleCup.getDice());

//        updatere den øverste del, dog kun hvis textfeltet ikke er låst
        for (int indeks = 0; indeks < upperSectionScoreTextField.length; indeks++) {
            if (!upperSectionScoreTextField[indeks].getUserData().equals("locked")) {
                int result = resultCalculator.upperSectionScore(indeks + 1);
                upperSectionScoreTextField[indeks].setText(String.valueOf(result));
            }
        }
//        liste med metoderne fra klassen YatzyResultCalculator
        List<?> list = List.of(resultCalculator.onePairScore(), resultCalculator.twoPairScore(), resultCalculator.threeOfAKindScore(), resultCalculator.fourOfAKindScore(), resultCalculator.smallStraightScore(), resultCalculator.largeStraightScore(), resultCalculator.fullHouseScore(), resultCalculator.chanceScore(), resultCalculator.yatzyScore());
//        updatere den øverste del, dog kun hvis textfeltet ikke er låst
        for (int indeks = 0; indeks < list.size(); indeks++) {
            if (!lowerSectionScoreTextField[indeks].getUserData().equals("locked")) {
                lowerSectionScoreTextField[indeks].setText(String.valueOf(list.get(indeks)));
            }
        }
    }

//    metode til, at låse et textfelt, som sker, hvis der er klikket på dette
    public void chooseFieldAction(Event event, int indeks, String upperOrLower) {
        TextField textField = (TextField) event.getSource();
//        style bliver sat med mørkt baggrundsfarve, for at indikerer at feltet er valgt
        textField.setStyle("-fx-background-color: darkGray;");
//        userdata bliver sat til locked, så feltet ikke længere vil blive updateret, når terningerne bliver kastet
        textField.setUserData("locked");

        int result = Integer.parseInt(textField.getText());
//        tjek om det er en af de øverste textfelter eller en af de nederste
        if (upperOrLower.equals("upper")) {
//            værdien for feltet skal gemmes i objektet af klassen ResultStorage
            resultStorage.setUpperSection(result, indeks);
//            summen for den øverste del skal updateres, når et felt låses
            sumTextField.setText(String.valueOf(resultStorage.getUpperSectionSum()));
//            der tjekkes om betingelserne for bonusfeltet er opfyldt, når et felt i den øverste del låses
            bonusTextField.setText(String.valueOf(resultStorage.bonusUnlocked()));
        } else if (upperOrLower.equals("lower")) {
//            værdien for feltet skal gemmes i objektet af klassen ResultStorage
            resultStorage.setLowerSection(result, indeks);
        }

//        Kald på metoden, så når der klikkes på et textfelt vil runden være slut, og derfor skal alle de interaktive felter resettes
        resetForNewRound();
//        kald på metoden, som tjekker om alle textfelter er låst, hvis ja vil total blive udregnet og indsat
        checkIfAllIsLocked();
    }

//    metode, der resetter alle de ikke låste felter, vores terninger og rollcount til en ny runde
    public void resetForNewRound() {
//        reset diceLabels og diceCheckBox
        for (int index = 0; index < diceLabels.length; index++) {
            diceLabels[index].setText("" + 0);
            diceCheckBoxes[index].setSelected(false);
        }
//        ikke låste felter i øverste del bliver reset
        for (TextField textField : upperSectionScoreTextField) {
            if (!textField.getUserData().equals("locked")) {
                textField.setText("0");
            }
        }
//        ikke låste felter i nederste del bliver reset
        for (TextField textField : lowerSectionScoreTextField) {
            if (!textField.getUserData().equals("locked")) {
                textField.setText("0");
            }
        }
//        rollcount bliver sat til 3 igen
        rollCount = 3;
        rollCountLabel.setText("Antal slag tilbage: " + rollCount);
    }

//    metode, der tjekker om textfelterne er låst, hvis ja vil total blive udregnet og vist
    public void checkIfAllIsLocked() {
//        variabel som tæller hvor mange felter, der er låst
        int numberOfLockedTextFields = 0;

//        tjek øverste del
        for (TextField textField : upperSectionScoreTextField) {
            if (textField.getUserData().equals("locked")) {
                numberOfLockedTextFields++;
            }
        }
//        tjek nederste del
        for (TextField textField : lowerSectionScoreTextField) {
            if (textField.getUserData().equals("locked")) {
                numberOfLockedTextFields++;
            }
        }
//        hvis alle 15 felter er låst, vil metoden getTotalScore, fra vores ResultStorage klasse, blive kaldt og total textfeltet vil blive sat til værdiem herfra
        if (numberOfLockedTextFields == 15) {
            totalTextField.setText(String.valueOf(resultStorage.getTotalScore()));
        }
    }
}