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
public class Pair_UserItem implements Comparable<Pair_UserItem> {

    Integer iduser, iditem;

    /**
     * Constructor
     *
     * @param iduser
     * @param iditem
     */
    public Pair_UserItem(int iduser, int iditem) {
        this.iduser = iduser;
        this.iditem = iditem;
    }

    /**
     * Get iduser value
     *
     * @return iduser value
     */
    public int getIduser() {
        return iduser;
    }

    /**
     * Set iduser value
     *
     * @param iduser
     */
    public void setIduser(int iduser) {
        this.iduser = iduser;
    }

    /**
     * Get iditem value
     *
     * @return iditem value
     */
    public int getIditem() {
        return iditem;
    }

    /**
     * Set iditem value
     *
     * @param iditem
     */
    public void setIditem(int iditem) {
        this.iditem = iditem;
    }

    /**
     * Compare two pairs
     *
     * @param o Object to compare
     * @return 0 if equal or -1 otherwise
     */
    @Override
    public int compareTo(Pair_UserItem o) {
        //return (this.iduser.compareTo(o.iduser) == 0 && this.iditem.compareTo(o.iditem) == 0 ? 0 : -1);
        if (this.iduser.compareTo(o.iduser) == 0 && this.iditem.compareTo(o.iditem) == 0) {
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
        return Integer.parseInt(new Integer(this.iduser*17).toString() + new Integer(this.iditem*23).toString());
    }

    /**
     * Equals method
     * @param o Objetc to compare
     * @return true if equals of false if not
     */
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Pair_UserItem)) {
            return false;
        }
        Pair_UserItem pairO = (Pair_UserItem) o;
        if (this.iduser.equals(pairO.iduser) && this.iditem.equals(pairO.iditem)){
            return true;
        }
        return false;
    }
    
    @Override
    public String toString(){
        return this.iduser + " " + this.iditem;
    }
}
