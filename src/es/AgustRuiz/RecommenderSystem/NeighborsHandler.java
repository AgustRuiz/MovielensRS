/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.AgustRuiz.RecommenderSystem;

import static java.lang.Math.sqrt;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.Vector;

/**
 *
 * @author Agustin Ruiz Linares <arl00029@red.ujaen.es>
 */
public class NeighborsHandler {

    /// Items handler
    ItemHandler items;

    /// Users handler
    UserHandler users;

    private Vector<Integer> idUsers;

    /// Ratings handler
    GenericRatingHandler ratings;

    /// Similarity matrix: HashMap<user1, HashMap<user2, similarity>>
    HashMap<Pair_UserUser, Double> similarityMatrix;

    /// Neighbors matrix: HashMap<activeIduser HashMap<similarity, user2>>
    HashMap<Integer, TreeMap<Double, Integer>> neighborsMatrix;

    /**
     * Constructor
     *
     * @param items Item handler
     * @param users Users handler
     * @param ratings Ratings handler
     */
    public NeighborsHandler(ItemHandler items, UserHandler users, GenericRatingHandler ratings) {
        this.similarityMatrix = new HashMap();
        this.neighborsMatrix = new HashMap();
        this.items = items;
        this.users = users;
        this.ratings = ratings;

        this.idUsers = new Vector();
        for (User u : users.getCollection()) {
            this.idUsers.add(u.getIduser());
        }
    }

    /**
     * Calculate similarity between users
     *
     * @param user1Id User Id 1
     * @param user2Id User Id 2
     */
    private void CalculateSimilarity(int user1Id, int user2Id) {
        if (user1Id == user2Id) {
            this.setValue(user1Id, user2Id, 1.0);
        } else {
            int iditem;
            Double user1Calc, user2Calc, dividend = 0.0, divisor1 = 0.0, divisor2 = 0.0;

            for (Item item : items.getSet()) { // max 2 ms
                iditem = item.getIditem();

                if (ratings.Get(user1Id, iditem) != null && ratings.Get(user2Id, iditem) != null) {
                    //user1Calc = ratings.Get(user1Id, iditem) - ratings.GetAvgRatingsUser(user1Id);
                    //user2Calc = ratings.Get(user2Id, iditem) - ratings.GetAvgRatingsUser(user2Id);
                    user1Calc = ratings.Get(user1Id, iditem) - ratings.GetAvgCorating(user1Id, user2Id);
                    user2Calc = ratings.Get(user2Id, iditem) - ratings.GetAvgCorating(user2Id, user1Id);
                    dividend += user1Calc * user2Calc;
                    divisor1 += user1Calc * user1Calc;
                    divisor2 += user2Calc * user2Calc;
                }

            }

            Double similarity = dividend / sqrt(divisor1 * divisor2);
            if (similarity.isNaN() || similarity < -1) {
                similarity = -1.0;
            } else if (similarity > 1) {
                similarity = 1.0;
            }

            this.setValue(user1Id, user2Id, similarity);
        }
    }

    private void setValue(int user1, int user2, Double similarity) {
        similarityMatrix.put(new Pair_UserUser(user1, user2), similarity);
    }

    public Double GetSimilarity(int user1, int user2) {
        Pair_UserUser myPair = new Pair_UserUser(user1, user2);
        if (!similarityMatrix.containsKey(myPair)) {
            this.CalculateSimilarity(user1, user2);
        }
        return similarityMatrix.get(myPair);
    }

    public TreeMap<Double, Integer> calculateKNN(int idActiveUser, int kSize, boolean filePrint) {

        // TIME PROBLEMS HERE
        if (!this.neighborsMatrix.containsKey(idActiveUser)) {
            this.neighborsMatrix.put(idActiveUser, new TreeMap(Collections.reverseOrder()));
            for (int idCurrentUser : this.idUsers) {
                this.neighborsMatrix.get(idActiveUser).put(this.GetSimilarity(idActiveUser, idCurrentUser), idCurrentUser);
            }
        }
        // TIME PROBLEMS HERE

        //TreeMap<Double, Integer> allNeighbors = (TreeMap<Double, Integer>)this.neighborsMatrix.Get(idActiveUser);
        TreeMap<Double, Integer> topKNeighbors = new TreeMap(Collections.reverseOrder());

        int counter = 0;
        for (Entry<Double, Integer> entry : this.neighborsMatrix.get(idActiveUser).entrySet()) {
            topKNeighbors.put(entry.getKey(), entry.getValue());
            if (++counter >= kSize) {
                break;
            }
        }

        if (filePrint) {
            FileWriter fileWriter = new FileWriter("Neighborhood_User" + idActiveUser + ".txt");
            fileWriter.Write("NEIGHBORHOOD");
            fileWriter.Write("Active user: " + idActiveUser + " | k = " + kSize + " | Time: " + Calendar.getInstance().getTime().toString());
            fileWriter.Write("");
            fileWriter.Write("[Ord]\tUSER\t\tSIMILARITY");
            int i = 0;
            for (Entry<Double, Integer> entry : this.neighborsMatrix.get(idActiveUser).entrySet()) {
                fileWriter.Write("[" + String.format("%3d", ++i) + "]\tUser '" + entry.getValue() + "':\t\t" + entry.getKey());
            }
            fileWriter.Close();
        }

        return topKNeighbors;
    }

    public void calculateSimilarityMatrix() {
        double d;
        for (int i = 0; i < users.count(); ++i) {
            for (int j = i; j < users.count(); ++j) {
                d = this.GetSimilarity(i, j);
            }
        }
    }

}
