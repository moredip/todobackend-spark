package plumbing;

import db.TodoDAO;
import domain.Todo;
import representations.NewTodo;

import java.util.List;

public class TodoController {
    final private TodoDAO dao;

    public TodoController(TodoDAO dao){
        this.dao = dao;
    }

    public Todo createTodo(NewTodo newTodo){
        return dao.createTodo(newTodo.getTitle());
    }

    public List<Todo> getAllTodos(){
        return dao.findAll();
    }

    public String deleteAllTodos() {
        dao.deleteAll();
        return "[]";
    }

    public domain.Todo getTodo(Integer todoId) {
        return dao.findById(todoId);
    }
}
