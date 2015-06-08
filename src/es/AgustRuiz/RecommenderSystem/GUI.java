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
 *
 * @author Agustin Ruiz Linares <arl00029@red.ujaen.es>
 */
public class GUI {

    /**
     * Main Menu
     */
    public static void MainMenu() {
        List<Integer> validOptions = new ArrayList();
        validOptions.add(1);
        validOptions.add(2);
        validOptions.add(3);
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
            System.out.println("[3] Check database connection.");
            System.out.println("[0] Exit.");
            System.out.println("");
            System.out.print("Option: ");
            selectedOption = GUI.getIntegerFromKeyboard();
            switch (selectedOption) {
                case 1:
                    GUI.MainMenu_Practice01();
                    break;
                case 2:
                    //GUI.MainMenu_Practice02();
                    GUI.MainMenu_Practice02();
                    break;
                case 3:
                    GUI.DatabaseConnection();
                    break;
            }
        }
    }

    /**
     * Reads an integer from keyboard before pressing enter key
     * @return Number introduced by keyboard, or Integer.MIN_VALUE if not valid
     */
    private static int getIntegerFromKeyboard() {
        Scanner keyboard = new Scanner(System.in);
        try {
            return keyboard.nextInt();
        } catch (Exception e) {
            return Integer.MIN_VALUE;
        }
    }

    /**
     * Show database connection data
     */
    private static void DatabaseConnection() {
        System.out.println("");
        System.out.println("+--------------------------+");
        System.out.println("| DATABASE CONNECTION DATA |");
        System.out.println("+--------------------------+");
        System.out.println("");
        System.out.println("Database\t: " + DbConnection.getDb());
        System.out.println("Username\t: " + DbConnection.getUsername());
        System.out.println("Password\t: " + DbConnection.getPassword());
        System.out.println("Url connection\t: " + DbConnection.getUrlConnection());
        System.out.println("");
        GUI.pauseProg();
    }

    /**
     * Pause program until press "enter" key
     */
    public static void pauseProg() {
        System.out.print("Press enter to continue... ");
        Scanner keyboard = new Scanner(System.in);
        keyboard.nextLine();
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
            System.out.println("[1] Select UserId. (Current: " + Recommender.getActiveUserId() + ").");
            System.out.println("[2] Select K value. (Current: " + Recommender.getKvalue() + ").");
            System.out.println("[3] Run recommender for current user.");
            System.out.println("[0] Back.");
            System.out.println("");
            System.out.print("Option: ");
            selectedOption = GUI.getIntegerFromKeyboard();
            switch (selectedOption) {
                case 1:
                    GUI.changeActiveUser_Practice01();
                    break;
                case 2:
                    GUI.changeKValue_Practice01();
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
    private final static void changeActiveUser_Practice01() {
        System.out.println("");
        System.out.println("+----------------+");
        System.out.println("| CHANGE USER ID |");
        System.out.println("+----------------+");
        System.out.println("");
        System.out.print("Enter active user id: ");

        int userSelected = GUI.getIntegerFromKeyboard();
        if (UserDAO.existIduser(userSelected)) {
            Recommender.setActiveUserId(userSelected);
        }
    }

    /**
     * Change K value (for KNN)
     */
    private final static void changeKValue_Practice01() {
        System.out.println("");
        System.out.println("+----------------------+");
        System.out.println("| CHANGE K VALUE (KNN) |");
        System.out.println("+----------------------+");
        System.out.println("");
        System.out.print("Enter K value: ");

        int kvalue = GUI.getIntegerFromKeyboard();
        if (kvalue > 0 && kvalue <= UserDAO.count()) {
            Recommender.setKvalue(kvalue);
        }
    }

    /**
     * Practice 1 - Main menu
     */
    private static void MainMenu_Practice02() {
        int selectedOption = -1;
        while (selectedOption != 0) {
            System.out.println("");
            System.out.println("+------------------------+");
            System.out.println("| PRACTICE 2: EVALUATION |");
            System.out.println("+------------------------+");
            System.out.println("Please, select an option:");
            System.out.println("");
            System.out.println("[1] Select UserId. Current: " + Recommender_Evaluator.getActiveUserId() + ".");
            System.out.println("[2] Add K value. Current: " + Recommender_Evaluator.getKValuesInString() + ".");
            System.out.println("[3] Reset K values.");
            System.out.println("[4] Run evaluator.");
            System.out.println("[0] Back.");
            System.out.println("");
            System.out.print("Option: ");
            selectedOption = GUI.getIntegerFromKeyboard();
            switch (selectedOption) {
                case 1:
                    GUI.changeActiveUser_Practice02();
                    break;
                case 2:
                    GUI.addKValue_Practice02();
                    break;
                case 3:
                    Recommender_Evaluator.resetKValue();
                    break;
                case 4:
                    System.out.println("");
                    System.out.println("+-----------+");
                    System.out.println("| EVALUATOR |");
                    System.out.println("+-----------+");
                    Recommender_Evaluator.Run();
                    GUI.pauseProg();
                    break;
            }
        }
    }

    /**
     * Change K value (for KNN)
     */
    private final static void addKValue_Practice02() {
        int kvalue;
        do{
        System.out.print("Enter K value (0 to exit): ");
        kvalue = GUI.getIntegerFromKeyboard();
        if (kvalue > 0 && kvalue <= UserDAO.count() && !Recommender_Evaluator.hasKValue(kvalue)) {
            Recommender_Evaluator.addKValue(kvalue);
        }
        }while(kvalue > 0);
    }

    /**
     * Change active user
     */
    private final static void changeActiveUser_Practice02() {
        System.out.println("");
        System.out.println("+----------------+");
        System.out.println("| CHANGE USER ID |");
        System.out.println("+----------------+");
        System.out.println("");
        System.out.print("Enter active user id: ");

        int userSelected = GUI.getIntegerFromKeyboard();
        if (UserDAO.existIduser(userSelected)) {
            Recommender_Evaluator.setActiveUserId(userSelected);
        }
    }
}
