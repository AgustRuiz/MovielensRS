/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.AgustRuiz.RecommenderSystem;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Graphic User Interface
 * @author Agustin Ruiz Linares <arl00029@red.ujaen.es>
 */
public class GUI {
    /// Scanner
    private static Scanner keyboard = new Scanner(System.in);
    
    /**
     * Main Menu
     */
    public static void MainMenu(){
        List<Integer> validOptions = new ArrayList();
        validOptions.add(1);
        validOptions.add(2);
        validOptions.add(0);
        int selectedOption = -1;
        while (selectedOption != 0) {
            System.out.println("");
            System.out.println("+-----------+");
            System.out.println("| MAIN MENU |");
            System.out.println("+-----------+");
            System.out.println("Please, select an option:");
            System.out.println("");
            System.out.println("[1] Practice 1 - Collaborative Recommendation System.");
            System.out.println("[2] Practice 2 - Evaluation.");
            System.out.println("[0] Exit.");
            System.out.println("");
            System.out.print("Option: ");
            selectedOption = keyboard.nextInt();
            switch (selectedOption) {
                case 1:
                    GUI.MainMenu_Practice01();
                    break;
                case 2:
                    //GUI.MainMenu_Practice02();
                    System.err.println("Not implemented yet");
                    GUI.pauseProg();
                    break;
            }
        }
    }

    /**
     * Practice 1 - Main menu 
     */
    private static void MainMenu_Practice01() {
        List<Integer> validOptions = new ArrayList();
        validOptions.add(1);
        validOptions.add(2);
        validOptions.add(0);
        int selectedOption = -1;
        while (selectedOption != 0) {
            System.out.println("");
            System.out.println("+-------------------------------------------------+");
            System.out.println("| PRACTICE 1: COLLABORATIVE RECOMMENDATION SYSTEM |");
            System.out.println("+-------------------------------------------------+");
            System.out.println("Please, select an option:");
            System.out.println("");
            System.out.println("[1] Select UserId. (Current: " + Main.ACTIVE_USER + ").");
            System.out.println("[2] Select K value. (Current: " + Main.K_VALUE + ").");
            System.out.println("[3] Run recommender for current user.");
            System.out.println("[0] Back.");
            System.out.println("");
            System.out.print("Option: ");
            selectedOption = keyboard.nextInt();
            switch (selectedOption) {
                case 1:
                    GUI.changeActiveUser();
                    break;
                case 2:
                    GUI.changeKValue();
                    break;
                case 3:
                    System.out.println("");
                    System.out.println("+-----------------+");
                    System.out.println("| RECOMMENDATIONS |");
                    System.out.println("+-----------------+");
                    System.out.println("");
                    Recommender.Run();
                    System.out.println("");
                    GUI.pauseProg();
                    break;
            }
        }
    }

    /**
     * Change active user
     */
    private final static void changeActiveUser() {
        System.out.println("");
        System.out.println("+----------------+");
        System.out.println("| CHANGE USER ID |");
        System.out.println("+----------------+");
        System.out.println("");
        System.out.print("Enter active user id: ");

        int userSelected = keyboard.nextInt();
        if (UserDAO.existIduser(userSelected)) {
            Main.ACTIVE_USER = userSelected;
        }
    }

    /**
     * Change K value (for KNN)
     */
    private final static void changeKValue() {
        System.out.println("");
        System.out.println("+----------------------+");
        System.out.println("| CHANGE K VALUE (KNN) |");
        System.out.println("+----------------------+");
        System.out.println("");
        System.out.print("Enter K value: ");

        int kvalue = keyboard.nextInt();
        if (kvalue > 0 && kvalue <= UserDAO.count()) {
            Main.K_VALUE = kvalue;
        }
    }

    /**
     * Pause program until press "enter" key
     */
    public static void pauseProg() {
        System.out.print("Press enter to continue... ");
        Scanner keyboard = new Scanner(System.in);
        keyboard.nextLine();
    }
}
