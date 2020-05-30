import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

public class Credentials {
    static Properties properties = new Properties();

    static {
        try {
            File file = new File("src/main/resources/credentials.properties");
            if (!file.exists()) {
                throw new IllegalStateException(String.format("could not find file at [%s]", file.getAbsolutePath()));
            }
            FileInputStream fileInputStream = new FileInputStream(file);
            properties.load(fileInputStream);
            fileInputStream.close();
        } catch (Exception e) {
            throw new RuntimeException(e);// e.printStackTrace() is not a good exception strategy
        }
    }

    public static String getYoutubeApiKey() {
        return properties.getProperty("DEVELOPER_KEY");
    }

    public static String getApplicationName() {
        return properties.getProperty("APPLICATION_NAME");
    }
}
