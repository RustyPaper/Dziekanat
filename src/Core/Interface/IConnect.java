package Core.Interface;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface IConnect {
    void load(ResultSet resultSet) throws SQLException;
    String save();
}
