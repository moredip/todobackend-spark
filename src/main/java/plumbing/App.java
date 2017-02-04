package plumbing;

import com.google.gson.Gson;
import db.TodoDAO;
import domain.Todo;
import representations.NewTodo;
import representations.TodoUpdate;

import static plumbing.SparkRequestContext.inContextOf;
import static spark.Spark.*;

public class App {
    public static void initServer(int port) throws Exception {
        port(port);

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
            return inContextOf(request).represent(todo);
        });

        get("/todos", "application/json", (request, response) -> inContextOf(request).represent(controller.getAllTodos()));

        delete("/todos", "application/json", (request, response) -> controller.deleteAllTodos());

        get("/todos/:id", "application/json", (request, response) -> {
            Integer todoId = Integer.parseInt(request.params("id"));
            return inContextOf(request).represent(controller.getTodo(todoId));
        });

        patch("/todos/:id", "application/json", (request, response) -> {
            Integer todoId = Integer.parseInt(request.params("id"));
            TodoUpdate update = gson.fromJson(request.body(), TodoUpdate.class);
            return inContextOf(request).represent(controller.patchTodo(todoId,update));
        });

        delete("/todos/:id", "application/json", (request, response) -> {
            Integer todoId = Integer.parseInt(request.params("id"));
            return "";
        });
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
