package plumbing;

import db.TodoDAO;
import representations.NewTodo;
import domain.Todo;

import java.util.List;

public class TodoController {
    final private TodoDAO dao;

    public TodoController(TodoDAO dao){
        this.dao = dao;
    }

    public Todo createTodo(NewTodo newTodo){
        return dao.createTodo(newTodo.getTitle());
    }

    public List<Todo> allTodos(){
        return dao.findAll();
    }
}
