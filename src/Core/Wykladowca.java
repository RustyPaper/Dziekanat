package Core;

public class Wykladowca {
        private String imie;
        private String nazwisko;
        private String adresZamieszkania;
        private String email;
        private long pesel;
        private float zarobki;

    public Wykladowca(String imie,
                      String nazwisko,
                      String adresZamieszkania,
                      String email,
                      long pesel,
                      float zarobki) {
        this.imie = imie;
        this.nazwisko = nazwisko;
        this.adresZamieszkania = adresZamieszkania;
        this.email = email;
        this.pesel = pesel;
        this.zarobki = zarobki;
    }

    public String getImie() {
        return imie;
    }

    public void setImie(String imie) {
        this.imie = imie;
    }

    public String getNazwisko() {
        return nazwisko;
    }

    public void setNazwisko(String nazwisko) {
        this.nazwisko = nazwisko;
    }

    public String getAdresZamieszkania() {
        return adresZamieszkania;
    }

    public void setAdresZamieszkania(String adresZamieszkania) {
        this.adresZamieszkania = adresZamieszkania;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public long getPesel() {
        return pesel;
    }

    public void setPesel(long pesel) {
        this.pesel = pesel;
    }

    public float getZarobki() {
        return zarobki;
    }

    public void setZarobki(float zarobki) {
        this.zarobki = zarobki;
    }
}
