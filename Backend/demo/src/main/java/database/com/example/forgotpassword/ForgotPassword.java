package database.com.example.forgotpassword;
import jakarta.persistence.*;
//Om Sanghvi Made this change
@Entity
public class ForgotPassword {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;


    @Column(unique = true)  // This ensures that usernames are unique in the database
    private String email;
    private String ansSecurityQuestion1;

    private String ansSecurityQuestion2;

    // Constructors
    public ForgotPassword(String email, String ansSecurityQuestion1, String ansSecurityQuestion2) {
        this.email = email;
        this.ansSecurityQuestion1 = ansSecurityQuestion1;
        this.ansSecurityQuestion2 = ansSecurityQuestion2;
    }


    public ForgotPassword() {}

    // Getters and Setters

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public String getansSecurityQuestion1() {
        return ansSecurityQuestion1;
    }

    public void setansSecurityQuestion1(String ansSecurityQuestion1) {
        this.ansSecurityQuestion1 = ansSecurityQuestion1;
    }




    public String getansSecurityQuestion2() {
        return ansSecurityQuestion2;
    }

    public void setansSecurityQuestion2(String ansSecurityQuestion2) {
        this.ansSecurityQuestion2 = ansSecurityQuestion2;
    }


}
