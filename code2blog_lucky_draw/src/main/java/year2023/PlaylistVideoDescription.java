package year2023;

import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.*;
import lombok.extern.log4j.Log4j2;
import year2020.Credentials;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;

@Log4j2
public class PlaylistVideoDescription {
    final String API_KEY = Credentials.getYoutubeApiKey();
    final String PLAYLIST_ID = "PL7ws7gPffavI-kQ21xIlLCjV2LNoqwjRI";
    final boolean readOnly = true;

    private YouTube youtube;

    public PlaylistVideoDescription() throws Exception {
        if (readOnly) {
            youtube = new YouTube.Builder(new NetHttpTransport(), new JacksonFactory(), new HttpRequestInitializer() {
                public void initialize(HttpRequest request) throws IOException {
                }
            }).setApplicationName(Credentials.getApplicationName()).build();
        } else {
            youtube = GoogleOAuth2Credentials.getService();
        }
    }

    public void list() throws Exception {
        // Set the parameters for the API request
        HashMap<String, String> parameters = new HashMap<>();
        parameters.put("part", "snippet,contentDetails");
        parameters.put("maxResults", "250");
        parameters.put("playlistId", PLAYLIST_ID);

        // Call the playlistItems.list method to retrieve the playlist items
        YouTube.PlaylistItems.List playlistItemsListByPlaylistIdRequest = youtube.playlistItems().list(parameters.get("part").toString());
        if (parameters.containsKey("maxResults")) {
            playlistItemsListByPlaylistIdRequest.setMaxResults(Long.parseLong(parameters.get("maxResults").toString()));
        }
        if (parameters.containsKey("playlistId") && parameters.get("playlistId") != "") {
            playlistItemsListByPlaylistIdRequest.setPlaylistId(parameters.get("playlistId").toString());
        }
        playlistItemsListByPlaylistIdRequest.setKey(API_KEY);

        // Execute the request and get the response
        PlaylistItemListResponse response = playlistItemsListByPlaylistIdRequest.execute();
        List<PlaylistItem> videos = response.getItems();
        for (PlaylistItem video : videos) {
            processVideo(video);
        }


    }

    private void processVideo(PlaylistItem video) {
        PlaylistItemSnippet snippet = video.getSnippet();

        log.info(String.format("video description : [%s]", snippet.getDescription()));
    }


    public static void main(String[] args) throws Exception {
        //new PlaylistVideoDescription().list();
        PlaylistVideoDescription.replaceEmail();
    }

    private static void replaceEmail() throws Exception {
        File fileInput = new File(Credentials.getProperties().getProperty("PLAYLIST_INPUT"));
        File fileReplace = new File(Credentials.getProperties().getProperty("PLAYLIST_REPLACEMENT"));
        File fileOutput = new File(Credentials.getProperties().getProperty("PLAYLIST_OUTPUT"));
        String input = new String(Files.readAllBytes(Paths.get(fileInput.getAbsolutePath())));
        String output = input;
        List<String> lines = Files.readAllLines(Paths.get(fileReplace.getAbsolutePath()));
        for(String line : lines){
            output = output.replace(line, "");
        }
        Files.write(Paths.get(fileOutput.getAbsolutePath()), output.getBytes());
        System.out.println("done");
    }
}
