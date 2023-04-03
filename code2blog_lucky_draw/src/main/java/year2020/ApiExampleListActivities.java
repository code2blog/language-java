package year2020; /**
 * Sample Java code for youtube.activities.list
 * See instructions for running these code samples locally:
 * https://developers.google.com/explorer-help/guides/code_samples#java
 */

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;

import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.Collection;
import java.util.function.Consumer;

public class ApiExampleListActivities {
    private static final String CLIENT_SECRETS= "client_secret.json";
    private static final Collection<String> SCOPES =
            Arrays.asList("https://www.googleapis.com/auth/youtube.force-ssl");

    private static final String APPLICATION_NAME = Credentials.getApplicationName();
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();

    /**
     * Create an authorized Credential object.
     *
     * @return an authorized Credential object.
     * @throws IOException
     */
    public static Credential authorize(final NetHttpTransport httpTransport) throws IOException {
        // Load client secrets.
        InputStream in = ApiExample.class.getResourceAsStream(CLIENT_SECRETS);
        GoogleClientSecrets clientSecrets =
          GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));
        // Build flow and trigger user authorization request.
        GoogleAuthorizationCodeFlow flow =
            new GoogleAuthorizationCodeFlow.Builder(httpTransport, JSON_FACTORY, clientSecrets, SCOPES)
            .build();
        Credential credential =
            new AuthorizationCodeInstalledApp(flow, new LocalServerReceiver()).authorize("user");
        return credential;
    }

    /**
     * Build and return an authorized API client service.
     *
     * @return an authorized API client service
     * @throws GeneralSecurityException, IOException
     */
    public static YouTube getService() throws GeneralSecurityException, IOException {
        final NetHttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
        Credential credential = authorize(httpTransport);
        return new YouTube.Builder(httpTransport, JSON_FACTORY, credential)
            .setApplicationName(APPLICATION_NAME)
            .build();
    }

    /**
     * Call function to create API service object. Define and
     * execute API request. Print API response.
     *
     * @throws GeneralSecurityException, IOException, GoogleJsonResponseException
     */
    public static void main(String[] args)
        throws GeneralSecurityException, IOException, GoogleJsonResponseException {
        YouTube youtubeService = getService();
        // Define and execute the API request
        YouTube.Activities.List request = youtubeService.activities()
            .list("snippet,contentDetails");
        ActivityListResponse response = request.setChannelId("UCq9U-gJ1LtDCE4W5BhEDFSQ")
            .setMaxResults(50L)
            .execute();
        response.getItems().forEach(new Consumer<Activity>() {
            @lombok.SneakyThrows
            @Override
            public void accept(Activity activity) {
                String videoId = activity.getContentDetails().getUpload().getVideoId();
                System.out.println(String.format("https://youtu.be/%s %s", videoId, activity.getSnippet().getTitle()));
                addToPlaylist(videoId, youtubeService);
            }
        });
//        System.out.println(response);
    }

    private static void addToPlaylist(String videoId, YouTube youtubeService) throws Exception {
        // Define the PlaylistItem object, which will be uploaded as the request body.
        PlaylistItem playlistItem = new PlaylistItem();

        // Add the snippet object property to the PlaylistItem object.
        PlaylistItemSnippet snippet = new PlaylistItemSnippet();
        snippet.setPlaylistId("PL7ws7gPffavLAi9SosH1jvwZwzny_gfQ4");
        snippet.setPosition(0L);
        ResourceId resourceId = new ResourceId();
        resourceId.setKind("youtube#video");
        resourceId.setVideoId(videoId);
        snippet.setResourceId(resourceId);
        playlistItem.setSnippet(snippet);

        // Define and execute the API request
        YouTube.PlaylistItems.Insert request = youtubeService.playlistItems()
                .insert("snippet", playlistItem);
        PlaylistItem response = request.execute();
    }
}