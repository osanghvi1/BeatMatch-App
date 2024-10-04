package com.example.forgotpassword;

import jakarta.persistence.*;

@Entity
public class ForgotPassword {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;


    @Column(unique = true)  // This ensures that usernames are unique in the database
    private String username;
    private String securityQuestion1;
    private String ansSecurityQuestion1;
    private String securityQuestion2;
    private String ansSecurityQuestion2;

    // Constructors
    public ForgotPassword(String username, String securityQuestion1, String securityQuestion2,String ansSecurityQuestion1,String ansSecurityQuestion2 ) {
        this.username = username;
        this.securityQuestion1 = securityQuestion1;
        this.securityQuestion2 = securityQuestion2;
        this.ansSecurityQuestion1 = ansSecurityQuestion1;
        this.ansSecurityQuestion2 = ansSecurityQuestion2;
    }

    public ForgotPassword() {}

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSecurityQuestion1() {
        return securityQuestion1;
    }

    public void setSecurityQuestion1(String securityQuestion1) {
        this.securityQuestion1 = securityQuestion1;
    }

    public String getansSecurityQuestion1() {
        return ansSecurityQuestion1;
    }

    public void setansSecurityQuestion1(String ansSecurityQuestion1) {
        this.ansSecurityQuestion1 = ansSecurityQuestion1;
    }



    public String getSecurityQuestion2() {
        return securityQuestion2;
    }

    public void setSecurityQuestion2(String securityQuestion2) {
        this.securityQuestion2 = securityQuestion2;
    }



    public String getansSecurityQuestion2() {
        return ansSecurityQuestion2;
    }

    public void setansSecurityQuestion2(String ansSecurityQuestion2) {
        this.ansSecurityQuestion2 = ansSecurityQuestion2;
    }


}
