package coms309.people;


/**
 * Provides the Definition/Structure for the people row
 *
 * @author Vivek Bengre
 */

public class User {

    private String firstName;

    private String lastName;

    private String username;

    private String password;

    private String email;

    private String musicPref1;

    private String musicPref2;

    private String musicPref3;

    public User() {

    }

    public User(String username, String firstName, String lastName, String password, String email, String musicPref1, String musicPref2, String musicPref3) {

        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.email = email;
        this.musicPref1 = musicPref1;
        this.musicPref2 = musicPref2;
        this.musicPref3 = musicPref3;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() { return this.email; }
    public void setEmail(String email) { this.email = email; }

    public String getMusicPref1() { return this.musicPref1; }
    public void setMusicPref1(String musicPref1) { this.musicPref1 = musicPref1; }

    public String getMusicPref2() { return this.musicPref2; }
    public void setMusicPref2(String musicPref2) { this.musicPref2 = musicPref2; }

    public String getMusicPref3() { return this.musicPref3; }
    public void setMusicPref3(String musicPref3) { this.musicPref3 = musicPref3; }

    @Override
    public String toString() {
        return firstName + " "
                + lastName + " "
                + username + " "
                + password + " "
                + email + " "
                + musicPref1 + " "
                + musicPref2 + " "
                + musicPref3;
    }
}
