package db;

import domain.Todo;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import plumbing.Database;

import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

public class TodoDAOTest {
    static TodoDAO dao;

    @BeforeClass
    public static void beforeClass() {
        Database db = Database.forIntegrationTesting().nukeAndRecreate();
        dao = db.getDao(TodoDAO.class);
    }

    @Test
    public void createNewTodoReturnsSaneLookingEntity(){
        Todo createdTodo = dao.createTodo("my-new-todo");
        assertEquals("my-new-todo",createdTodo.getTitle());
        assertEquals(false,createdTodo.getCompleted());
        assertEquals((Integer)0,createdTodo.getOrder());
        assertNotNull(createdTodo.getId());
    }

    @Test
    public void creatingSomeTodosThenReadingThemBack(){
        dao.createTodo("todo-the-first");
        dao.createTodo("todo-el-segundo");

        List<Todo> allTodos = dao.findAll();
        assertThat(allTodos.size(), is(greaterThanOrEqualTo(2)));

        List<String> allTitles = allTodos.stream().map(Todo::getTitle).collect(Collectors.toList());
        assertThat( allTitles, hasItem("todo-the-first"));
        assertThat( allTitles, hasItem("todo-el-segundo"));
    }

    @Test
    public void findingATodoWhichExists(){
        Todo createdTodo = dao.createTodo("blah");
        Todo foundTodo = dao.findById(createdTodo.getId());

        assertThat(foundTodo, is(equalTo(createdTodo)));
    }

    @Test
    public void findingATodoWhichDoesNotExist(){
        Todo foundTodo = dao.findById(1421312412);
        assertThat(foundTodo, is(nullValue()));
    }
}