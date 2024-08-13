package com.example.helbthermo;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

/*
* cette classe représente le groupe de cellule
* */
public class CellsGroup {
    private int numberOfRows;
    private int numberOfColumns;
    private int rowWithOutside;
    private int colWithOutside;
    private int numberOfHeatSource;
    private int initialTemperature;
    private int initialNormalCellTemperature=0;
    private double probaDeathCell;
    private char config;
    private int outsideTemperature;
    private int averageTemperature=0;
    public static final int maxTemperature=100;
    public static final int minTemperature=-50;

    Cell[][] tabCell ;
    Cell[][] tabWithOutsideCell ;

    private List<Cell> cells;
    private List<NormalCell> normalCells;
    private List<OutsideCell> outsideCells;
    private List<DeadCell> deadCells;
    private List<HeatSource> heatSourceCells;


    private View observer;
    //public Map<String, HeatSource> heatMap; //Conteneur de HeatSource

    public CellsGroup(int rows, int cols, int numberOfHeatSource, int initialTemperature, double probaDeathCell, char config, View observer) {

        this.numberOfRows = rows;
        this.numberOfColumns = cols;
        this.observer = observer;
        this.numberOfHeatSource = numberOfHeatSource;
        this.initialTemperature = initialTemperature;
        this.probaDeathCell = probaDeathCell;
        this.config = config;
        rowWithOutside= rows+2;
        colWithOutside= cols+2;
        cells = new ArrayList<>();
        normalCells = new ArrayList<>();
        outsideCells = new ArrayList<>();
        deadCells = new ArrayList<>();
        heatSourceCells = new ArrayList<>();
        tabCell = new Cell[numberOfRows][numberOfColumns];
        tabWithOutsideCell = new Cell[rowWithOutside][colWithOutside];
        initializeCells();


    }

    private void initializeCells() {
        initializeOutsideAndNormalCell();// initialise les Cells pas encore de source de chaleur ou de morte
        initializeHeatSource();// initialise les de source de chaleur
        initializeDeadCell();
        for (int row = 0; row < numberOfRows; row++) {
            for (int col =0; col < numberOfColumns; col++) {
                cells.add(tabCell[row][col]);

            }
        }

        initializeNeiboringCell();



    }

    private void initializeOutsideAndNormalCell() {
        // Initialiser le tableau des cellules normales
        for (int row = 0; row < numberOfRows; row++) {
            for (int col = 0; col < numberOfColumns; col++) {
                tabCell[row][col] = new NormalCell(numberOfRows, numberOfColumns, initialNormalCellTemperature);
                normalCells.add((NormalCell) tabCell[row][col]);
            }
        }
        // Initialiser le tableau avec des OutsideCell autour des cellules normales
        for (int row = 0; row < rowWithOutside; row++) {
            for (int col = 0; col < rowWithOutside; col++) {
                if (row ==0 || col == 0 || row == numberOfRows || col == numberOfColumns) {
                    // Placer des OutsideCell autour des cellules normales
                    tabWithOutsideCell[row][col] = new OutsideCell(numberOfRows, numberOfColumns, initialNormalCellTemperature);
                    outsideCells.add((OutsideCell) tabWithOutsideCell[row ][col]);
                }
                else
                {

                    tabWithOutsideCell[row][col] = new NormalCell(numberOfRows, numberOfColumns,initialNormalCellTemperature);
                }
            }
        }
    }


    public void initializeDeadCell()
    {
        for (int row = 0; row < numberOfRows ; row++) {
            for (int col = 0; col < numberOfColumns; col++)
            {
                if(tabCell[row][col] instanceof NormalCell)
                {
                   double death=(Math.random());
                   if(death<=probaDeathCell)
                   {
                       tabCell[row][col] = new DeadCell(row,col,initialNormalCellTemperature);
                       deadCells.add((DeadCell) tabCell[row][col]);
                   }
                }
            }
        }
    }

    private void initializeHeatSource()
    {

        if(config =='+') {
            placeConfigPlus();
        }
        else
        {
            placeConfigX();
        }

    }

