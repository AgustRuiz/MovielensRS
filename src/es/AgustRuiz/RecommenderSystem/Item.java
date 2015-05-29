package es.AgustRuiz.RecommenderSystem;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;

/**
 * Items class
 * @author Agustin Ruiz Linares <arl00029@red.ujaen.es>
 */
public class Item {

    /*** VARIABLES ***/
    
    /// Item identificator
    private Integer iditem;

    /// Product name
    private String name;

    /// Num genres
    private int numGenres;

    /// Genres
    private List<Boolean> genres;

    // Genres Strings
    private List<String> genresStrings;

    /*** METHODS ***/
    
    /**
     * Initialize variables
     */
    private void InitializeVariables() {
        //System.out.println("Initializing variables...");

        this.genresStrings = new ArrayList<>();
        //this.genresStrings.add("unknown");
        this.genresStrings.add("Action");
        this.genresStrings.add("Adventure");
        this.genresStrings.add("Animation");
        this.genresStrings.add("Children's");
        this.genresStrings.add("Comedy");
        this.genresStrings.add("Crime");
        this.genresStrings.add("Documentary");
        this.genresStrings.add("Drama");
        this.genresStrings.add("Fantasy");
        this.genresStrings.add("Film-Noir");
        this.genresStrings.add("Horror");
        this.genresStrings.add("Musical");
        this.genresStrings.add("Mystery");
        this.genresStrings.add("Romance");
        this.genresStrings.add("Sci-Fi");
        this.genresStrings.add("Thriller");
        this.genresStrings.add("War");
        this.genresStrings.add("Western");

        this.numGenres = this.genresStrings.size();

        this.genres = new ArrayList();
        for (int i = 0; i < this.numGenres; ++i) {
            this.genres.add(false);
        }
    }
    
    /**
     * Default constructor
     */
    private Item(){}

    /**
     * Constructor
     * @param iditem Item id
     * @param name Item name
     */
    public Item(Integer iditem, String name) {
        InitializeVariables();
        setUnknownGenre();
        this.iditem = iditem;
        this.name = name;
    }

    /**
     * Set an item as unknown genre
     */
    public void setUnknownGenre() {
        for (Boolean gen : genres) {
            gen = false;
        }
    }

    /**
     * Return if an item is unknown genre
     * @return true is is unknong genre or false otherwise
     */
    public Boolean isUnknownGenre() {
        for (Boolean gen : genres) {
            if (gen == true) {
                return false;
            }
        }
        return true;
    }

    /**
     * Get the item Id
     * @return Item Id
     */
    public Integer getIditem() {
        return iditem;
    }

    /**
     * Set the item Id
     * @param iditem Item Id
     */
    private void setIditem(Integer iditem) {
        this.iditem = iditem;
    }

    /**
     * Get the item name
     * @return Item name
     */
    public String getName() {
        return name;
    }

    /**
     * Set item name
     * @param name Item name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get size of genres
     * @return Size of genres
     */
    public int getNumGenres() {
        return numGenres;
    }
    

    /**
     * Get Action genre value
     *
     * @return Boolean value (true/false)
     */
    public Boolean isActionGenre() {
        return this.genres.get(0);
    }

    /**
     * Set Action genre value
     *
     * @param value Boolean value (true/false)
     */
    public void setActionGenre(Boolean value) {
        this.genres.set(0, value);
    }

    /**
     * Get Adventure genre value
     *
     * @return Boolean value (true/false)
     */
    public Boolean isAdventureGenre() {
        return this.genres.get(1);
    }

    /**
     * Set Adventure genre value
     *
     * @param value Boolean value (true/false)
     */
    public void setAdventureGenre(Boolean value) {
        this.genres.set(1, value);
    }

    /**
     * Get Animation genre value
     *
     * @return Boolean value (true/false)
     */
    public Boolean isAnimationGenre() {
        return this.genres.get(2);
    }

    /**
     * Set Animation genre value
     *
     * @param value Boolean value (true/false)
     */
    public void setAnimationGenre(Boolean value) {
        this.genres.set(2, value);
    }

    /**
     * Get Childrens genre value
     *
     * @return Boolean value (true/false)
     */
    public Boolean isChildrensGenre() {
        return this.genres.get(3);
    }

    /**
     * Set Childrens genre value
     *
     * @param value Boolean value (true/false)
     */
    public void setChildrensGenre(Boolean value) {
        this.genres.set(3, value);
    }

    /**
     * Get Comedy genre value
     *
     * @return Boolean value (true/false)
     */
    public Boolean isComedyGenre() {
        return this.genres.get(4);
    }

    /**
     * Set Comedy genre value
     *
     * @param value Boolean value (true/false)
     */
    public void setComedyGenre(Boolean value) {
        this.genres.set(4, value);
    }

    /**
     * Get Crime genre value
     *
     * @return Boolean value (true/false)
     */
    public Boolean isCrimeGenre() {
        return this.genres.get(5);
    }

