import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Properties;

public class Credentials {
    static Properties properties = new Properties();

    static {
        try {
            File file = new File("credentials.properties");
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
        properties.getProperty("DEVELOPER_KEY");
    }
}
