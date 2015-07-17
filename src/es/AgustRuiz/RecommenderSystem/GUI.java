/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.AgustRuiz.RecommenderSystem;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import static es.AgustRuiz.RecommenderSystem.Main.usersHandler;

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
            System.out.println("[3] About.");
            System.out.println("[0] Exit.");
            System.out.println("");
            System.out.print("Option: ");
            selectedOption = GUI.getIntegerFromKeyboard();
            switch (selectedOption) {
                case 1:
                    GUI.MainMenu_Practice01();
                    break;
                case 2:
                    GUI.MainMenu_Practice02();
                    break;
                case 3:
                    GUI.About();
                    break;
            }
        }
    }

    /**
     * Reads an integer from keyboard before pressing enter key
     *
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
            System.out.println("[1] Select UserId. (Current: " + Practice01.getActiveIduser() + ").");
            System.out.println("[2] Select K value. (Current: " + Practice01.getkSize() + ").");
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
                    Practice01.Run();
                    System.out.println("");
                    GUI.pauseProg();
                    break;
            }
        }
    }

    /**
     * Change active user
     */
    private static void changeActiveUser_Practice01() {
        System.out.println("");
        System.out.println("+----------------+");
        System.out.println("| CHANGE USER ID |");
        System.out.println("+----------------+");
        System.out.println("");
        System.out.print("Enter active user id: ");

        int userSelected = GUI.getIntegerFromKeyboard();
        if (usersHandler.existIduser(userSelected)) {
            Practice01.setActiveIduser(userSelected);
        }
    }

    /**
     * Change K value (for KNN)
     */
    private static void changeKValue_Practice01() {
        System.out.println("");
        System.out.println("+----------------------+");
        System.out.println("| CHANGE K VALUE (KNN) |");
        System.out.println("+----------------------+");
        System.out.println("");
        System.out.print("Enter K value: ");

        int kvalue = GUI.getIntegerFromKeyboard();
        if (kvalue > 0 && kvalue <= usersHandler.count()) {
            Practice01.setkSize(kvalue);
        }
    }

    /**
     * Practice 2 - Main menu
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
            System.out.println("[1] Add K value. Current: " + Practice02.getKSizes() + ".");
            System.out.println("[2] Reset K values.");
            System.out.println("[3] Run evaluator.");
            System.out.println("[0] Back.");
            System.out.println("");
            System.out.print("Option: ");
            selectedOption = GUI.getIntegerFromKeyboard();
            switch (selectedOption) {
                case 1:
                    GUI.addKValue_Practice02();
                    break;
                case 2:
                    Practice02.clearKSizes();
                    break;
                case 3:
                    System.out.println("");
                    System.out.println("+-----------+");
                    System.out.println("| EVALUATOR |");
                    System.out.println("+-----------+");
                    Practice02.Run();
                    GUI.pauseProg();
                    break;
            }
        }
    }

    /**
     * Change K value (for KNN)
     */
    private static void addKValue_Practice02() {
        int kSize;
        do {
            System.out.print("Enter new K value (0 to exit): ");
            kSize = GUI.getIntegerFromKeyboard();
            if (kSize > 0 && kSize <= usersHandler.count() && !Practice02.hasKSize(kSize)) {
                Practice02.addKSize(kSize);
            }
        } while (kSize > 0);
    }

    /**
     * Practice 2 - Main menu
     */
    private static void About() {
        System.out.println("+-------+");
        System.out.println("| ABOUT |");
        System.out.println("+-------+");
        System.out.println("");
        System.out.println("Developed by Agustín Ruiz Linares <arl00029@red.ujaen.es>.");
        System.out.println("Subject: Business Ingeligence and Web - Module III: Recommender Systems.");
        System.out.println("Year: 2015.");
        System.out.println("University of Jaén.");
        System.out.println("");
        pauseProg();
    }
}
