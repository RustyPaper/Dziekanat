package Core;

import Core.DBConnect.Connect;
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
    public void load(Connect connect, ResultSet resultSet) throws SQLException {
        this.setNumerRocznika(resultSet.getInt(2));
        String idKierunkow = resultSet.getString(3);
        if(idKierunkow != null)
            for (String id: idKierunkow.split(";")){
                this.listaKierunkow.add(Integer.parseInt(id));
            }
    }

    @Override
    public String save() {
        StringBuilder queryWartosci = new StringBuilder();
        queryWartosci.append("(numer_rocznika, lista_kierunkow) VALUES(");
        queryWartosci.append(this.getNumerRocznika()).append(",");

        StringBuilder listaKierunkow = new StringBuilder();
        if(getListaKierunkow().size() > 0)
             for (int indeks: getListaKierunkow()){
                listaKierunkow.append(indeks).append(";");
             }
        else
            listaKierunkow.append("''");

        queryWartosci.append(listaKierunkow.toString());
        queryWartosci.append(")");

        return queryWartosci.toString();
    }
}
