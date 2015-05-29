package es.AgustRuiz.RecommenderSystem;

import java.sql.Struct;

/**
 * User class
 *
 * @author Agustin Ruiz Linares <arl00029@red.ujaen.es>
 */
public class User {

    /**
     * * VARIABLES **
     */
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
    String zipcode;

    /**
     * * METHODS **
     */
    /**
     * Constructor
     */
    public User() {
    }

    /**
     * Get user id
     *
     * @return User id
     */
    public Integer getIduser() {
        return iduser;
    }

    /**
     * Set user id
     *
     * @param iduser User id
     */
    public void setIduser(Integer iduser) {
        this.iduser = iduser;
    }

    /**
     * Get user name
     *
     * @return User name
     */
    public String getName() {
        return name;
    }

    /**
     * Set user name
     *
     * @param name User name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get user age
     *
     * @return User age
     */
    public Integer getAge() {
        return age;
    }

    /**
     * Set user age
     *
     * @param age User age
     */
    public void setAge(Integer age) {
        this.age = age;
    }

    /**
     * Get user gender
     *
     * @return User gender
     */
    public char getGender() {
        return gender;
    }

    /**
     * Set user gender
     *
     * @param gender User gender
     */
    public void setGender(char gender) {
        if (Gender.isValidGender(gender)){
            this.gender = gender;
        }else{
            this.gender = Gender.UNDEFINED;
        }
    }

    /**
     * Get user occupation
     *
     * @return User occupation
     */
    public String getOccupation() {
        return occupation;
    }

    /**
     * Set user occupation
     *
     * @param occupation User occupation
     */
    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    /**
     * Get user zip code
     *
     * @return User zip code
     */
    public String getZipcode() {
        return zipcode;
    }

    /**
     * Set user zipcode
     *
     * @param zipcode User zip code
     */
    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }
}

/**
 * Gender class
 *
 * @author Agustin Ruiz Linares <arl00029@red.ujaen.es>
 */
class Gender {

    public static final char UNDEFINED = '-';
    public static final String UNDEFINED_STRING = "undefined";
    public static final char MALE = 'M';
    public static final String MALE_STRING = "Male";
    public static final char FEMALE = 'F';
    public static final String FEMALE_STRING = "Female";

    public static Boolean isValidGender(char gender) {
        if (gender == Gender.MALE || gender == Gender.FEMALE) {
            return true;
        } else {
            return false;
        }
    }
};
