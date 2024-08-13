package com.example.helbthermo;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;


public class Controller {
    private int timeInSeconds = 0;
    private boolean running = false;
    private Timeline timeline;
    private View vue;
    private final int  numberOfRows = 4;
    private final int numberOfCols = 5;
    private final int timerDuration =1;
    private final String outsideTemperatureFilename = "C:/informatiquedegestion/2emeannee/Q4bis/Java/HelbThermo/src/main/java/com/example/helbthermo/simul.data";
    private OutsideTemperatureParser parser;
    private OutsideTemperature newOutsideTemperature;
    private CellsGroup cellsGroup;


   public  Controller(View vue,int systemSize,int numberOfHeatCell, int initialTemperature, double probaDeathCell, char config)
   {

       this.vue = vue;
       cellsGroup = new CellsGroup(systemSize,systemSize,numberOfHeatCell,initialTemperature,probaDeathCell,config,vue);
       vue.initView(cellsGroup);
       parser = new OutsideTemperatureParser(outsideTemperatureFilename);

       configurerActions();
       timeline = new Timeline(
              new KeyFrame(Duration.seconds(timerDuration), e-> updateTimer())
       );
       timeline.setCycleCount(Animation.INDEFINITE);
   }
    private void configurerActions() {
        vue.playButton.setOnAction(e ->{
            startTimer();




        });

        vue.pauseButton.setOnAction(e ->{
            pauseTimer();
        });

        vue.reStartButton.setOnAction(e ->{
            resetTimer();
        });

    }



    private void startTimer() {
        if (!running) {
            timeline.play();
            running = true;
        }

    }

    private void pauseTimer() {
        if (running) {
            timeline.pause();
            running = false;
        }
    }

    private void resetTimer() {
        timeline.stop();
        running = false;
        timeInSeconds = 0; // Réinitialisez la durée du timer
        System.out.println("Timer réinitialisé !");
        vue.timeLabel.setText(""+timeInSeconds+"S");
    }

    private void updateTimer() {

            timeInSeconds++;
        vue.timeLabel.setText(""+timeInSeconds+"S");
        startSimulation();
    }

    private void  startSimulation()
    {
        if(running){
            if(parser.hasNextRequest())
            {
                newOutsideTemperature=parser.getNextTemperature();
                vue.outsideTempLabel.setText(""+newOutsideTemperature.getTemperature());
                OutsideCell.outsideTemperature=newOutsideTemperature.getTemperature();
                cellsGroup.initializeNeiboringCell();
                cellsGroup.updateCellsTemperature();
                vue.initView(cellsGroup);

            }


        }

    }
}