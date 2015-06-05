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
 *
 * @author Agustin Ruiz Linares <arl00029@red.ujaen.es>
 */
public class GUI {

    private static Scanner keyboard = new Scanner(System.in);

    public static void MainMenu() {

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
            System.out.println("");
            System.out.println("Please, select an option:");
            System.out.println("");
            System.out.println("[1] Select UserId. (Current: " + Main.ACTIVE_USER + ").");
            System.out.println("[2] Select K value. (Current: " + Main.K_VALUE + ").");
            System.out.println("[3] Run recommender for current user.");
            System.out.println("[0] Quit.");
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

    public static void pauseProg() {
        System.out.print("Press enter to continue... ");
        Scanner keyboard = new Scanner(System.in);
        keyboard.nextLine();
    }
}
