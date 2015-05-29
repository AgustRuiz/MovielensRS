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
public class User {

    /* VARIABLES */
    /// Item identificator
    Integer iduser;
    
    /// User name
    String name;
    
    /// User age
    Integer age;
    
    /// User gender
    char gender;
    
    /// User occupation
    String occupation;
    
    /// Zip code
    Integer zipcode;

    /**
     * Constructor
     */
    public User() {
    }

    /**
     * Get user id
     * @return User id
     */
    public Integer getIduser() {
        return iduser;
    }

    /**
     * Set user id
     * @param iduser User id
     */
    public void setIduser(Integer iduser) {
        this.iduser = iduser;
    }

    /**
     * Get user name
     * @return User name
     */
    public String getName() {
        return name;
    }

    /**
     * Set user name
     * @param name User name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get user age
     * @return User age
     */
    public Integer getAge() {
        return age;
    }

    /**
     * Set user age
     * @param age User age
     */
    public void setAge(Integer age) {
        this.age = age;
    }

    /**
     * Get user gender
     * @return User gender
     */
    public char getGender() {
        return gender;
    }

    /**
     * Set user gender
     * @param gender User gender
     */
    public void setGender(char gender) {
        this.gender = gender;
    }

    /**
     * Get user occupation
     * @return User occupation
     */
    public String getOccupation() {
        return occupation;
    }

    /**
     * Set user occupation
     * @param occupation User occupation
     */
    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    /**
     * Get user zip code
     * @return User zip code
     */
    public Integer getZipcode() {
        return zipcode;
    }

    /**
     * Set user zipcode
     * @param zipcode User zip code
     */
    public void setZipcode(Integer zipcode) {
        this.zipcode = zipcode;
    }
}
