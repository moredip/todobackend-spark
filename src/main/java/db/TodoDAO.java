package db;

import domain.Todo;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

@RegisterMapper(TodoMapper.class)
public interface TodoDAO {
    @SqlQuery("insert into todos (title,completed,item_order) values (:title, false, 0) RETURNING *")
    Todo createTodo(@Bind("title") String title);
}

