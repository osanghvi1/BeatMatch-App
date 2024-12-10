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

    @Operation(summary = "Get top 10 leaderboard", description = "Retrieve the top 10 leaderboard entries.")
    @GetMapping("/top/10")
    public List<Leaderboard> getTop10Leaderboard() {
        return leaderboardService.getLeaderboard(10);
    }

    @Operation(summary = "Get top 100 leaderboard", description = "Retrieve the top 100 leaderboard entries.")
    @GetMapping("/top/100")
    public List<Leaderboard> getTop100Leaderboard() {
        return leaderboardService.getLeaderboard(100);
    }

    @Operation(summary = "Reset leaderboard", description = "Resets the leaderboard and updates it with the latest data.")
    @PostMapping("/reset")
    public String resetLeaderboard() {
        leaderboardService.resetLeaderboard();
        return "{\"message\": \"Leaderboard reset successfully\"}";
    }
}
