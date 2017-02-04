package db;

import domain.Todo;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

import java.util.List;

@RegisterMapper(TodoMapper.class)
public interface TodoDAO {
    @SqlQuery("INSERT INTO todos (title,completed,item_order) VALUES (:title, false, 0) RETURNING *")
    Todo createTodo(@Bind("title") String title);

    @SqlQuery("SELECT * FROM todos")
    List<Todo> findAll();

    @SqlQuery("SELECT * FROM todos WHERE id = :id LIMIT 1")
    Todo findById(@Bind("id") Integer id);

    @SqlUpdate("DELETE FROM todos")
    void deleteAll();

    @SqlQuery(
            "UPDATE todos " +
            "SET title=COALESCE(:title,title), completed=COALESCE(:completed,completed) " +
            "WHERE id = :id " +
            "RETURNING *")
    Todo updateTodo(@Bind("id") Integer id, @Bind("title") String title, @Bind("completed")Boolean completed);
}

