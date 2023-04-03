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
    final String PLAYLIST_ID = "PL7ws7gPffavKlwxWX-JzccmdkQ7GUwKjB";
    final boolean readOnly = false;

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
            processPlaylistItem(video);
        }


    }

    private void processPlaylistItem(PlaylistItem playlistItem) throws Exception {
        PlaylistItemSnippet snippet = playlistItem.getSnippet();
        if(readOnly){
            log.info(String.format("video description : [%s]", snippet.getDescription()));
            return;
        }
        log.info(snippet);

        String videoId = playlistItem.getSnippet().getResourceId().getVideoId();
        log.info(String.format("videoId=%s", videoId));
        processVideoStatus(videoId);
        processVideoSnippet(videoId);
    }

    private void processVideoStatus(String videoId) throws Exception {
        YouTube.Videos.List videoRequest = youtube.videos().list("status");
        videoRequest.setId(videoId);
        videoRequest.setKey(API_KEY);
        VideoListResponse videoResponse = videoRequest.execute();

        // Get the first video in the list
        List<Video> videoList = videoResponse.getItems();
        if (videoList.isEmpty()) {
            log.info("Can't find a video with ID: " + videoId);
            return;
        }
        Video video = videoList.get(0);
        VideoStatus status = video.getStatus();

        log.info(String.format("video status in json format : [%s]", status));
        status.setPrivacyStatus("public");
        video.setStatus(status);

        // Update the video on the API
        YouTube.Videos.Update updateRequest = youtube.videos().update("status", video);
        updateRequest.setKey(API_KEY);
        Video updatedVideo = updateRequest.execute();
        //
    }

    private void processVideoSnippet(String videoId) throws Exception {
        YouTube.Videos.List videoRequest = youtube.videos().list("snippet");
        videoRequest.setId(videoId);
        videoRequest.setKey(API_KEY);
        VideoListResponse videoResponse = videoRequest.execute();

        // Get the first video in the list
        List<Video> videoList = videoResponse.getItems();
        if (videoList.isEmpty()) {
            log.info("Can't find a video with ID: " + videoId);
            return;
        }
        Video video = videoList.get(0);

        // Update the video description
        VideoSnippet snippet = video.getSnippet();

        log.info(String.format("video attributes in json format : [%s]", snippet));

        String replcementDescription = replaceEmailInDescription(snippet.getDescription());
        VideoSnippet videoSnippet = snippet.setDescription(replcementDescription);
        video.setSnippet(snippet);

        // Update the video on the API
        YouTube.Videos.Update updateRequest = youtube.videos().update("snippet", video);
        updateRequest.setKey(API_KEY);
        Video updatedVideo = updateRequest.execute();
        log.info(String.format("Updated description = [%s]", replcementDescription));
    }

    public static void main(String[] args) throws Exception {
        new PlaylistVideoDescription().list();
        //PlaylistVideoDescription.replaceEmail();
    }

    private static void replaceEmail() throws Exception {
        File fileInput = new File(Credentials.getProperties().getProperty("PLAYLIST_INPUT"));
        File fileOutput = new File(Credentials.getProperties().getProperty("PLAYLIST_OUTPUT"));
        String input = new String(Files.readAllBytes(Paths.get(fileInput.getAbsolutePath())));
        String output = replaceEmailInDescription(input);
        Files.write(Paths.get(fileOutput.getAbsolutePath()), output.getBytes());
        System.out.println("done");
    }

    private static String replaceEmailInDescription(String output) throws IOException {
        File fileReplace = new File(Credentials.getProperties().getProperty("PLAYLIST_REPLACEMENT"));
        List<String> lines = Files.readAllLines(Paths.get(fileReplace.getAbsolutePath()));
        for(String line : lines){
            output = output.replace(line, "");
        }
        return output;
    }
}
