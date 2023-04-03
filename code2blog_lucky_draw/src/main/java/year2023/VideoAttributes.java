package year2023;
// Import the required libraries

import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.Video;
import com.google.api.services.youtube.model.VideoListResponse;
import com.google.api.services.youtube.model.VideoSnippet;
import lombok.extern.log4j.Log4j2;
import year2020.Credentials;

import java.io.IOException;
import java.util.List;

@Log4j2
public class VideoAttributes {
    // Define the API key and video ID
    final String API_KEY = Credentials.getYoutubeApiKey();
    final String VIDEO_ID = "J9NuELWYgB4";
    private static final String APPLICATION_NAME = "code2blog_lucky_draw";

    public void updateDescription() {
        // Create a YouTube object
//        YouTube youtube = new YouTube.Builder(new NetHttpTransport(), new JacksonFactory(), new HttpRequestInitializer() {
//            public void initialize(HttpRequest request) throws IOException {
//            }
//        }).setApplicationName(APPLICATION_NAME).build();
        //
        YouTube youtube;
        try {
            youtube = GoogleOAuth2Credentials.getService();
        }catch (Exception e){
            throw new RuntimeException(e);
        }

        try {
            // Retrieve the video from the API
            // get-all-videos-from-my-own-youtube-channel-via-youtube-data-api-v3
            // The parameter "snippet" specifies that the API response will include the snippet property of the video resource, which contains information such as the title, description, thumbnails, etc. of the video1. You can also specify other parameters to filter or sort the videos according to your needs.
            YouTube.Videos.List videoRequest = youtube.videos().list("snippet");
            videoRequest.setId(VIDEO_ID);
            videoRequest.setKey(API_KEY);
            VideoListResponse videoResponse = videoRequest.execute();

            // Get the first video in the list
            List<Video> videoList = videoResponse.getItems();
            if (videoList.isEmpty()) {
                System.out.println("Can't find a video with ID: " + VIDEO_ID);
                return;
            }
            Video video = videoList.get(0);

            // Update the video description
            VideoSnippet snippet = video.getSnippet();

            log.info(String.format("video description : [%s]", snippet.getDescription()));
            log.info(String.format("video snippet in json format : [%s]", snippet));
            log.info(String.format("video attributes in json format : [%s]", video));

            snippet.setDescription("This is the updated description of the video.");
            video.setSnippet(snippet);

            // Update the video on the API
            YouTube.Videos.Update updateRequest = youtube.videos().update("snippet", video);
            updateRequest.setKey(API_KEY);
            Video updatedVideo = updateRequest.execute();

            // Print the result
            System.out.println("The video description has been updated to: " + updatedVideo.getSnippet().getDescription());

        } catch (GoogleJsonResponseException e) {
            e.printStackTrace();
            System.err.println("GoogleJsonResponseException code: " + e.getDetails().getCode() + " : "
                    + e.getDetails().getMessage());
        } catch (IOException e) {
            System.err.println("IOException: " + e.getMessage());
        } catch (Throwable t) {
            System.err.println("Throwable: " + t.getMessage());
        }
    }

    public static void main(String[] args) {
        new VideoAttributes().updateDescription();
    }

}
