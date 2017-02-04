package representations;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import domain.Todo;

import java.util.List;
import java.util.stream.Collectors;

public class TodoRepresenter {
    private final Gson gson = new Gson();

    public static TodoRepresenter relativeTo(Links links) {
        return new TodoRepresenter(links);
    }

    public TodoRepresenter(Links links) {
        this.links = links;
    }

    public String represent(Todo todo) {
        return representAsJson(todo).toString();
    }

    public String represent(List<Todo> allTodos) {
        return gson.toJson(
            allTodos.stream()
                    .map(this::representAsJson)
                    .collect(Collectors.toList())
        );
    }

    private JsonObject representAsJson(Todo todo) {
        JsonObject json = gson.toJsonTree(todo).getAsJsonObject();
        json.addProperty("url",links.resolve("/todos/"+todo.id.toString())); // urk
        return json;
    }

    private final Links links;

}
