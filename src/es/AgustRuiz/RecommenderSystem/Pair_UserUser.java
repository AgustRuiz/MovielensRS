/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.AgustRuiz.RecommenderSystem;

/**
 *
 * @author Agustin Ruiz Linares <arl00029@red.ujaen.es>
 */
public class Pair_UserUser implements Comparable {

    Integer user1, user2;

    /**
     * Constructor
     *
     * @param user1
     * @param user2
     */
    public Pair_UserUser(Integer user1, Integer user2) {
        if (user1 < user2) {
            this.user1 = user1;
            this.user2 = user2;
        } else {
            this.user1 = user2;
            this.user2 = user1;
        }
    }

    /**
     * Compare two pairs
     *
     * @param o Object to compare
     * @return 0 if equal or -1 otherwise
     */
    @Override
    public int compareTo(Object o) {
        if (this.user1.compareTo(((Pair_UserUser)o).user1) == 0 && this.user2.compareTo(((Pair_UserUser)o).user2) == 0) {
            return 0;
        } else {
            return -1;
        }
    }

    /**
     * Custom hashCode
     *
     * @return Hashcode
     */
    @Override
    public int hashCode() {
        return this.user1.hashCode() + this.user2.hashCode();
    }

    /**
     * Equals method
     *
     * @param o Objetc to compare
     * @return true if equals of false if not
     */
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Pair_UserUser)) {
            return false;
        }
        Pair_UserUser pairO = (Pair_UserUser) o;
        if (this.user1.equals(pairO.user1) && this.user2.equals(pairO.user2)) {
            return true;
        }
        return false;
    }

}
