/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.AgustRuiz.RecommenderSystem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author Agustin Ruiz Linares <arl00029@red.ujaen.es>
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here

        System.out.println("Hola desde Main");

        //Item item = new Item(0, "apetecán");
        
        //ItemDAO itemDAO = new ItemDAO();
        
        
        System.out.println("Antes getItem");
        
        Item item23 = ItemDAO.get(23);
        
        System.err.println(item23.toString());
        
        System.out.println("Antes getList");
        
        HashMap itemsMap = ItemDAO.getList();
        
        System.out.println(itemsMap.get(23));
        
    }

}
