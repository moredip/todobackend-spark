package representations;

import java.net.MalformedURLException;
import java.net.URL;

public class Links {
    private final URL baseUrl;

    public Links(URL baseUrl) {
        this.baseUrl = baseUrl;
    }

    String resolve(String path) {
        try {
            return new URL(baseUrl,path).toString();
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }
}
