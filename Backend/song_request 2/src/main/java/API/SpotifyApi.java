package API;

public class SpotifyApi {
    // Replace these with your actual client ID, redirect URI, and scopes
    private static final String CLIENT_ID = "acc59f1ad406423eb8469709f72feaec";
    private static final String REDIRECT_URI = "http://localhost:8080/callback";
    private static final String SCOPES = "user-read-private user-read-email";  // Add scopes you need

    public static void main(String[] args) {
        // URL for Spotify's authorization endpoint
        String authUrl = "https://accounts.spotify.com/authorize" +
                "?response_type=code" +
                "&client_id=" + CLIENT_ID +
                "&scope=" + SCOPES.replace(" ", "%20") +
                "&redirect_uri=" + REDIRECT_URI;

        System.out.println("Open this URL in your browser: " + authUrl);
    }
}
