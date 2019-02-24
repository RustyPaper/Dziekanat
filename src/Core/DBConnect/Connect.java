package Core.DBConnect;


import Core.Interface.IConnect;

import java.sql.*;
import java.util.ArrayList;
import java.util.Properties;

public class Connect {

    private Connection conn;
    ResultSet resultSet;
    PreparedStatement st;

    public Connect() {
    String url = "jdbc:postgresql://ec2-54-75-230-41.eu-west-1.compute.amazonaws.com/d8jo80j7ro8qdr";
    Properties props = new Properties();
    props.setProperty("user","tbswqeuckwbbbf");
    props.setProperty("password","0f7a37c062e7ff8a7b2ff730406dab2abf70cc29f293fb2a6aadd96330351f60");
        try {
            conn = DriverManager.getConnection(url, props);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private void loadFromDB (String nazwaTablicy, String[] nazwyKolumn, String filtr){
        String kolumny = "*";
        try {
            if (nazwyKolumn != null){
                kolumny = String.join(", ", nazwyKolumn);
            }
            String query = "SELECT "+kolumny+" FROM "+nazwaTablicy+" "+filtr+";";
            this.st = conn.prepareStatement(query);
            this.resultSet = this.st.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void laduj(IConnect loaderInterface, String nazwaTablicy, String filtr){
        this.loadFromDB(nazwaTablicy, null, filtr);

        try {
            this.resultSet.next();
            loaderInterface.load(this,this.resultSet);
            this.st.close();
        } catch (SQLException e) {
            //e.printStackTrace();
            System.out.println("!!! ZLE WPROWADZONE DANE !!! SPROBOJ PONOWNIE !!!");
        }

    }

    public void laduj(IConnect loaderInterface, int nazwaTablicy, String filtr){
        this.laduj(loaderInterface,"rocznik_"+String.valueOf(nazwaTablicy), filtr);
    }

    public String[][] getColumn(String nazwaTablicy, String[] nazwaKolumny, String filtr){
        this.loadFromDB(nazwaTablicy, nazwaKolumny, filtr);
        ArrayList<String[]> wynik = new ArrayList<>();

        try {
            while (this.resultSet.next()) {

                String[] wartosciKolumn = new String[nazwaKolumny.length];

                for(int index=0; index<nazwaKolumny.length;++index)
                    wartosciKolumn[index] = this.resultSet.getString(index+1);

                wynik.add(wartosciKolumn);

            }
            this.st.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        String[][] wynikArr = new String[wynik.size()][];
        wynik.toArray(wynikArr);
        return wynikArr;
    }

    public int save(IConnect inst, String nazwaTab){
        String query = "INSERT INTO "+nazwaTab+" "+inst.save()+" RETURNING id;";
        try {
            this.st = conn.prepareStatement(query);
            this.resultSet = this.st.executeQuery();
            resultSet.next();
            return resultSet.getInt(1);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return -1;
    }

    public void createTable(String name, String[] kolumy){
        String kol = String.join(",",kolumy);
        String query = "CREATE TABLE "+name+" (id SERIAL PRIMARY KEY,"+kol+" );";
        try {
            this.st = conn.prepareStatement(query);
            this.st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
