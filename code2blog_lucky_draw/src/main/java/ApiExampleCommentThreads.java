/**
 * Sample Java code for youtube.commentThreads.list
 * See instructions for running these code samples locally:
 * https://developers.google.com/explorer-help/guides/code_samples#java
 */

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.CommentSnippet;
import com.google.api.services.youtube.model.CommentThread;
import com.google.api.services.youtube.model.CommentThreadListResponse;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Consumer;
import java.util.logging.Logger;

public class ApiExampleCommentThreads {
    static Logger logger = Logger.getLogger(ApiExampleCommentThreads.class.getName());
    // You need to set this value for your code to compile.
    // For example: ... DEVELOPER_KEY = "YOUR ACTUAL KEY";
    private static final String DEVELOPER_KEY = Credentials.getYoutubeApiKey();

    private static final String APPLICATION_NAME = Credentials.getApplicationName();
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();

    /**
     * Build and return an authorized API client service.
     *
     * @return an authorized API client service
     * @throws GeneralSecurityException, IOException
     */
    public static YouTube getService() throws GeneralSecurityException, IOException {
        final NetHttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
        return new YouTube.Builder(httpTransport, JSON_FACTORY, null)
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
            throws Exception {
        YouTube youtubeService = getService();
        // Define and execute the API request
        YouTube.CommentThreads.List request = youtubeService.commentThreads()
                .list("snippet");
        CommentThreadListResponse response = request.setKey(DEVELOPER_KEY)
                .setVideoId("J9NuELWYgB4")
                .execute();
        Set contestants = new TreeSet();  // don't try to add your name multiple times in the lucky draw
        response.getItems().forEach(new Consumer<CommentThread>() {
            public void accept(CommentThread commentThread) {
                try {
                    CommentSnippet snippet = commentThread.getSnippet().getTopLevelComment().getSnippet();
//                    logger.info(String.format("Contestant Name [%s]", snippet.getAuthorDisplayName()) );
                    contestants.add(snippet.getAuthorDisplayName());
                } catch (Exception e) {
                    throw new RuntimeException(e); // fail fast principle
                }
            }
        });
//        System.out.println(response);
        System.out.println(String.format("[%s] has been selected as the winner of lucky draw.\n" +
                        "You get 1 hour free training seesion on the topic that your select.\n" +
                        "Send your skype id to my email VISHNU31122013@gmail.com",
                contestants.toArray()[new Random().nextInt(contestants.size())]));
    }
}