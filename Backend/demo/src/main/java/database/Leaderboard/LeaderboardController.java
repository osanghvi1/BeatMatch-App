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

    // Retrieve current leaderboard
    @GetMapping
    public List<Leaderboard> getLeaderboard() {
        return leaderboardRepository.findAll();
    }

    // Reset and update the leaderboard
    @PostMapping("/reset")
    public String resetLeaderboard() {
        leaderboardService.resetLeaderboard();
        return "{\"message\": \"Leaderboard reset successfully\"}";
    }
}
