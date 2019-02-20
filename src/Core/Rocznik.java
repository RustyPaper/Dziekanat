package Core;

import Core.Interface.IConnect;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Rocznik implements IConnect {

    private int numerRocznika;
    private List<Integer> listaKierunkow;

    public Rocznik(){
        this.listaKierunkow = new ArrayList<>();
    }

    public Rocznik(int numerRocznika) {
        this.numerRocznika = numerRocznika;
        this.listaKierunkow = new ArrayList<>();
    }

    public int getNumerRocznika() {
        return numerRocznika;
    }

    public void setNumerRocznika(int numerRocznika) {
        this.numerRocznika = numerRocznika;
    }

    public List<Integer> getListaKierunkow() {
        return listaKierunkow;
    }

    public void dodajKierunek(Integer idKierunku){
        listaKierunkow.add(idKierunku);
    }

    public void usunKierunek(Integer idKierunku){
        listaKierunkow.remove(idKierunku);
    }

    @Override
    public void load(ResultSet resultSet) throws SQLException {
        this.setNumerRocznika(resultSet.getInt(2));
        String[] idKierunkow = resultSet.getString(3).split(";");
        for (String id: idKierunkow){
            this.listaKierunkow.add(Integer.parseInt(id));
        }
    }

    @Override
    public String save() {
        StringBuilder queryWartosci = new StringBuilder();
        queryWartosci.append("(");
        queryWartosci.append("'"+this.getNumerRocznika()+"',");
        for (int indeks: getListaKierunkow()){
            queryWartosci.append("'"+indeks+"',");
        }
        queryWartosci.append(")");

        return queryWartosci.toString();
    }
}
