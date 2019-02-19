package Core;

import java.util.ArrayList;
import java.util.Map;

public class KierunekStudiow {

    private String nazwaKierunku;
    private int progWejsciowy;
    private Map<Integer, Student> listaStudentow;
    private ArrayList<String> listaPrzedmiotow;
    private Map<Integer, Map<String, Double>> listaOcen;
    private Map<Long, Wykladowca> listaWykladowcow;

    public KierunekStudiow(String nazwaKierunku,
                           int progWejsciowy,
                           Map<Integer, Student> listaStudentow,
                           ArrayList<String> listaPrzedmiotow,
                           Map<Long, Wykladowca> listaWykladowcow,
                           Map<Integer, Map<String, Double>> listaOcen) {
        this.nazwaKierunku = nazwaKierunku;
        this.progWejsciowy = progWejsciowy;
        this.listaStudentow = listaStudentow;
        this.listaPrzedmiotow = listaPrzedmiotow;
        this.listaWykladowcow = listaWykladowcow;
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

    public Map<Integer, Student> getListaStudentow() {
        return listaStudentow;
    }

    public void setListaStudentow(Map<Integer, Student> listaStudentow) {
        this.listaStudentow = listaStudentow;
    }

    public ArrayList<String> getListaPrzedmiotow() {
        return listaPrzedmiotow;
    }

    public void setListaPrzedmiotow(ArrayList<String> listaPrzedmiotow) {
        this.listaPrzedmiotow = listaPrzedmiotow;
    }

    public Map<Long, Wykladowca> getListaWykladowcow() {
        return listaWykladowcow;
    }

    public void setListaWykladowcow(Map<Long, Wykladowca> listaWykladowcow) {
        this.listaWykladowcow = listaWykladowcow;
    }

    public Map<Integer, Map<String, Double>> getListaOcen() {
        return listaOcen;
    }

    public void setListaOcen(Map<Integer, Map<String, Double>> listaOcen) {
        this.listaOcen = listaOcen;
    }
}

