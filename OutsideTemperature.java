package com.example.helbthermo;
/**
 * Classe représentant la temperature extérieur
 */
public class OutsideTemperature {
    private int temperature;

    public OutsideTemperature(int temperature)
    {
        this.temperature=temperature;
    }
    @Override
    public String toString() {
        return "Temperature:"+ temperature;
    }

    public int getTemperature() {
        return temperature;
    }

    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }
}
