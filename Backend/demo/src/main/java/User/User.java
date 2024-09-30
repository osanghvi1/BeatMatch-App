package User;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userID;
    private String userName;
    private int password;
    private String email;
    private String firstName;
    private String lastName;
    private int accountVisibility;
    private int accountStatus;

    public User(String firstName, String lastName, String email, String username, int password, int accountVisibility, int accountStatus) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.userName = username;
        this.password = password;
        this.accountVisibility = accountVisibility;
        this.accountStatus = accountStatus;
    }

    public User() {
    }


    // ================ Getters and setters ==========================//
    public int getUserID() {return userID;}

    public int getPassword() {return password;}

    public String getUserName() {return userName;}

    public String getEmail() {return email;}

    public String getFirstName() {return firstName;}

    public String getLastName() {return lastName;}

    public int getAccountVisibility() {return accountVisibility;}

    public int getAccountStatus() {return accountStatus;}

    public void setUserID(int userID) {this.userID = userID;}

    public void setPassword(int password) {this.password = password;}

    public void setUserName(String userName) {this.userName = userName;}

    public void setEmail(String email) {this.email = email;}

    public void setFirstName(String firstName) {this.firstName = firstName;}

    public void setLastName(String lastName) {this.lastName = lastName;}

    public void setAccountVisibility(int accountVisibility) {this.accountVisibility = accountVisibility;}

    public void setAccountStatus(int accountStatus) {this.accountStatus = accountStatus;}



}
