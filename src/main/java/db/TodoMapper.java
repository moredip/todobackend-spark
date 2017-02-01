package db;

import domain.Todo;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TodoMapper implements ResultSetMapper<Todo> {

    @Override
    public Todo map(int i, ResultSet rs, StatementContext statementContext) throws SQLException {
        return Todo.of(
                rs.getInt("id"),
                rs.getString("title"),
                rs.getBoolean("completed"),
                rs.getInt("item_order")
        );
    }
}