    private void placeConfigPlus()
    {
        int cpt=0;
        int left=1;
        int right=1;
        int up=1;
        int down=1;
        for(int i=0; i<numberOfHeatSource;i++)
        {
            if(i==0)
            {
                tabCell[numberOfRows/2][numberOfColumns/2]= new HeatSource(numberOfRows/2,numberOfColumns/2, initialTemperature);
                tabWithOutsideCell[numberOfRows/2+1][numberOfColumns/2+1]=tabCell[numberOfRows/2][numberOfColumns/2];
                heatSourceCells.add((HeatSource) tabCell[numberOfRows/2][numberOfColumns/2]);
            }
            else if(cpt==1)
            {
                //if(tabWithOutsideCell[(numberOfRows/2+1)-up][numberOfColumns/2+1] instanceof OutsideCell)
                if((numberOfRows/2)-up<0)
                 {
                    tabCell[(numberOfRows / 2) + down][numberOfColumns / 2] = new HeatSource((numberOfRows / 2)+ down, numberOfColumns / 2, initialTemperature);
                    tabWithOutsideCell[(numberOfRows / 2+1) + down][numberOfColumns / 2+1] = new HeatSource((numberOfRows / 2+1)+ down, numberOfColumns / 2+1, initialTemperature);
                    heatSourceCells.add((HeatSource) tabCell[(numberOfRows / 2) + down][numberOfColumns / 2]);
                    down++;
                }
                else
                {
                    tabCell[(numberOfRows / 2) - up][numberOfColumns / 2] = new HeatSource((numberOfRows / 2)-up, numberOfColumns / 2, initialTemperature);
                    tabWithOutsideCell[(numberOfRows / 2+1) -up][numberOfColumns / 2+1] = new HeatSource((numberOfRows / 2+1)-up, numberOfColumns / 2+1, initialTemperature);
                    heatSourceCells.add((HeatSource) tabCell[(numberOfRows / 2) -up][numberOfColumns / 2]);
                    up++;
                }
            }
            else if(cpt==2)
            {
                //if(tabWithOutsideCell[(numberOfRows/2+1)][numberOfColumns/2+1+right] instanceof OutsideCell)
                 if(numberOfColumns/2+1+right>= numberOfColumns)
                {
                    tabCell[numberOfRows / 2 ][numberOfColumns/ 2 -left] = new HeatSource((numberOfRows / 2), numberOfColumns / 2-left, initialTemperature);
                    tabWithOutsideCell[(numberOfRows / 2+1) ][numberOfColumns / 2+1-left] = new HeatSource((numberOfRows / 2+1), numberOfColumns / 2+1-left, initialTemperature);
                    heatSourceCells.add((HeatSource) tabCell[(numberOfRows / 2) ][numberOfColumns / 2-left]);
                    left++;
                }
                else
                {
                    tabCell[numberOfRows / 2 ][numberOfColumns/ 2 +right] = new HeatSource((numberOfRows / 2), numberOfColumns / 2+right, initialTemperature);
                    tabWithOutsideCell[(numberOfRows / 2+1) ][numberOfColumns / 2+1+right] = new HeatSource((numberOfRows / 2+1), numberOfColumns / 2+1+right, initialTemperature);
                    heatSourceCells.add((HeatSource) tabCell[(numberOfRows / 2) ][numberOfColumns / 2+right]);
                    right++;
                }
            }
            else if(cpt==3)
            {
               // if(tabWithOutsideCell[(numberOfRows/2+1)+down][numberOfColumns/2+1] instanceof OutsideCell)
                if(numberOfRows/2+down>=numberOfRows )
                {
                    tabCell[(numberOfRows/ 2) - up][numberOfColumns/ 2] = new HeatSource((numberOfRows / 2)-up, numberOfColumns / 2, initialTemperature);
                    tabWithOutsideCell[(numberOfRows / 2+1) -up][numberOfColumns / 2+1] = new HeatSource((numberOfRows / 2+1)-up, numberOfColumns / 2+1, initialTemperature);
                    heatSourceCells.add((HeatSource) tabCell[(numberOfRows / 2) -up][numberOfColumns / 2]);
                    up++;

                }
                else
                {
                    tabCell[(numberOfRows / 2) + down][numberOfColumns / 2] = new HeatSource((numberOfRows / 2)+ down, numberOfColumns / 2, initialTemperature);
                    tabWithOutsideCell[(numberOfRows / 2+1) + down][numberOfColumns / 2+1] = new HeatSource((numberOfRows / 2+1)+ down, numberOfColumns / 2+1, initialTemperature);
                    heatSourceCells.add((HeatSource) tabCell[(numberOfRows / 2) + down][numberOfColumns / 2]);
                    down++;
                }
            }
            else if(cpt==0)
            {
                //if(tabWithOutsideCell[(numberOfRows/2+1)][numberOfColumns/2+1-left] instanceof OutsideCell)
                if(numberOfColumns/2-left<0)
                {
                    tabCell[numberOfRows / 2 ][numberOfColumns/ 2 +right] = new HeatSource((numberOfRows / 2), numberOfColumns / 2+right, initialTemperature);
                    tabWithOutsideCell[(numberOfRows / 2+1) ][numberOfColumns / 2+1+right] = new HeatSource((numberOfRows / 2+1), numberOfColumns / 2+1+right, initialTemperature);
                    heatSourceCells.add((HeatSource) tabCell[(numberOfRows / 2) ][numberOfColumns / 2+right]);
                    right++;
                }
                else
                {

                    tabCell[numberOfRows / 2 ][numberOfColumns/ 2 -left] = new HeatSource((numberOfRows / 2), numberOfColumns / 2-left, initialTemperature);
                    tabWithOutsideCell[(numberOfRows / 2+1) ][numberOfColumns / 2+1-left] = new HeatSource((numberOfRows / 2+1), numberOfColumns / 2+1-left, initialTemperature);
                    heatSourceCells.add((HeatSource) tabCell[(numberOfRows / 2) ][numberOfColumns / 2-left]);
                    left++;
                }
            }

            cpt++;
            cpt= cpt%4;
        }

    }

