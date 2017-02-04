package plumbing;

import domain.Todo;
import representations.Links;
import representations.TodoRepresenter;
import spark.Request;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class SparkRequestContext {
    private final Request request;

    public static SparkRequestContext inContextOf(Request request){
        return new SparkRequestContext(request);
    }

    public SparkRequestContext(Request request) {
        this.request = request;
    }

    public String represent(Todo todo) {
        return todoRepresenter().represent(todo);
    }
    public String represent(List<Todo> allTodos) {
        return todoRepresenter().represent(allTodos);
    }

    private TodoRepresenter todoRepresenter() {
        return TodoRepresenter.relativeTo(links());
    }

    private Links links() {
        try {
            return new Links( new URL(request.url()) );
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

}
