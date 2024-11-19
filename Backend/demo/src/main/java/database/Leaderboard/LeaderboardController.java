package database.Leaderboard;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller for managing the leaderboard.
 */
@RestController
@RequestMapping("/leaderboard")
@Tag(name = "Leaderboard API", description = "Handles leaderboard retrieval and reset operations.")
public class LeaderboardController {

    @Autowired
    private LeaderboardService leaderboardService;

    @Autowired
    private LeaderboardRepository leaderboardRepository;

    /**
     * Retrieve the current leaderboard.
     *
     * @return A list of `Leaderboard` entries.
     */
    @Operation(summary = "Get leaderboard", description = "Retrieve the current leaderboard with all song rankings.")
    @GetMapping
    public List<Leaderboard> getLeaderboard() {
        return leaderboardRepository.findAll();
    }

    /**
     * Reset and update the leaderboard.
     *
     * @return A success message indicating the leaderboard was reset.
     */
    @Operation(summary = "Reset leaderboard", description = "Resets the leaderboard and recalculates song rankings.")
    @PostMapping("/reset")
    public String resetLeaderboard() {
        leaderboardService.resetLeaderboard();
        return "{\"message\": \"Leaderboard reset successfully\"}";
    }
}
