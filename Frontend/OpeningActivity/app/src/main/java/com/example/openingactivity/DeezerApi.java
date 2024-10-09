package com.example.openingactivity;

import retrofit2.Call;
import retrofit2.http.GET;

public interface DeezerApi {
    @GET("genre")
    Call<GenreResponse> getGenres(); // Call to get genres from Deezer
}
