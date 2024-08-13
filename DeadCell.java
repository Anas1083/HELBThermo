package com.example.helbthermo;

public class DeadCell extends Cell{
    public DeadCell(int x, int y, int temperature) {
        super(x, y, temperature);
    }

    @Override
    public void updateCellTemperature(int outsidetemperature) {

    }
}
