package com.example.helbthermo;

public class OutsideCell extends Cell{

    public static int outsideTemperature;
    public OutsideCell(int x, int y, int temperature) {
        super(x, y, temperature);
    }

    @Override
    public void updateCellTemperature(int outsidetemperature) {

        outsideTemperature= outsidetemperature;
        temperature= outsidetemperature;
    }


}
