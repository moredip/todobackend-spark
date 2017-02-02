package db;

import domain.Todo;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.skife.jdbi.v2.DBI;
import plumbing.Database;

import javax.xml.crypto.Data;

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
}