package db;

import domain.Todo;
import org.junit.BeforeClass;
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

        Todo refetchedTodo = dao.findById(createdTodo.getId());
        assertEquals("my-new-todo",refetchedTodo.getTitle());
        assertEquals(false,refetchedTodo.getCompleted());
        assertEquals((Integer)0,refetchedTodo.getOrder());
        assertNotNull(refetchedTodo.getId());
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

    @Test
    public void deletingAllTodos(){
        dao.createTodo("blah");
        assertThat(dao.findAll().size(), is(greaterThan(0)));
        dao.deleteAll();
        assertThat(dao.findAll().size(), is(equalTo(0)));
    }

    @Test
    public void updateJustTodoTitle(){
        Todo initialTodo = dao.createTodo("original-title");
        Integer todoId = initialTodo.getId();

        dao.updateTodo(todoId,"new-title", null);
        assertThat(dao.findById(todoId).getTitle(), is("new-title"));
        assertThat(dao.findById(todoId).getCompleted(), is(false));
    }

    @Test
    public void updateTodoTitleAndCompletedness(){
        Todo initialTodo = dao.createTodo("original-title");
        Integer todoId = initialTodo.getId();

        Todo updatedTodo = dao.updateTodo(todoId, "new-title", true);
        Todo refetchedTodo = dao.findById(todoId);

        assertEquals(updatedTodo,refetchedTodo);

        assertThat(refetchedTodo.getTitle(), is("new-title"));
        assertThat(refetchedTodo.getCompleted(), is(true));
    }

    @Test
    public void updateJustTodoCompletedness(){
        Todo initialTodo = dao.createTodo("original-title");
        Integer todoId = initialTodo.getId();

        dao.updateTodo(todoId,null,true);

        Todo refetchedTodo = dao.findById(todoId);
        assertThat(refetchedTodo.getTitle(), is("original-title"));
        assertThat(dao.findById(todoId).getCompleted(), is(true));
    }
}