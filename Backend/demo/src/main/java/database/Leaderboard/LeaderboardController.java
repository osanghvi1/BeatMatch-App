package database.Leaderboard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

@RestController
@RequestMapping("/leaderboard")
@Tag(name = "Leaderboard API", description = "Handles leaderboard retrieval and reset operations.")
public class LeaderboardController {

    @Autowired
    private LeaderboardService leaderboardService;

    /**
     * Retrieve the top 10 leaderboard.
     *
     * @return A list of top 10 `Leaderboard` entries.
     */
    @Operation(summary = "Get top 10 leaderboard", description = "Retrieve the top 10 songs from the leaderboard.")
    @GetMapping("/top/10")
    public List<Leaderboard> getTop10Leaderboard() {
        return leaderboardService.getTop10Leaderboard();
    }

    /**
     * Retrieve the top 100 leaderboard.
     *
     * @return A list of top 100 `Leaderboard` entries.
     */
    @Operation(summary = "Get top 100 leaderboard", description = "Retrieve the top 100 songs from the leaderboard.")
    @GetMapping("/top/100")
    public List<Leaderboard> getTop100Leaderboard() {
        return leaderboardService.getTop100Leaderboard();
    }

    /**
     * Reset and update the leaderboards.
     *
     * @return A success message indicating the leaderboards were reset.
     */
    @Operation(summary = "Reset leaderboards", description = "Resets the top 10 and top 100 leaderboards.")
    @PostMapping("/reset")
    public String resetLeaderboards() {
        leaderboardService.resetLeaderboard();
        return "{\"message\": \"Leaderboards reset successfully\"}";
    }
}
