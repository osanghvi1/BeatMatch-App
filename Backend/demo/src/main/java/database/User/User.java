package database.User;

import database.GenrePreferences.GenrePreferences;
import jakarta.persistence.*;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userID;
    private String userName;
    private String password;

    @Column(name = "email", unique = true)
    private String email;

    private String firstName;
    private String lastName;
    private int accountVisibility;
    private int accountStatus;

    @OneToOne
    private GenrePreferences genrePreferences;

    public User(String firstName, String lastName, String email, String username, String password, int accountVisibility, int accountStatus) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.userName = username;
        this.password = password;
        this.accountVisibility = accountVisibility;
        this.accountStatus = accountStatus;
    }

    public User() {}


    // ================ Getters and setters ==========================//
    public int getUserID() {return userID;}

    public String getPassword() {return password;}

    public String getUserName() {return userName;}

    public String getEmail() {return email;}

    public String getFirstName() {return firstName;}

    public String getLastName() {return lastName;}

    public int getAccountVisibility() {return accountVisibility;}

    public int getAccountStatus() {return accountStatus;}

    public GenrePreferences getGenrePreferences() {return genrePreferences;}

    public void setUserID(int userID) {this.userID = userID;}

    public void setPassword(String password) {this.password = password;}

    public void setUserName(String userName) {this.userName = userName;}

    public void setEmail(String email) {this.email = email;}

    public void setFirstName(String firstName) {this.firstName = firstName;}

    public void setLastName(String lastName) {this.lastName = lastName;}

    public void setAccountVisibility(int accountVisibility) {this.accountVisibility = accountVisibility;}

    public void setAccountStatus(int accountStatus) {this.accountStatus = accountStatus;}

    public void setGenrePreferences(GenrePreferences genrePreferences) {this.genrePreferences = genrePreferences;}

}
