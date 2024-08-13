package com.example.helbthermo;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Classe centralisant les fonctionnalités permettant le parsing
 * du fichier .data contenant les requetes de reservation.
 * Les lignes du fichiers .data étant construites de la manière suivante  : John;Smith;jsmith@mail.com;A;5
 * Le parser agit également comme un conteneur de requetes de reservations, pouvant être itérées à la suite.
 */
public class OutsideTemperatureParser {
    private ArrayList<OutsideTemperature> temperatureList = new ArrayList<OutsideTemperature>();
    private int currentIndex = 0;
    private int maxIndex = 0;

    public OutsideTemperatureParser(String filename)
    {
        parse(filename);
        maxIndex= temperatureList.size();
    }
    //true si u ne nouvelle request est disponible
    public boolean hasNextRequest(){
        return (currentIndex < maxIndex);
    }


    //renvoie une nouvelle request si celle ci est disponible sinon renvoie une exception
    public OutsideTemperature getNextTemperature(){
        if (hasNextRequest()){
            return temperatureList.get(currentIndex++);
        } else {
            throw new IndexOutOfBoundsException("No more temperature available");
        }
    }


    //return true si une ligne est correcte
    private boolean isLineCorrect(String line){
        try {
            int temperature = Integer.parseInt(line);
            if(temperature>=0 && temperature <=40){
                System.out.println("Temperature is valid"+ temperature);
                return true;
            }
            else
            {
                System.out.println("Temperature is invalid"+temperature);
                return false;
            }
        } catch (NumberFormatException e) {

            System.out.println("Temperature is not an Integer");
            return false;
        }

    }

    /*
        Parse le fichier filename et pour chaque ligne du fichier,
        verifie si la ligne est correcte
        et si celle ci est correcte,
        ajoute dans la liste une ReservationRequest construite à partir de cette ligne.
    */
    private void parse(String filename) {
        try (Scanner scanner = new Scanner(new File(filename))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if(isLineCorrect(line)){
                    int degree = Integer.parseInt(line);
                    OutsideTemperature temperature = new OutsideTemperature(degree);
                    temperatureList.add(temperature);
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading the file: " + e.getMessage());
        }
    }
}
