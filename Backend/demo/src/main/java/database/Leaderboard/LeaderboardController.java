package database.Leaderboard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/leaderboard")
public class LeaderboardController {

    @Autowired
    private LeaderboardService leaderboardService;

    @Autowired
    private LeaderboardRepository leaderboardRepository;

    // Get leaderboard entries for a specific category, sorted by rank
    @GetMapping("/{category}")
    public List<Leaderboard> getLeaderboardByCategory(@PathVariable String category) {
        return leaderboardRepository.findByCategoryOrderByRank(category);
    }

    // Endpoint to manually reset and update leaderboard (for testing)
    @GetMapping("/reset")
    public String resetLeaderboard() {
        leaderboardService.resetLeaderboardWeekly();
        return "{\"message\":\"Leaderboard reset successfully\"}";
    }
}
