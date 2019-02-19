package Core;

import java.util.Map;

public class Rocznik {

    private int numerRocznika;
    private Map<String, KierunekStudiow> listaKierunkow;

    public Rocznik(int numerRocznika,
                   Map<String, KierunekStudiow> listaKierunkow) {
        this.numerRocznika = numerRocznika;
        this.listaKierunkow = listaKierunkow;
    }

    public int getNumerRocznika() {
        return numerRocznika;
    }

    public void setNumerRocznika(int numerRocznika) {
        this.numerRocznika = numerRocznika;
    }

    public Map<String, KierunekStudiow> getListaKierunkow() {
        return listaKierunkow;
    }

    public void setListaKierunkow(Map<String, KierunekStudiow> listaKierunkow) {
        this.listaKierunkow = listaKierunkow;
    }
}
