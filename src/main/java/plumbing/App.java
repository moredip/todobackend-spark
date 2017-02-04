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

        addCORS();

        post("/todos", "application/json", (request,response) -> {
            NewTodo newTodo = gson.fromJson(request.body(), NewTodo.class);
            Todo todo = controller.createTodo(newTodo);
            response.status(201);
            return todo;
        }, jsonTransformer);

        get("/todos", "application/json", (request, response) -> controller.allTodos(), jsonTransformer);
    }

    // based on https://sparktutorials.github.io/2016/05/01/cors.html
    static void addCORS(){
        before((request, response) -> {
            response.header("Access-Control-Allow-Origin", "*");
        });

        options("/*", (request, response) -> {
            String accessControlRequestHeaders = request.headers("Access-Control-Request-Headers");
            if (accessControlRequestHeaders != null) {
                response.header("Access-Control-Allow-Headers", accessControlRequestHeaders);
            }

            String accessControlRequestMethod = request.headers("Access-Control-Request-Method");
            if (accessControlRequestMethod != null) {
                response.header("Access-Control-Allow-Methods", accessControlRequestMethod);
            }

            return "OK";
        });
    }
}
