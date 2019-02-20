package Core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Przedmiot {

    private String nazwaPrzedmiotu;
    private Wykladowca wykladowca;
    private RodzajePrzedmiotow rodzaj;
    private Map<Integer, List<Double>> ocenyCzastkowe;

    public Przedmiot(String nazwaPrzedmiotu, Wykladowca wykladowca, RodzajePrzedmiotow rodzaj) {
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

    public Wykladowca getWykladowca() {
        return wykladowca;
    }

    public void setWykladowca(Wykladowca wykladowca) {
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
}
