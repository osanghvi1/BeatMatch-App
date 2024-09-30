package API;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class ApiService {

    // Deezer API base URL
    private static final String API_BASE_URL = "https://api.deezer.com";

    // Method to search for an artist by name
    public void searchArtist(String artistName) {
        // Create a RestTemplate instance
        RestTemplate restTemplate = new RestTemplate();

        // Construct the full URL for the API request
        String searchUrl = API_BASE_URL + "/search/artist?q=" + artistName;

        // Make a GET request to the Deezer API
        ResponseEntity<String> response = restTemplate.getForEntity(searchUrl, String.class);

        // Print the response
        System.out.println("Deezer API Response: " + response.getBody());
    }

    public static void main(String[] args) {
        // Example usage
        ApiService deezerApiService = new ApiService();
        deezerApiService.searchArtist("Daft Punk");  // Replace with any artist name
    }
}
