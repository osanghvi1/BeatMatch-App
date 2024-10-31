package database.SimilarGenres;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

public class SimilarGenres {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int genreID;
    private String genreName;
    private String similarGenre1;
    private String similarGenre2;
    private String similarGenre3;


    public SimilarGenres(String genreName, String similarGenre1, String similarGenre2, String similarGenre3) {
        this.genreName = genreName;
        this.similarGenre1 = similarGenre1;
        this.similarGenre2 = similarGenre2;
        this.similarGenre3 = similarGenre3;
    }

    public SimilarGenres() {}

    public int getGenreID() {return genreID;}
    public String getGenreName() {return genreName;}
    public String getSimilarGenre1() {return similarGenre1;}
    public String getSimilarGenre2() {return similarGenre2;}
    public String getSimilarGenre3() {return similarGenre3;}


    public void setGenreName(String genreName) {this.genreName = genreName;}
    public void setSimilarGenre1(String similarGenre1) {this.similarGenre1 = similarGenre1;}
    public void setSimilarGenre2(String similarGenre2) {this.similarGenre2 = similarGenre2;}
    public void setSimilarGenre3(String similarGenre3) {this.similarGenre3 = similarGenre3;}



}
