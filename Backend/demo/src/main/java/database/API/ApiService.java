package database.API;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;

@Service
public class ApiService {

    // Deezer API base URL
    private static final String API_BASE_URL = "https://api.deezer.com";

    // Method to search for a track by name and return the track ID of the first result
    public long searchTrack(String trackName) throws JSONException {
        RestTemplate restTemplate = new RestTemplate();
        String searchUrl = API_BASE_URL + "/search/track?q=" + trackName;

        ResponseEntity<String> response = restTemplate.getForEntity(searchUrl, String.class);
        JSONObject responseJson = new JSONObject(response.getBody());
        JSONArray dataArray = responseJson.getJSONArray("data");

        if (dataArray.length() > 0) {
            JSONObject track = dataArray.getJSONObject(0);
            return track.getLong("id");
        } else {
            throw new JSONException("No tracks found for the search query.");
        }
    }

    // Method to fetch track details by trackID (songID)
    public JSONObject getTrackDetailsById(long trackID) throws JSONException {
        RestTemplate restTemplate = new RestTemplate();
        String trackDetailsUrl = API_BASE_URL + "/track/" + trackID;

        ResponseEntity<String> trackResponse = restTemplate.getForEntity(trackDetailsUrl, String.class);
        return new JSONObject(trackResponse.getBody());
    }

    // Method to fetch album details by albumID
    public JSONObject getAlbumDetailsById(long albumID) throws JSONException {
        RestTemplate restTemplate = new RestTemplate();
        String albumDetailsUrl = API_BASE_URL + "/album/" + albumID;

        ResponseEntity<String> albumResponse = restTemplate.getForEntity(albumDetailsUrl, String.class);
        return new JSONObject(albumResponse.getBody());
    }

    // Method to fetch artist details by artistID
    public JSONObject getArtistDetailsById(long artistID) throws JSONException {
        RestTemplate restTemplate = new RestTemplate();
        String artistDetailsUrl = API_BASE_URL + "/artist/" + artistID;

        ResponseEntity<String> artistResponse = restTemplate.getForEntity(artistDetailsUrl, String.class);
        return new JSONObject(artistResponse.getBody());
    }

    // Method to fetch genre details by genreID
    public JSONObject getGenreDetailsById(long genreID) throws JSONException {
        RestTemplate restTemplate = new RestTemplate();
        String genreDetailsUrl = API_BASE_URL + "/genre/" + genreID;

        ResponseEntity<String> genreResponse = restTemplate.getForEntity(genreDetailsUrl, String.class);
        return new JSONObject(genreResponse.getBody());
    }
}
