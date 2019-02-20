package Core;

import Core.DBConnect.Connect;
import Core.DBConnect.NazwyTablic;
import Core.Interface.IListaStudentow;
import Core.Interface.ILoader;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class KierunekStudiow implements IListaStudentow, ILoader {

    private String nazwaKierunku;
    private int progWejsciowy;
    private List<Integer> listaStudentow;
    private ArrayList<Przedmiot> listaPrzedmiotow;
    private TypStudiow typStudiow;

    public KierunekStudiow(){

        this.listaStudentow = new ArrayList<>();
        this.listaPrzedmiotow = new ArrayList<>();

    }

    public KierunekStudiow(String nazwaKierunku,
                           int progWejsciowy,
                           TypStudiow typStudiow) {
        this.nazwaKierunku = nazwaKierunku;
        this.progWejsciowy = progWejsciowy;
        this.listaStudentow = new ArrayList<>();
        this.listaPrzedmiotow = new ArrayList<>();
        this.typStudiow = typStudiow;
    }

    public void dodajPrzedmiot(Przedmiot przedmiot) {

        this.listaPrzedmiotow.add(przedmiot);

    }

    public List<Przedmiot> wyszukajPrzedmiot(String nazwa){

        ArrayList<Przedmiot> wynikWyszukiwania = new ArrayList<>();
        for (Przedmiot przedmiot: this.listaPrzedmiotow){
            if (przedmiot.getNazwaPrzedmiotu() == nazwa){
                wynikWyszukiwania.add(przedmiot);
            }
        }

        return wynikWyszukiwania;
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

    public ArrayList<Przedmiot> getListaPrzedmiotow() {
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
    public void load(ResultSet resultSet) throws SQLException {
        this.setNazwaKierunku(resultSet.getString(2));
        this.setProgWejsciowy(resultSet.getInt(3));
        String[] indeksyStudentow = resultSet.getString(4).split(";");
        for (String indeks: indeksyStudentow){
            this.listaStudentow.add(Integer.parseInt(indeks));
        }
    }
}

