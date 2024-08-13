package com.example.helbthermo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.application.Application;


public class StartingPage {
    private Stage stage;
    private Scene scene;
    GridPane grid;
    TextField textFieldSystemSize;
    Boolean systemSizeOk= false;
    int size;
    TextField textFieldNumberOfHeatSource;
    Boolean numberofHeatSourceOk= false;
    int numberOfHeatSource;
    TextField textFieldTemperatureInitial;
    Boolean temperatureInitialOk= false;
    int temperatureInitial;
    TextField textFieldProbaDeathCell;
    Boolean probaDeathCellOk= false;
    double probaDeathCell;
    RadioButton radioButtonConfigX;
    RadioButton radioButtonConfigPlus;
    ToggleGroup toggleGroup;
    Button nextScene;
/*
* Cette classe représente la page de départ ou l'on configure la vue principale de l'appllication
* (taille du système,nombre de source de chaleur,nombre de cellule mort, ect..)
**/
    public StartingPage(Stage stage)
    {
        this.stage = stage;

        grid= new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        textFieldSystemSize = new TextField();
        textFieldNumberOfHeatSource = new TextField();
        textFieldTemperatureInitial = new TextField();
        textFieldProbaDeathCell = new TextField();

        grid.add(new Label("Taille du système(minimum 3)"), 0, 0);
        grid.add(textFieldSystemSize, 1, 0);

        grid.add(new Label("Nombre de sources de chaleurs(nombre entre 4 et 9"), 0, 1);
        grid.add(textFieldNumberOfHeatSource, 1, 1);

        grid.add(new Label("Température initiale des sources de chaleur(nombre entre -50 et 100:"), 0, 2);
        grid.add(textFieldTemperatureInitial, 1, 2);

        grid.add(new Label("Probabilité cellules mortes(nombre entre 0 et 1"), 0, 3);
        grid.add(textFieldProbaDeathCell, 1, 3);

        radioButtonConfigX = new RadioButton("X");
        radioButtonConfigPlus= new RadioButton("+");
        toggleGroup = new ToggleGroup();/// mettre les deux radio boutton dans un toggle group pourque seulement un seul soit selectionner
        radioButtonConfigX.setToggleGroup(toggleGroup);
        radioButtonConfigPlus.setToggleGroup(toggleGroup);
        radioButtonConfigX.setSelected(true);// par défault le premier choix est selectionner
        grid.add(new Label("Configuration des sources"),0,4);
        grid.add(radioButtonConfigX, 1, 4);
        grid.add(radioButtonConfigPlus, 2, 4);

        nextScene= new Button("lancer la simulation");
        grid.add(nextScene,0,5);
        // Créer la scène avec le GridPane
        Scene scene = new Scene(grid, 400, 250);
        nextScene.setOnAction(e -> {
            isSizeOk(textFieldSystemSize.getText());
            isNumberOfHeatSourceOk(textFieldNumberOfHeatSource.getText());
            isTemperatureInitialOk(textFieldTemperatureInitial.getText());
            isProbaDeathOk(textFieldProbaDeathCell.getText());

            if(systemSizeOk&& temperatureInitialOk&&numberofHeatSourceOk&&probaDeathCellOk)
            {
                if(radioButtonConfigPlus.isSelected()){
                    View vue = new View(stage);
                    Controller controleur = new  Controller(vue,size,numberOfHeatSource,temperatureInitial,probaDeathCell,'+');
                }
                else
                {
                    View vue = new View(stage);
                    Controller controleur = new  Controller(vue,size,numberOfHeatSource,temperatureInitial,probaDeathCell,'X');
                }

            }
            else
            {
                showAlert("Erreur","L' un des champ n'est pas valide réesayer");
            }


        });
        stage.setTitle("Formulaire avec Boutons Radio");
        stage.setScene(scene);
        stage.show();
    }
    // Méthode pour vérifier si une chaîne est un entier
    private boolean isDouble(String text) {
        try {
            Double.parseDouble(text);
            return true;  // La chaîne est un chiffre
        } catch (NumberFormatException e) {
            return false;  // La chaîne n'est pas un entier valide
        }

    }
    private boolean isInteger(String text)
    {
        try{
            Integer.parseInt(text);
            return true;  // La chaîne est un chiffre
        } catch (NumberFormatException e){
            return false ;
        }

    }

    // Méthode pour afficher une alerte en cas d'erreur
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void isSizeOk(String text)
    {
        if(isInteger(text))
        {
           if(Integer.parseInt(text)>=3)
           {
               size=Integer.parseInt(text);
               systemSizeOk=true;
           }
        }
    }
    public void isNumberOfHeatSourceOk(String text)
    {
        if(isInteger(text))
        {
            if(Integer.parseInt(text)>=4 && Integer.parseInt(text)<=9)
            {
                numberOfHeatSource=Integer.parseInt(text);
                numberofHeatSourceOk=true;
            }
        }
    }

    public void isTemperatureInitialOk(String text)
    {
        if(isInteger(text))
        {
            if(Integer.parseInt(text)>=-50 && Integer.parseInt(text)<=100)
            {
                temperatureInitial=Integer.parseInt(text);
                temperatureInitialOk=true;
            }
        }
    }
    public void isProbaDeathOk(String text)
    {
        if(isDouble(text))
        {
            if(Double.parseDouble(text)>=0 && Double.parseDouble(text)<=1)
            {
                probaDeathCell=Double.parseDouble(text);
                probaDeathCellOk=true;
            }
        }
    }
}
