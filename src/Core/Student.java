package Core;

import Core.DBConnect.Connect;
import Core.Interface.ILoader;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Student implements ILoader {

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
                   int numerTelefonu,
                   int numerIndeksu) {
        this.imie = imie;
        this.nazwisko = nazwisko;
        this.adresZamieszkania = adresZamieszkania;
        this.email = email;
        this.pesel = pesel;
        this.numerTelefonu = numerTelefonu;
        this.numerIndeksu = numerIndeksu;
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
    public void load(ResultSet resultSet) throws SQLException {
        this.setImie(resultSet.getString(2));
        this.setNazwisko(resultSet.getString(3));
        this.setAdresZamieszkania(resultSet.getString(4));
        this.setPesel(Long.parseLong(resultSet.getString(5)));
        this.setEmail(resultSet.getString(6));
        this.setNumerTelefonu(Integer.parseInt(resultSet.getString(7)));
        this.setNumerIndeksu(Integer.parseInt(resultSet.getString(8)));
    }
}