    /**
     * Set Crime genre value
     *
     * @param value Boolean value (true/false)
     */
    public void setCrimeGenre(Boolean value) {
        this.genres.set(5, value);
    }

    /**
     * Get Documentary genre value
     *
     * @return Boolean value (true/false)
     */
    public Boolean isDocumentaryGenre() {
        return this.genres.get(6);
    }

    /**
     * Set Documentary genre value
     *
     * @param value Boolean value (true/false)
     */
    public void setDocumentaryGenre(Boolean value) {
        this.genres.set(6, value);
    }

    /**
     * Get Drama genre value
     *
     * @return Boolean value (true/false)
     */
    public Boolean isDramaGenre() {
        return this.genres.get(7);
    }

    /**
     * Set Drama genre value
     *
     * @param value Boolean value (true/false)
     */
    public void setDramaGenre(Boolean value) {
        this.genres.set(7, value);
    }

    /**
     * Get Fantasy genre value
     *
     * @return Boolean value (true/false)
     */
    public Boolean isFantasyGenre() {
        return this.genres.get(8);
    }

    /**
     * Set Fantasy genre value
     *
     * @param value Boolean value (true/false)
     */
    public void setFantasyGenre(Boolean value) {
        this.genres.set(8, value);
    }

    /**
     * Get FilmNoir genre value
     *
     * @return Boolean value (true/false)
     */
    public Boolean isFilmNoirGenre() {
        return this.genres.get(9);
    }

    /**
     * Set Film Noir genre value
     *
     * @param value Boolean value (true/false)
     */
    public void setFilmNoirGenre(Boolean value) {
        this.genres.set(9, value);
    }

    /**
     * Get Horror genre value
     *
     * @return Boolean value (true/false)
     */
    public Boolean isHorrorGenre() {
        return this.genres.get(10);
    }

    /**
     * Set Horror genre value
     *
     * @param value Boolean value (true/false)
     */
    public void setHorrorGenre(Boolean value) {
        this.genres.set(10, value);
    }

    /**
     * Get Musical genre value
     *
     * @return Boolean value (true/false)
     */
    public Boolean isGenre() {
        return this.genres.get(11);
    }

    /**
     * Set Musical genre value
     *
     * @param value Boolean value (true/false)
     */
    public void setMusicalGenre(Boolean value) {
        this.genres.set(11, value);
    }

    /**
     * Get Mystery genre value
     *
     * @return Boolean value (true/false)
     */
    public Boolean isMysteryGenre() {
        return this.genres.get(12);
    }

    /**
     * Set Mystery genre value
     *
     * @param value Boolean value (true/false)
     */
    public void setMysteryGenre(Boolean value) {
        this.genres.set(12, value);
    }

    /**
     * Get Romance genre value
     *
     * @return Boolean value (true/false)
     */
    public Boolean isRomanceGenre() {
        return this.genres.get(13);
    }

    /**
     * Set Romance genre value
     *
     * @param value Boolean value (true/false)
     */
    public void setRomanceGenre(Boolean value) {
        this.genres.set(13, value);
    }

    /**
     * Get Sci-Fi genre value
     *
     * @return Boolean value (true/false)
     */
    public Boolean isSciFiGenre() {
        return this.genres.get(14);
    }

    /**
     * Set Sci-Fi genre value
     *
     * @param value Boolean value (true/false)
     */
    public void setSciFiGenre(Boolean value) {
        this.genres.set(14, value);
    }

    /**
     * Get Thriller genre value
     *
     * @return Boolean value (true/false)
     */
    public Boolean isThrillerGenre() {
        return this.genres.get(15);
    }

    /**
     * Set Thriller genre value
     *
     * @param value Boolean value (true/false)
     */
    public void setThrillerGenre(Boolean value) {
        this.genres.set(15, value);
    }

    /**
     * Get War genre value
     *
     * @return Boolean value (true/false)
     */
    public Boolean isWarGeGenre() {
        return this.genres.get(16);
    }

    /**
     * Set War genre value
     *
     * @param value Boolean value (true/false)
     */
    public void setWarGenre(Boolean value) {
        this.genres.set(16, value);
    }

    /**
     * Get Western genre value
     *
     * @return Boolean value (true/false)
     */
    public Boolean isWesternGeGenre() {
        return this.genres.get(17);
    }

    /**
     * Set Western genre value
     *
     * @param value Boolean value (true/false)
     */
    public void setWesternGenre(Boolean value) {
        this.genres.set(17, value);
    }

    /**
     * Get the Item information in a string
     * @return String with Item information
     */
    public String toString() {
        String s = "[ITEM] Id: " + this.iditem + ", Name: " + this.name;
        s += ", Genres: {";
        if (this.isUnknownGenre()) {
            s += "unknown";
        } else {
            Boolean primero = true;
            for (int i = 0; i < this.genres.size(); ++i) {
                if (this.genres.get(i) == true) {
                    if(!primero){
                        s += ", " + this.genresStrings.get(i);
                    }else{
                        s += this.genresStrings.get(i);
                        primero = false;
                    }
                }
            }
        }
        s += "}";
        return s;
    }
}
