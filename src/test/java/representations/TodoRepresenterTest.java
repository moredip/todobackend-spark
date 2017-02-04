package representations;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import domain.Todo;
import org.junit.Test;

import java.net.MalformedURLException;
import java.net.URL;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

public class TodoRepresenterTest {

    @Test
    public void representsATodoWithAFullUrl() throws MalformedURLException {
        Todo todo = Todo.of(100, "some-title", false, 10);
        Links links = new Links(new URL("http://example.com/foo/bar"));
        String representation = TodoRepresenter.relativeTo(links).represent(todo);

        JsonParser parser = new JsonParser();
        JsonElement actualJson = parser.parse(representation);
        JsonElement expectedJson = parser.parse(
            "{" +
            "  \"id\": 100," +
            "  \"title\": \"some-title\"," +
            "  \"url\": \"http://example.com/todos/100\"," +
            "  \"completed\": false," +
            "  \"order\": 10" +
            "}"
        );
        assertEquals(expectedJson,actualJson);
    }
}