package plumbing;

import db.TodoDAO;
import domain.Todo;
import representations.NewTodo;
import representations.TodoUpdate;

import java.util.List;

public class TodoController {
    final private TodoDAO dao;

    public TodoController(TodoDAO dao){
        this.dao = dao;
    }

    public Todo createTodo(NewTodo newTodo){
        return dao.createTodo(newTodo.getTitle(),newTodo.getOrder());
    }

    public List<Todo> getAllTodos(){
        return dao.findAll();
    }

    public String deleteAllTodos() {
        dao.deleteAll();
        return "[]";
    }

    public Todo getTodo(Integer todoId) {
        return dao.findById(todoId);
    }

    public Todo patchTodo(Integer todoId, TodoUpdate update) {
        return dao.updateTodo(todoId,update.getTitle(), update.isCompleted(), update.getOrder());
    }

    public void deleteTodo(Integer todoId) {
        dao.deleteById(todoId);
    }
}
