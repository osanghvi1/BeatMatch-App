package com.example.openingactivity;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface PlaylistEntryApi {
    @GET("/playlistEntry/{pid}")
    Call<List<PlaylistEntry>> getPlaylistEntries(@Path("pid") int playlistId);

    @POST("/playlistEntry/addSong")
    Call<String> addSong(@Body PlaylistEntry entry);

    @DELETE("/playlistEntry/delete/{pid}/{sid}")
    Call<String> deleteSong(@Path("pid") int playlistId, @Path("sid") int songEntryId);
}
