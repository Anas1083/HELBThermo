package com.example.helbthermo;

import javafx.scene.Scene;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.Modality;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.geometry.Pos;

/*
* Cette classe représente la fenetre qui s ouvre lorsque l'on clique sur une cellule avec l'option
* de changer les caractéristique de la cellule en question.
*/
public class CellView {
    private static boolean isDead = false;
    private static boolean isHeatSource = false;

    private static int minWidth = 250;

    public static Cell display(Cell cell)
    {
        Cell newCell;
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL); //focus sur la fenetre
        window.setTitle("Cell description: ");
        window.setMinWidth(minWidth);
        VBox layout = new VBox();
        Label positionLabel = new Label("position of the cell: X="+cell.getX()+" Y= "+cell.getY());
        Label temperatureLabel= new Label("Temperature: "+cell.getTemperature());
        RadioButton deadcell= new RadioButton("define the cell like dead:");
        layout.getChildren().addAll(positionLabel,temperatureLabel,deadcell);

        if(cell.isAlive)
        {
            deadcell.setSelected(false);
        }
        else
        {
            deadcell.setSelected(true);
        }
        RadioButton heatSource= new RadioButton("define the cell like a heat Source");
        if(cell instanceof HeatSource)
        {
            heatSource.setSelected(true);
        }
        else
        {
            heatSource.setSelected(false);
        }



        Label tempSourceLabel = new Label("Temperature of the source when its activated");
        TextField temperaturesource =new TextField(""+cell.temperature);

        Button validate = new Button("validate");

        validate.setOnAction(e -> {

                window.close();

        });
        layout.getChildren().addAll(heatSource,tempSourceLabel,temperaturesource,validate);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();
        if(deadcell.isSelected()){
            cell= new DeadCell(cell.getX(), cell.getY(),0);

        }
        else if(heatSource.isSelected())
        {
            cell= new HeatSource(cell.getX(), cell.getY(), Integer.parseInt(temperaturesource.getText()));
        }

        return cell;

    }
}
