/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.AgustRuiz.RecommenderSystem;

import static java.lang.Math.sqrt;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
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
    HashMap<Integer, HashMap<Integer, Double>> neighborsMatrix;

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
                    user1Calc = ratings.Get(user1Id, iditem) - ratings.GetAvgCoratingUser(user1Id, user2Id);
                    user2Calc = ratings.Get(user2Id, iditem) - ratings.GetAvgCoratingUser(user2Id, user1Id);
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

    public TreeMap<Integer, Double> calculateKNN(int idActiveUser, int kSize, boolean filePrint) {

        // TIME PROBLEMS HERE
        if (!this.neighborsMatrix.containsKey(idActiveUser)) {
            this.neighborsMatrix.put(idActiveUser, new HashMap());
            for (int idCurrentUser : this.idUsers) {
                this.neighborsMatrix.get(idActiveUser).put(idCurrentUser, this.GetSimilarity(idActiveUser, idCurrentUser));
            }
            // Remove active user
            this.neighborsMatrix.get(idActiveUser).remove(idActiveUser);
        }
        // TIME PROBLEMS HERE

        // Ordenate TreeMap
        TreeMap<Integer, Double> topKNeighbors = new TreeMap();

        Map<Integer, Double> neighborsSorted = sortByValues(this.neighborsMatrix.get(idActiveUser));
        Set<Entry<Integer, Double> > neighborsSortedSet = neighborsSorted.entrySet();
        Iterator iterator = neighborsSortedSet.iterator();
        int counter = 0;
        while (iterator.hasNext() && counter < kSize) {
            Entry<Integer, Double> entry = (Entry<Integer, Double>)iterator.next();
            topKNeighbors.put(entry.getKey(), entry.getValue());
            ++counter;
        }

        if (filePrint) {
            FileWriter fileWriter = new FileWriter("Neighbors_User" + idActiveUser + ".txt");
            fileWriter.Write("NEIGHBORS");
            fileWriter.Write("Active user: " + idActiveUser + " | Time: " + Calendar.getInstance().getTime().toString());
            fileWriter.Write("");
            fileWriter.Write("[Ord]\tUSER\t\tSIMILARITY");
            int i = 0;
            for (Entry<Integer, Double> entry : this.neighborsMatrix.get(idActiveUser).entrySet()) {
                fileWriter.Write("[" + String.format("%3d", ++i) + "]\tUser '" + entry.getKey() + "':\t\t" + entry.getValue());
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

    private static HashMap sortByValues(HashMap map) {
        List list = new LinkedList(map.entrySet());
        // Defined Custom Comparator here
        Collections.sort(list, new Comparator() {
            public int compare(Object o1, Object o2) {
                return ((Comparable) ((Map.Entry) (o1)).getValue())
                        .compareTo(((Map.Entry) (o2)).getValue());
            }
        });

        // Here I am copying the sorted list in HashMap
        // using LinkedHashMap to preserve the insertion order
        HashMap sortedHashMap = new LinkedHashMap();
        //for (Iterator it = list.iterator(); it.hasNext();) {
        for (int i = list.size()-1 ; i >= 0 ; --i){
            Map.Entry entry = (Map.Entry)list.get(i);
            sortedHashMap.put(entry.getKey(), entry.getValue());
        }
        return sortedHashMap;
    }
}
