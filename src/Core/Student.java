package Core;

import Core.DBConnect.Connect;
import Core.Interface.IConnect;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Student implements IConnect {

    private String imie;
    private String nazwisko;
    private String adresZamieszkania;
    private String email;
    private long pesel;
    private int numerTelefonu;
    private int numerIndeksu;

    public  Student(){

    }

    public Student(String imie,
                   String nazwisko,
                   String adresZamieszkania,
                   String email,
                   long pesel,
                   int numerTelefonu) {
        this.imie = imie;
        this.nazwisko = nazwisko;
        this.adresZamieszkania = adresZamieszkania;
        this.email = email;
        this.pesel = pesel;
        this.numerTelefonu = numerTelefonu;
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

    public int getNumerTelefonu() {
        return numerTelefonu;
    }

    public void setNumerTelefonu(int numerTelefonu) {
        this.numerTelefonu = numerTelefonu;
    }

    public int getNumerIndeksu() {
        return numerIndeksu;
    }

    public void setNumerIndeksu(int numerIndeksu) {
        this.numerIndeksu = numerIndeksu;
    }

    @Override
    public void load(Connect connect, ResultSet resultSet) throws SQLException {
        this.setImie(resultSet.getString(2));
        this.setNazwisko(resultSet.getString(3));
        this.setAdresZamieszkania(resultSet.getString(4));
        this.setPesel(Long.parseLong(resultSet.getString(5)));
        this.setEmail(resultSet.getString(6));
        this.setNumerTelefonu(Integer.parseInt(resultSet.getString(7)));
        this.setNumerIndeksu(Integer.parseInt(resultSet.getString(1)));
    }

    @Override
    public String save() {
        StringBuilder queryWartosci = new StringBuilder();
        queryWartosci.append("(imie, nazwisko, adres_zamieszkania, pesel, email, numer_telefonu, numer_indeksu) VALUES(");
        queryWartosci.append("'"+this.getImie()+"',");
        queryWartosci.append("'"+this.getNazwisko()+"',");
        queryWartosci.append("'"+this.getAdresZamieszkania()+"',");
        queryWartosci.append("'"+this.getPesel()+"',");
        queryWartosci.append("'"+this.getEmail()+"',");
        queryWartosci.append("'"+this.getNumerTelefonu()+"',");
        queryWartosci.append("'"+this.getNumerIndeksu()+"',");
        queryWartosci.append(")");

        return queryWartosci.toString();
    }
}
