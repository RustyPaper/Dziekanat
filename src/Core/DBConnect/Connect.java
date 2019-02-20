package Core.DBConnect;


import Core.Interface.ILoader;

import java.sql.*;
import java.util.Properties;

public class Connect {

    private Connection conn;

    public Connect() {
    String url = "jdbc:postgresql://ec2-54-75-230-41.eu-west-1.compute.amazonaws.com/d8jo80j7ro8qdr";
    Properties props = new Properties();
    props.setProperty("user","tbswqeuckwbbbf");
    props.setProperty("password","0f7a37c062e7ff8a7b2ff730406dab2abf70cc29f293fb2a6aadd96330351f60");
   // props.setProperty("ssl","true");
        try {
            conn = DriverManager.getConnection(url, props);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void laduj(ILoader loaderInterface, String nazwaTablicy, String filtr){

        PreparedStatement st = null;
        try {
            st = conn.prepareStatement("SELECT * FROM "+nazwaTablicy+" "+filtr+";");
            ResultSet resultSet = st.executeQuery();
            resultSet.next();
            loaderInterface.load(resultSet);
            st.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void laduj(ILoader loaderInterface, int nazwaTablicy, String filtr){
        this.laduj(loaderInterface,"rocznik_"+String.valueOf(nazwaTablicy), filtr);
    }
}
