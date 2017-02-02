package db;

import domain.Todo;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@RegisterMapper(TodoMapper.class)
public interface TodoDAO {
    @SqlQuery("INSERT INTO todos (title,completed,item_order) VALUES (:title, false, 0) RETURNING *")
    Todo createTodo(@Bind("title") String title);

    @SqlQuery("SELECT * FROM todos")
    List<Todo> findAll();
}

