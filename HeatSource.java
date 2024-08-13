package com.example.helbthermo;

public class HeatSource extends Cell{


    public HeatSource(int x, int y, int temperature) {
        super(x, y, temperature);
        isAlive= false;
    }



    @Override
    public void updateCellTemperature(int temperature) {
        this.temperature=temperature;
    }
  /*  boolean isactive;

    public HeatSource(int x, int y) {
        super(x, y);
    }

    @Override
    public void calculTemperature(int temperature) {

    }
    /*public HeatSource(int x, int y, int temperature) {
        super(x, y, temperature);
        isactive=false;
    }*/

}
