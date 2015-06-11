/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.AgustRuiz.RecommenderSystem;

import static java.lang.Math.sqrt;
import java.util.AbstractMap.SimpleEntry;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

/**
 *
 * @author Agustin Ruiz Linares <arl00029@red.ujaen.es>
 */
public class NeighborsHandler {

    /// Items handler
    ItemHandler items;

    /// Users handler
    UserHandler users;

    /// Ratings handler
    RatingHandler ratings;

    /// Similarity matrix: HashMap<user1, HashMap<user2, similarity>>
    HashMap<Integer, HashMap<Integer, Double>> similarityMatrix;

    /// Neighbors matrix: HashMap<activeIduser HashMap<similarity, user2>>
    HashMap<Integer, TreeMap<Double, Integer>> neighborsMatrix;

    /**
     * Constructor
     *
     * @param items Item handler
     * @param users Users handler
     * @param ratings Ratings handler
     */
    public NeighborsHandler(ItemHandler items, UserHandler users, RatingHandler ratings) {
        this.similarityMatrix = new HashMap();
        this.neighborsMatrix = new HashMap();
        this.items = items;
        this.users = users;
        this.ratings = ratings;
    }

    private void calculateSimilarity(int user1Id, int user2Id) {
        int iditem;
        Double user1Calc, user2Calc, dividend = 0.0, divisor1 = 0.0, divisor2 = 0.0;

        for (Item item : items.getSet()) {
            iditem = item.getIditem();

            if (ratings.get(user1Id, iditem) != null && ratings.get(user2Id, iditem) != null) {
                user1Calc = ratings.get(user1Id, iditem) - ratings.getAvgRatingsUser(user1Id);
                user2Calc = ratings.get(user2Id, iditem) - ratings.getAvgRatingsUser(user2Id);
                dividend += user1Calc * user2Calc;
                divisor1 += user1Calc * user1Calc;
                divisor2 += user2Calc * user2Calc;
            }
        }

        Double similarity = dividend / sqrt(divisor1 * divisor2);
        this.setValue(user1Id, user2Id, similarity);

    }

    private void setValue(int user1, int user2, Double similarity) {
        if (user1 != user2) {
            int u, v;
            if (user1 < user2) {
                u = user1;
                v = user2;
            } else {
                u = user2;
                v = user1;
            }

            if (!similarityMatrix.containsKey(u)) {
                similarityMatrix.put(u, new HashMap());
            }

            similarityMatrix.get(u).put(v, similarity);
        }
    }

    public Double getSimilarity(int user1, int user2) {
        if (user1 == user2) {
            return 1.0;
        } else {
            int u, v;
            if (user1 < user2) {
                u = user1;
                v = user2;
            } else {
                u = user2;
                v = user1;
            }

            if (!similarityMatrix.containsKey(u) || !similarityMatrix.get(u).containsKey(v)) {
                this.calculateSimilarity(u, v);
            }

            return similarityMatrix.get(u).get(v);
        }
    }

    public Map<Double, Integer> calculateKNN(int idActiveUser, int kSize) {
        if (!this.neighborsMatrix.containsKey(idActiveUser)) {
            this.neighborsMatrix.put(idActiveUser, new TreeMap(Collections.reverseOrder()));

            Integer idCurrentUser;
            Double currentSimilarity;
            for (User u : users.getCollection()) {
                idCurrentUser = u.getIduser();
                currentSimilarity = this.getSimilarity(idActiveUser, idCurrentUser);
                if (!currentSimilarity.isNaN()) {
                    this.neighborsMatrix.get(idActiveUser).put(currentSimilarity, idCurrentUser);
                }
            }

        }

        TreeMap<Double, Integer> allNeighbors = (TreeMap<Double, Integer>)this.neighborsMatrix.get(idActiveUser).clone();
        TreeMap<Double, Integer> topKNeighbors = new TreeMap(Collections.reverseOrder());

        Entry<Double, Integer> entry;
        for (int i = 0; i < kSize && !allNeighbors.isEmpty(); ++i) {
            entry = new SimpleEntry(allNeighbors.firstEntry());
            allNeighbors.remove(entry.getKey());
            topKNeighbors.put(entry.getKey(), entry.getValue());
        }
        return topKNeighbors;

    }

}
