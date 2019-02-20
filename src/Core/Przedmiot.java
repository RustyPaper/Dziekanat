package Core;

import Core.Interface.IConnect;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Przedmiot implements IConnect {

    private String nazwaPrzedmiotu;
    private int wykladowca;
    private RodzajePrzedmiotow rodzaj;
    private Map<Integer, List<Double>> ocenyCzastkowe;

    public Przedmiot(String nazwaPrzedmiotu, int wykladowca, RodzajePrzedmiotow rodzaj) {
        this.nazwaPrzedmiotu = nazwaPrzedmiotu;
        this.wykladowca = wykladowca;
        this.rodzaj = rodzaj;
        this.ocenyCzastkowe = new HashMap<>();

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

    public void dodajOcene(int numerIndeksu,  double ocena) {

        if (!ocenyCzastkowe.containsKey(numerIndeksu)){
            ocenyCzastkowe.put(numerIndeksu, new ArrayList<>());
        }

        ocenyCzastkowe.get(numerIndeksu).add(ocena);
    }

    public void zamienOcene(int numerIndeksu, int numerOceny, double ocena) throws Exception {

        if (!ocenyCzastkowe.containsKey(numerIndeksu)){
            throw new Exception("Nie ma takiego studenta.");
        }

        ocenyCzastkowe.get(numerIndeksu).set(numerOceny, ocena);
    }

    public List<Double> getOceny(int numerIndeksu) throws Exception {

        if (!ocenyCzastkowe.containsKey(numerIndeksu)){
            throw new Exception("Nie ma takiego studenta.");
        }

        return ocenyCzastkowe.get(numerIndeksu);
    }

    @Override
    public void load(ResultSet resultSet) throws SQLException {
        this.setNazwaPrzedmiotu(resultSet.getString(2));
        this.setRodzaj(RodzajePrzedmiotow.valueOf(resultSet.getString(3)));
        this.setWykladowca(Integer.parseInt(resultSet.getString(4)));
    }

    @Override
    public String save() {
        StringBuilder queryWartosci = new StringBuilder();
        queryWartosci.append("(");
        queryWartosci.append("'"+this.getNazwaPrzedmiotu()+"',");
        queryWartosci.append("'"+this.getRodzaj()+"',");
        queryWartosci.append("'"+this.getWykladowca()+"',");
        queryWartosci.append(")");

        return queryWartosci.toString();
    }
}