    private void placeConfigX()
    {
        int upRight=1;
        int upLeft=1;
        int downRight=1;
        int downLeft=1;
        int cpt=0;
        for(int i=0; i<numberOfHeatSource;i++)
        {
            if(numberOfRows%2==0)
            {
                if(i==0)
                {
                    tabCell[numberOfRows/2][numberOfColumns/2]= new HeatSource(numberOfRows/2,numberOfColumns/2, initialTemperature);
                    tabWithOutsideCell[numberOfRows/2+1][numberOfColumns/2+1]=tabCell[numberOfRows/2][numberOfColumns/2];
                    heatSourceCells.add((HeatSource) tabCell[numberOfRows/2][numberOfColumns/2]);
                } else if (i==1) {
                    tabCell[numberOfRows/2+1][numberOfColumns/2]= new HeatSource(numberOfRows/2+1,numberOfColumns/2, initialTemperature);
                    tabWithOutsideCell[numberOfRows/2+1+1][numberOfColumns/2+1]=tabCell[numberOfRows/2+1][numberOfColumns/2];
                    heatSourceCells.add((HeatSource) tabCell[numberOfRows/2+1][numberOfColumns/2]);
                }
                else if (i==2){
                    tabCell[numberOfRows/2+1][numberOfColumns/2+1]= new HeatSource(numberOfRows/2+1,numberOfColumns/2+1, initialTemperature);
                    tabWithOutsideCell[numberOfRows/2+1+1][numberOfColumns/2+1+1]=tabCell[numberOfRows/2+1][numberOfColumns/2+1];
                    heatSourceCells.add((HeatSource) tabCell[numberOfRows/2+1][numberOfColumns/2+1]);

                } else if (i==3) {
                    tabCell[numberOfRows/2][numberOfColumns/2+1]= new HeatSource(numberOfRows/2,numberOfColumns/2+1, initialTemperature);
                    tabWithOutsideCell[numberOfRows/2+1][numberOfColumns/2+1+1]=tabCell[numberOfRows/2][numberOfColumns/2+1];
                    heatSourceCells.add((HeatSource) tabCell[numberOfRows/2][numberOfColumns/2+1]);
                }
                else if(cpt==0)
                {
                    tabCell[numberOfRows/2-upLeft][numberOfColumns/2-upLeft]= new HeatSource(numberOfRows/2-upLeft,numberOfColumns/2-upLeft, initialTemperature);
                    tabWithOutsideCell[numberOfRows/2+1-upLeft][numberOfColumns/2+1-upLeft]=tabCell[numberOfRows/2-upLeft][numberOfColumns/2-upLeft];
                    heatSourceCells.add((HeatSource) tabCell[numberOfRows/2-upLeft][numberOfColumns/2-upLeft]);
                    upLeft++;
                }
                else if(cpt==1)
                {
                    tabCell[numberOfRows/2-upRight][numberOfColumns/2+upRight]= new HeatSource(numberOfRows/2-upRight,numberOfColumns/2+upRight, initialTemperature);
                    tabWithOutsideCell[numberOfRows/2+1-upRight][numberOfColumns/2+1+upRight]=tabCell[numberOfRows/2-upRight][numberOfColumns/2+upRight];
                    heatSourceCells.add((HeatSource) tabCell[numberOfRows/2-upRight][numberOfColumns/2+upRight]);
                    upRight++;
                }
                else if(cpt==2)
                {
                    tabCell[numberOfRows/2+downRight][numberOfColumns/2+downRight]= new HeatSource(numberOfRows/2+downRight,numberOfColumns/2+downRight, initialTemperature);
                    tabWithOutsideCell[numberOfRows/2+1+downRight][numberOfColumns/2+1+downRight]=tabCell[numberOfRows/2+downRight][numberOfColumns/2+downRight];
                    heatSourceCells.add((HeatSource) tabCell[numberOfRows/2+downRight][numberOfColumns/2+downRight]);
                    downRight++;
                }
                else if(cpt==3)
                {
                    tabCell[numberOfRows/2+downLeft][numberOfColumns/2-downLeft]= new HeatSource(numberOfRows/2+downLeft,numberOfColumns/2-downLeft, initialTemperature);
                    tabWithOutsideCell[numberOfRows/2+1+downLeft][numberOfColumns/2+1-downLeft]=tabCell[numberOfRows/2+downLeft][numberOfColumns/2-downLeft];
                    heatSourceCells.add((HeatSource) tabCell[numberOfRows/2+downLeft][numberOfColumns/2-downLeft]);
                    downLeft++;
                }


                    cpt++;
                    cpt=cpt%4;

            }
            else
            {
                if(i==0)
                {
                    tabCell[numberOfRows/2][numberOfColumns/2]= new HeatSource(numberOfRows/2,numberOfColumns/2, initialTemperature);
                    tabWithOutsideCell[(numberOfRows/2)+1][(numberOfColumns/2)+1]=tabCell[numberOfRows/2][numberOfColumns/2];
                    heatSourceCells.add((HeatSource) tabCell[numberOfRows/2][numberOfColumns/2]);
                }
                else if(cpt==0)
                {
                    tabCell[numberOfRows/2-upLeft][numberOfColumns/2-upLeft]= new HeatSource(numberOfRows/2-upLeft,numberOfColumns/2-upLeft, initialTemperature);
                    tabWithOutsideCell[numberOfRows/2+1-upLeft][numberOfColumns/2+1-upLeft]=tabCell[numberOfRows/2-upLeft][numberOfColumns/2-upLeft];
                    heatSourceCells.add((HeatSource) tabCell[numberOfRows/2-upLeft][numberOfColumns/2-upLeft]);
                    upLeft++;
                }
                else if(cpt==1)
                {
                    tabCell[numberOfRows/2-upRight][numberOfColumns/2+upRight]= new HeatSource(numberOfRows/2-upRight,numberOfColumns/2+upRight, initialTemperature);
                    tabWithOutsideCell[numberOfRows/2+1-upRight][numberOfColumns/2+1+upRight]=tabCell[numberOfRows/2-upRight][numberOfColumns/2+upRight];
                    heatSourceCells.add((HeatSource) tabCell[numberOfRows/2-upRight][numberOfColumns/2+upRight]);
                    upRight++;
                }
                else if(cpt==2)
                {
                    tabCell[numberOfRows/2+downRight][numberOfColumns/2+downRight]= new HeatSource(numberOfRows/2+downRight,numberOfColumns/2+downRight, initialTemperature);
                    tabWithOutsideCell[numberOfRows/2+1+downRight][numberOfColumns/2+1+downRight]=tabCell[numberOfRows/2+downRight][numberOfColumns/2+downRight];
                    heatSourceCells.add((HeatSource) tabCell[numberOfRows/2+downRight][numberOfColumns/2+downRight]);
                    downRight++;
                }
                else if(cpt==3)
                {
                    tabCell[numberOfRows/2+downLeft][numberOfColumns/2-downLeft]= new HeatSource(numberOfRows/2+downLeft,numberOfColumns/2-downLeft, initialTemperature);
                    tabWithOutsideCell[numberOfRows/2+1+downLeft][numberOfColumns/2+1-downLeft]=tabCell[numberOfRows/2+downLeft][numberOfColumns/2-downLeft];
                    heatSourceCells.add((HeatSource) tabCell[numberOfRows/2+downLeft][numberOfColumns/2-downLeft]);
                    downLeft++;
                }


                cpt++;
                cpt=cpt%4;
            }


        }
    }

