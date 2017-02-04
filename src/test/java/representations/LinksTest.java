package representations;

import org.junit.Test;

import java.net.MalformedURLException;
import java.net.URL;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

public class LinksTest {

    @Test
    public void createsGoodLinkForUrlWithoutPath() throws MalformedURLException {
        Links links = new Links(new URL("http://example.com"));
        assertThat(links.resolve("/foo/bar"),is(equalTo("http://example.com/foo/bar")));
    }

    @Test
    public void createsGoodLinkForUrlWithPath() throws MalformedURLException {
        Links links = new Links(new URL("http://example.com/bar/baz"));
        assertThat(links.resolve("/foo/bar"),is(equalTo("http://example.com/foo/bar")));
    }
}