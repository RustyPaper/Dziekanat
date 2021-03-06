package Core;

import Core.DBConnect.Connect;
import Core.DBConnect.NazwyTablic;
import Core.Interface.IListaStudentow;
import Core.Interface.IConnect;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class KierunekStudiow implements IListaStudentow, IConnect {

    private int idKierunku;
    private String nazwaKierunku;
    private int progWejsciowy;
    private List<Integer> listaStudentow;
    private ArrayList<Integer> listaPrzedmiotow;
    private Map<Integer, List<Przedmiot>> listaOcen;
    private TypStudiow typStudiow;

    public Map<Integer, List<Przedmiot>> getListaOcen() {
        return listaOcen;
    }

    public void setListaOcen(Map<Integer, List<Przedmiot>> listaOcen) {
        this.listaOcen = listaOcen;
    }

    public KierunekStudiow(){

        this.listaStudentow = new ArrayList<>();
        this.listaPrzedmiotow = new ArrayList<>();
        this.listaOcen = new HashMap<>();
    }

    public KierunekStudiow(String nazwaKierunku,
                           int progWejsciowy,
                           TypStudiow typStudiow) {
        this.nazwaKierunku = nazwaKierunku;
        this.progWejsciowy = progWejsciowy;
        this.listaStudentow = new ArrayList<>();
        this.listaPrzedmiotow = new ArrayList<>();
        this.listaOcen = new HashMap<>();
        this.typStudiow = typStudiow;
    }

    public int getIdKierunku() {
        return idKierunku;
    }

    public void setIdKierunku(int idKierunku) {
        this.idKierunku = idKierunku;
    }

    public void dodajPrzedmiot(int idPrzedmiotu) {

        this.listaPrzedmiotow.add(idPrzedmiotu);

    }


    public String getNazwaKierunku() {
        return nazwaKierunku;
    }

    public void setNazwaKierunku(String nazwaKierunku) {
        this.nazwaKierunku = nazwaKierunku;
    }

    public int getProgWejsciowy() {
        return progWejsciowy;
    }

    public void setProgWejsciowy(int progWejsciowy) {
        this.progWejsciowy = progWejsciowy;
    }

    public ArrayList<Integer> getListaPrzedmiotow() {
        return listaPrzedmiotow;
    }

    public TypStudiow getTypStudiow() {
        return typStudiow;
    }

    public void setTypStudiow(TypStudiow typStudiow) {
        this.typStudiow = typStudiow;
    }

    @Override
    public List<Integer> getListaStudentow() {
        return listaStudentow;
    }


    @Override
    public void load(Connect connect, ResultSet resultSet) throws SQLException {
        this.setIdKierunku(resultSet.getInt(6));
        this.setNazwaKierunku(resultSet.getString(1));
        this.setProgWejsciowy(resultSet.getInt(2));

        String przedmioty = resultSet.getString(4);

        if(przedmioty != null) {
            if(przedmioty.length() > 0) {
                String[] idPrzedmiotow = przedmioty.split(";");
                for (String idPrzedmiotu : idPrzedmiotow)
                    this.listaPrzedmiotow.add(Integer.parseInt(idPrzedmiotu));
            }
        }
        String studenci = resultSet.getString(3);
        if(studenci != null) {
            if(studenci.length() > 0) {
                String[] indeksyStudentow = studenci.split(";");
                Przedmiot przedmiot;
                for (String indeks : indeksyStudentow) {
                    this.listaStudentow.add(Integer.parseInt(indeks));
                    this.listaOcen.put(Integer.parseInt(indeks), new ArrayList<>());
                    for (int idPrzedmiotu : this.listaPrzedmiotow) {
                        przedmiot = new Przedmiot();
                        String[][] listaOcenZBazy = connect.getColumn("oceny", new String[]{"oceny"}, "WHERE numer_indeksu = " + indeks + " AND id_przedmiotu = " + idPrzedmiotu);

                        connect.laduj(przedmiot, NazwyTablic.PRZEDMIOTY.getNazwa(), "WHERE id = " + idPrzedmiotu);
                        if (listaOcenZBazy.length > 0) {
                            przedmiot.setListaOcen(listaOcenZBazy[0][0].split(";"));

                            this.listaOcen.get(Integer.parseInt(indeks)).add(przedmiot);
                        }
                    }
                }
            }
        }
    }

    @Override
    public String save() {
        StringBuilder queryWartosci = new StringBuilder();
        queryWartosci.append("(nazwa_kierunku, prog_wejsciowy, lista_studentow, lista_przedmiotow, typ_studiow) VALUES(");
        queryWartosci.append("'"+this.getNazwaKierunku()+"',");
        queryWartosci.append("'"+this.getProgWejsciowy()+"',");

            StringBuilder listaStudentowString = new StringBuilder();
            listaStudentowString.append("'");
            for (int indeks : getListaStudentow()) {
                listaStudentowString.append(indeks).append(";");
            }
            queryWartosci.append(listaStudentowString.toString());
            queryWartosci.append("',");

            queryWartosci.append("'");
            StringBuilder listaPRzedmiotowString = new StringBuilder();
            for(int idPrzedmiotu: this.listaPrzedmiotow){
                listaPRzedmiotowString.append(idPrzedmiotu).append(";");
            }
            queryWartosci.append(listaPRzedmiotowString.toString());
            queryWartosci.append("',");

            queryWartosci.append("'").append(this.typStudiow.toString()).append("'");
            queryWartosci.append(")");

        return queryWartosci.toString();
    }
}

