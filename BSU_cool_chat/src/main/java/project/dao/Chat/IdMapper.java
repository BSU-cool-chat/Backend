package project.dao.Chat;


import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

class IdMapper implements RowMapper<Integer> {
    @Override
    public Integer mapRow(ResultSet resultSet, int rowNumber) throws SQLException {
        return resultSet.getInt("id");
    }
}
