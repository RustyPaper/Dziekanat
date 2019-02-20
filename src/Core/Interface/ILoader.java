package Core.Interface;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface ILoader {
    public void load(ResultSet resultSet) throws SQLException;
}
