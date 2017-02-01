import plumbing.JsonTransformer;
import representations.Todo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static spark.Spark.*;

public class Main {
    public static void main(String[] args) {
        JsonTransformer jsonTransformer = new JsonTransformer();

        get("/hello", (req, res) -> "Hello World");

        get("/todos", "application/json", (request, response) -> {
            return Arrays.asList(
              Todo.of(1,"foo",false,1)
            );
        }, jsonTransformer);
    }
}