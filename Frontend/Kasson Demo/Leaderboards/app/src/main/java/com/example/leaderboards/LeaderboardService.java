package com.example.leaderboards;

import com.example.leaderboards.Leaderboard;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface LeaderboardService {

    @GET("/leaderboard/{category}")
    Call<List<Leaderboard>> getLeaderboardByCategory(@Path("category") String category);

    @GET("/leaderboard/reset")
    Call<String> resetLeaderboard(); // For manual testing if needed
}
