package com.example.genresuggestions;


import java.util.List;

public class GenreResponse {
    private List<Genre> data;  // Assuming 'data' is a list of genres.

    public List<Genre> getData() {
        return data;
    }

    public void setData(List<Genre> data) {
        this.data = data;
    }
}
