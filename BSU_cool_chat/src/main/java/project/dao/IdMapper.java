package project.dao;


import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class IdMapper implements RowMapper<Integer> {
    @Override
    public Integer mapRow(ResultSet resultSet, int rowNumber) throws SQLException {
        return resultSet.getInt("id");
    }
}
