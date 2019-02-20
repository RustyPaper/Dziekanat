package Core.Interface;

import Core.DBConnect.Connect;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface IConnect {
    void load(Connect connect, ResultSet resultSet) throws SQLException;
    String save();
}
