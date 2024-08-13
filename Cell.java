package com.example.helbthermo;

import java.util.ArrayList;
import java.util.List;

public  abstract class Cell {

        private int x;
        private int y;
        private final int maxTemperature=100;
        private final int minTemperature=-50;


        protected int temperature;
        protected List<Cell>neighborsCells;
        protected final int maxneighborsCells= 8;

        protected boolean isAlive;

        public Cell(int x,int y,int temperature)
        {
            this.x=x;
            this.y=y;
            if(temperature>maxTemperature)
            {
                this.temperature=maxTemperature;
            }
            else if(temperature<minTemperature)
            {
                this.temperature=minTemperature;
            }
            else
            {
                this.temperature= temperature;
            }

            this.neighborsCells=new ArrayList<>();
            this.isAlive=true;

        }
       // ajoute a la liste les cellules voisines de la cellule
        public void addNeighborCell(Cell neighbor)
        {
            this.neighborsCells.add(neighbor);
        }
        public boolean isAlive()
        {
            return this.isAlive;
        }
        public void killCell()
        {
            this.isAlive=false;
        }
        public void changeAlive(){
            isAlive = !isAlive;
        }
        public double getTemperature()
        {
            return this.temperature;
        }
        public void setTemperature(int temperature) {
            if(temperature>maxTemperature)
            {
                this.temperature=maxTemperature;
            }
            else if(temperature<minTemperature)
            {
                this.temperature=minTemperature;
            }
            else
            {
                this.temperature= temperature;
            }
        }
        public abstract void updateCellTemperature(int outsidetemperature);


    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
