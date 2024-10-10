package database.GenrePreferences;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class GenrePreferences {

    @Id
    private int userID;
    private String email;
    private String genre1;
    private String genre2;
    private String genre3;



    public GenrePreferences(int userID, String email, String genre1, String genre2, String genre3) {
        this.userID = userID;
        this.email = email;
        this.genre1 = genre1;
        this.genre2 = genre2;
        this.genre3 = genre3;
    }


    public GenrePreferences() {}

    public int getUserID() {return userID;}
    public String getEmail() {return email;}
    public String getGenre1() {return genre1;}
    public String getGenre2() {return genre2;}
    public String getGenre3() {return genre3;}
    public void setUserID(int userID) {this.userID = userID;}
    public void setEmail(String email) {this.email = email;}
    public void setGenre1(String genre1) {this.genre1 = genre1;}
    public void setGenre2(String genre2) {this.genre2 = genre2;}
    public void setGenre3(String genre3) {this.genre3 = genre3;}
}
