package Core;

import Core.DBConnect.Connect;
import Core.Interface.IConnect;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Przedmiot implements IConnect {

    private int id;
    private String nazwaPrzedmiotu;
    private int wykladowca;
    private RodzajePrzedmiotow rodzaj;
    private List<Double> ocenyCzastkowe;

    public Przedmiot(){
        this.ocenyCzastkowe = new ArrayList<>();
    }
    public Przedmiot(String nazwaPrzedmiotu, int wykladowca, RodzajePrzedmiotow rodzaj) {
        this.nazwaPrzedmiotu = nazwaPrzedmiotu;
        this.wykladowca = wykladowca;
        this.rodzaj = rodzaj;
        this.ocenyCzastkowe = new ArrayList<>();

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNazwaPrzedmiotu() {
        return nazwaPrzedmiotu;
    }

    public void setNazwaPrzedmiotu(String nazwaPrzedmiotu) {
        this.nazwaPrzedmiotu = nazwaPrzedmiotu;
    }

    public int getWykladowca() {
        return wykladowca;
    }

    public void setWykladowca(int wykladowca) {
        this.wykladowca = wykladowca;
    }

    public RodzajePrzedmiotow getRodzaj() {
        return rodzaj;
    }

    public void setRodzaj(RodzajePrzedmiotow rodzaj) {
        this.rodzaj = rodzaj;
    }

    public void setListaOcen(String[] oceny){
        for(String ocena : oceny){
            this.ocenyCzastkowe.add(Double.parseDouble(ocena));
        }
    }

    public void dodajOcene(double ocena) {

        ocenyCzastkowe.add(ocena);
    }

    public void zamienOcene(int numerIndeksu, int numerOceny, double ocena) throws Exception {

        ocenyCzastkowe.set(numerOceny, ocena);
    }

    public List<Double> getOceny() {

        return ocenyCzastkowe;
    }

    @Override
    public void load(Connect connect, ResultSet resultSet) throws SQLException {
        this.setId(resultSet.getInt(1));
        this.setNazwaPrzedmiotu(resultSet.getString(2));
        this.setRodzaj(RodzajePrzedmiotow.valueOf(resultSet.getString(3)));
        this.setWykladowca(Integer.parseInt(resultSet.getString(4)));
    }

    @Override
    public String save() {
        StringBuilder queryWartosci = new StringBuilder();
        queryWartosci.append("(nazwa_przedmiotu, rodzaj, wykladowca) VALUES(");
        queryWartosci.append("'"+this.getNazwaPrzedmiotu()+"',");
        queryWartosci.append("'"+this.getRodzaj()+"',");
        queryWartosci.append("'"+this.getWykladowca()+"'");
        queryWartosci.append(")");

        return queryWartosci.toString();
    }
}