    public void initializeNeiboringCell()
    {
        for (Cell cell: cells) {
            cell.neighborsCells.clear();
            for(int i =-1; i<2;i++)
            {
                for(int j =-1; j<2;j++)
                {
                    if(i==0 && j==0)
                    {

                    }
                    //else if(tabWithOutsideCell[cell.getX()+1 +i][cell.getY()+1 +j] instanceof OutsideCell)
                    else if(cell.getX()+i<0||cell.getY()+j<0||cell.getX()+i>=numberOfRows||cell.getY()+j>=numberOfColumns)
                    {
                        cell.addNeighborCell(new OutsideCell(cell.getX()+i,cell.getY()+j,outsideTemperature));
                    }
                    else
                    {
                        cell.addNeighborCell(tabCell[cell.getX() +i][cell.getY() +j]);
                    }

                }

            }

        }
    }
    public void updateCellsTemperature()
    {
        for(Cell cell : cells) // calcule de la temperature pour chaque cellule
        {
           if(cell instanceof NormalCell)
           {
               int sumTemperature=0;
               int deadCellCounter=0;
             for(Cell neighbor: cell.neighborsCells)
             {
                 if(neighbor instanceof DeadCell)
                 {
                     deadCellCounter++;
                 } else if (neighbor instanceof  OutsideCell)
                 {
                     sumTemperature+= OutsideCell.outsideTemperature;
                 } else if (neighbor instanceof HeatSource)
                 {
                     if(neighbor.isAlive)
                     {
                         sumTemperature += cell.temperature;
                     }
                     else
                     {
                         deadCellCounter++;
                     }

                 } else
                 {
                    sumTemperature += cell.temperature;
                 }
             }

             cell.setTemperature((sumTemperature)/(cell.maxneighborsCells-deadCellCounter));
           }


            // todo: ajouter la temperature extérieur
           // cell.updateCellTemperature();

        }
    }
    public void updatetabCell( Cell[][] newtabcell)
    {
        tabCell= newtabcell;
    }

    public int getAverageTemperature() {
        for( Cell cell : cells)
        {
            averageTemperature += cell.getTemperature();
        }
        averageTemperature= averageTemperature/cells.size();
        return averageTemperature;
    }

    public int getNumberOfRows(){return numberOfRows;}
    public int getNumberOfColumns(){return numberOfColumns;}
}
