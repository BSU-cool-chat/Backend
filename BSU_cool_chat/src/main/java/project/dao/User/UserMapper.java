package project.dao.User;

import org.springframework.jdbc.core.RowMapper;
import project.models.User;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserMapper implements RowMapper<User> {
    @Override
    public User mapRow(ResultSet resultSet, int rowNumber) throws SQLException {
        return new User(resultSet.getInt("id"),
                resultSet.getString("login"),
                resultSet.getString("password"),
                resultSet.getString("name"),
                resultSet.getString("sex"),
                resultSet.getInt("age"),
                resultSet.getString("additional_info"));
    }
}
