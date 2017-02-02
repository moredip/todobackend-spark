package plumbing;

import com.google.gson.Gson;
import db.TodoDAO;
import representations.NewTodo;
import domain.Todo;

import java.net.URISyntaxException;
import java.util.Arrays;

import static spark.Spark.*;

public class App {
    public static void initServer(int port) throws Exception {
        port(port);

        JsonTransformer jsonTransformer = new JsonTransformer();
        Gson gson = new Gson();

        Database db = Database.fromEnvVar();

        TodoDAO todoDao = db.getDao(TodoDAO.class);

        TodoController controller = new TodoController(todoDao);

        redirect.get("/", "/todos");

        post("/todos", "application/json", (request,response) -> {
            NewTodo newTodo = gson.fromJson(request.body(), NewTodo.class);
            Todo todo = controller.createTodo(newTodo);
            response.status(201);
            return todo;
        }, jsonTransformer);

        get("/todos", "application/json", (request, response) -> {
            return Arrays.asList(
                    Todo.of(1,"foo",false,1)
            );
        }, jsonTransformer);
    }
}
