package gui;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import models.RaffleCup;

public class YatzyGui extends Application {
    private Label[] diceLabels = new Label[5];
    private CheckBox[] diceCheckBoxes = new CheckBox[5];
    private RaffleCup raffleCup = new RaffleCup();
    private TextField[] upperSectionScoreTextField = new TextField[6];
    private TextField[] lowerSectionScoreTextField = new TextField[9];
    private TextField sumTextField = new TextField();
    private TextField bonusTextField = new TextField();
    private TextField totalTextField = new TextField();


    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Yatzy");
        GridPane dicePane = new GridPane();
        dicePane.setAlignment(Pos.CENTER);
        dicePane.setHgap(10);
        dicePane.setVgap(10);
        dicePane.setStyle("-fx-border-color: black; -fx-border-width: 1px; -fx-padding: 10;");


        for (int indeks = 0; indeks < 5; indeks++) {
            diceLabels[indeks] = new Label("" + 0);
            diceLabels[indeks].setStyle("-fx-font-size: 18; -fx-border-color: darkGray; -fx-padding: 10; -fx-min-width: 50; -fx-alignment: center; -fx-border-radius: 10;");
            dicePane.add(diceLabels[indeks], indeks, 0);

            diceCheckBoxes[indeks] = new CheckBox("Hold");
            dicePane.add(diceCheckBoxes[indeks], indeks, 1);
        }

//        Skal vi indsætte et rollCount? så den tæller hvor mange kast der er blevet brugt?
        Button rollButton = new Button("Kast terningerne");
        dicePane.add(rollButton, 3, 2, 2, 1);
        rollButton.setOnAction(event -> rollDice());


        GridPane scorePane = new GridPane();
        scorePane.setVgap(5);
        scorePane.setHgap(5);
        scorePane.setPadding(new Insets(10));
        scorePane.setStyle("-fx-border-color: black; -fx-border-width: 1px; -fx-padding: 10;");

        String[] stringsForTheUpperSectionScoreLabels = {"1'ere", "2'ere", "3'ere", "4'ere", "5'ere", "6'ere"};
        for (int indeks = 0; indeks < stringsForTheUpperSectionScoreLabels.length; indeks++) {
            Label upperSectionScoreLabels = new Label(stringsForTheUpperSectionScoreLabels[indeks]);
            scorePane.add(upperSectionScoreLabels, 0, indeks);

            upperSectionScoreTextField[indeks] = new TextField();
            upperSectionScoreTextField[indeks].setEditable(false);
            upperSectionScoreTextField[indeks].setPrefWidth(50);
            scorePane.add(upperSectionScoreTextField[indeks], 1, indeks);
        }

        Label sumLabel = new Label("Sum");
        scorePane.add(sumLabel, 2, 6);

        sumTextField.setEditable(false);
        sumTextField.setPrefWidth(50);
        scorePane.add(sumTextField, 3, 6);

        Label bonusLabel = new Label("Bonus");
        scorePane.add(bonusLabel, 2, 7);

        bonusTextField.setEditable(false);
        bonusTextField.setPrefWidth(50);
        scorePane.add(bonusTextField, 3, 7);

        String[] stringsForTheLowerSectionScoreLabels = {"Et par", "To par", "3 ens", "4 ens", "Lille straight", "Store straight", "Chance", "Yatzy"};
        for (int indeks = 0; indeks < stringsForTheLowerSectionScoreLabels.length; indeks++) {
            Label lowerSectionScoreLabels = new Label(stringsForTheLowerSectionScoreLabels[indeks]);
            scorePane.add(lowerSectionScoreLabels, 0, (indeks + 8));
            lowerSectionScoreTextField[indeks] = new TextField();
            lowerSectionScoreTextField[indeks].setEditable(false);
            lowerSectionScoreTextField[indeks].setPrefWidth(50);
            scorePane.add(lowerSectionScoreTextField[indeks], 1, (indeks + 8));
        }

        Label totalLabel = new Label("Total");
        scorePane.add(totalLabel, 2, 17);

        totalTextField.setEditable(false);
        totalTextField.setPrefWidth(50);
        scorePane.add(totalTextField, 3, 17);

        VBox mainLayout = new VBox(10, dicePane, scorePane);
        mainLayout.setPadding(new Insets(15));
        Scene scene = new Scene(mainLayout);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void rollDice() {
        for (int indeks = 0; indeks < diceLabels.length; indeks++) {
            if (!diceCheckBoxes[indeks].isSelected()) {
                raffleCup.getDice()[indeks].roll();
                int value = raffleCup.getDice()[indeks].getDie();
                diceLabels[indeks].setText("" + value);
            }
        }
    }

//    metode til at koble summen til upperSectionScore sammen med upperSectionScore metoden i calculator
//    Tænker det nemt kan gøres med et for loop, nu når metoden er ens, og det kun er parameteren, som ændre sig


//    Metode (måske en for hvert felt) til at koble summen til lowerSectionScore med de tilsvarende metoder i calculator
//    Det bliver mere individuelt her, så vi skal bruge selve elementerne i lowerSectionScore Arrayet, og huske hvilken der passer til beskrivelserne

//    Tænker metoderne skal kaldes i rollDice metoden, så de bliver aktiveret ved tryk på rollButton
}
