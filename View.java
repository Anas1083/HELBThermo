package com.example.helbthermo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.geometry.Insets;

/*Cette classe représente la vue principale de l application avec les différent label et button représentant les cellules
*
* */
public class View {
    private Stage stage;
    private Scene scene;

    private int vboxSpacing = 20; //spacing value
    private int vboxPadding = 50; //padding value
    private int hboxSpacing = 100; //spacing value
    private int hboxPadding = 50; //padding value
    private int winHeigth = 600;
    private int winWidth = 800;

    public Label timeLabel;
    public Label moneyLabel;
    public Label outsideTempLabel;
    public Label meanTempLabel;
    public ComboBox<String> heatingModeComboBox;

    public Button playButton;
    public Button pauseButton;
    public Button reStartButton;

    public Button buttonHeat1;
    public Button buttonHeat2;
    public Button buttonHeat3;
    public Button buttonHeat4;

    public GridPane gridPane;


    public View(Stage stage)
    {
        this.stage = stage;
         timeLabel = new Label("Temps");
         timeLabel.setStyle("-fx-border-color: grey; -fx-padding: 5px;");
         outsideTempLabel= new Label("T° ext");
         outsideTempLabel.setStyle("-fx-border-color: grey; -fx-padding: 5px;");
         meanTempLabel = new Label("T° moy");
         meanTempLabel.setStyle("-fx-border-color: grey; -fx-padding: 5px;");
         playButton= new Button("▶");
         pauseButton= new Button("⏸");
         reStartButton=new Button("\uD83D\uDD04");
         buttonHeat1 =new Button("S1");
         buttonHeat2=new Button("S2");
         buttonHeat3=new Button("S3");
         buttonHeat4=new Button("S4");
        gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.add(timeLabel,0,0);
        gridPane.add(outsideTempLabel,2,0);
        gridPane.add(meanTempLabel,3,0);
        gridPane.add(playButton,0,1);
        gridPane.add(pauseButton,1,1);
        gridPane.add(reStartButton,2,1);


        scene = new Scene(gridPane, winWidth, winHeigth);
        stage.setScene(scene);
        stage.show();
    }
    public void initView(CellsGroup cellsGroup)
    {
        int heatsourceCpt=0;
        VBox colBox = new VBox();
        meanTempLabel.setText(""+cellsGroup.getAverageTemperature());
        for(int i = 0; i<cellsGroup.getNumberOfRows();i++)
        {
            HBox rowBox = new HBox();
            for(int j =0; j<cellsGroup.getNumberOfColumns();j++)
            {
                Button cellButton;

                if(cellsGroup.tabCell[i][j] instanceof HeatSource)
                {
                    heatsourceCpt++;
                    cellButton = new Button("S"+heatsourceCpt );
                     Button heatSourceButton = new Button("S"+heatsourceCpt+": "+cellsGroup.tabCell[i][j].temperature);
                     heatSourceButton.setStyle(setTemperatureColor(cellsGroup.tabCell[i][j].temperature));
                    int finalI1 = i;
                    int finalJ1 = j;
                    heatSourceButton.setOnAction(new EventHandler<ActionEvent>() {
                         @Override
                         public void handle(ActionEvent actionEvent) {
                             cellsGroup.tabCell[finalI1][finalJ1].changeAlive();

                         }
                     });
                    gridPane.add(heatSourceButton,cellsGroup.getNumberOfRows()+3,heatsourceCpt);
                    cellButton.setStyle(setTemperatureColor(cellsGroup.tabCell[i][j].temperature));
                }
                else if(cellsGroup.tabCell[i][j] instanceof DeadCell)
                {
                    cellButton = new Button("00");
                    cellButton.setStyle("-fx-background-color: rgb(0,0,0); -fx-padding: 5px;");
                }
                else
                {
                    cellButton = new Button(""+cellsGroup.tabCell[i][j].temperature );
                    cellButton.setStyle(setTemperatureColor(cellsGroup.tabCell[i][j].temperature));
                }

                int finalI = i;
                int finalJ = j;
                cellButton.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        cellsGroup.tabCell[finalI][finalJ]= CellView.display(cellsGroup.tabCell[finalI][finalJ]);
                    }
                });

                gridPane.add(cellButton, i+2, j+2);

            }
        }

    }

    public String setTemperatureColor(int temperature)
    {
        String color="";

        if(temperature==0)
        {
            color="-fx-background-color: rgb(255,255,255);";
        }
        else if(temperature>0&&temperature<=CellsGroup.maxTemperature/4)
        {
            color="-fx-background-color: rgb(255,191,191);";
        }
        else if(temperature>CellsGroup.maxTemperature/4&&temperature<=CellsGroup.maxTemperature/2)
        {
            color="-fx-background-color: rgb(255,127,127);";
        }
        else if (temperature>CellsGroup.maxTemperature/2&&temperature<=CellsGroup.maxTemperature) {
            color="-fx-background-color: rgb(255,0,0);";
        } else if (temperature<0&&temperature>=CellsGroup.minTemperature/4)
        {
            color="-fx-background-color: rgb(191,191,255);";
        } else if (temperature<CellsGroup.minTemperature/4&&temperature>=CellsGroup.minTemperature/2)
        {
            color="-fx-background-color: rgb(127,127,255);";
        }
        else if(temperature<=CellsGroup.minTemperature/2&&temperature >=CellsGroup.minTemperature)
        {
            color="-fx-background-color: rgb(0,0,255);";
        }



        return color;
    }




}
