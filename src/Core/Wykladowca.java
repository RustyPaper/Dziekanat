package Core;

import Core.Interface.IConnect;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Wykladowca implements IConnect {
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

    @Override
    public void load(ResultSet resultSet) throws SQLException {
        this.setImie(resultSet.getString(1));
        this.setNazwisko(resultSet.getString(2));
        this.setAdresZamieszkania(resultSet.getString(3));
        this.setEmail(resultSet.getString(4));
        this.setPesel(Long.parseLong(resultSet.getString(5)));
        this.setZarobki(Float.parseFloat(resultSet.getString(6)));
    }

    @Override
    public String save() {
        StringBuilder queryWartosci = new StringBuilder();
        queryWartosci.append("(");
        queryWartosci.append("'"+this.getImie()+"',");
        queryWartosci.append("'"+this.getNazwisko()+"',");
        queryWartosci.append("'"+this.getAdresZamieszkania()+"',");
        queryWartosci.append("'"+this.getEmail()+"',");
        queryWartosci.append("'"+this.getPesel()+"',");
        queryWartosci.append("'"+this.getZarobki()+"',");
        queryWartosci.append(")");

        return queryWartosci.toString();
    }
}
