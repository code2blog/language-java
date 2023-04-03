package year2023;
// Import the required libraries
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.Video;
import com.google.api.services.youtube.model.VideoSnippet;
import year2020.Credentials;

public class UpdateDescription {
    // Define the API key and video ID
    final String API_KEY = Credentials.getYoutubeApiKey();
    final String VIDEO_ID = "YOUR_VIDEO_ID";

}
