package plumbing;

import db.TodoDAO;
import representations.NewTodo;
import domain.Todo;

public class TodoController {
    final private TodoDAO dao;

    public TodoController(TodoDAO dao){
        this.dao = dao;
    }

    public Todo createTodo(NewTodo newTodo){
        return dao.createTodo(newTodo.getTitle());
    }
}
