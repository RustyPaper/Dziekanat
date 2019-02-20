package Core;

import Core.DBConnect.Connect;
import Core.DBConnect.NazwyTablic;

public class Dziekanat {

    private Student student;
    private Wykladowca wykladowca;
    private KierunekStudiow kierunekStudiow;
    private Rocznik rocznik;
    private Przedmiot przedmiot;
    private Connect connect;

    public Dziekanat(){
        connect = new Connect();
    }

    public void stworzStudenta(String imie,
                              String nazwisko,
                              String adresZamieszkania,
                              String email,
                              long pesel,
                              int numerTelefonu,
                              int numerIndeksu){
        student = new Student(imie,
                              nazwisko,
                              adresZamieszkania,
                              email,
                              pesel,
                              numerTelefonu,
                              numerIndeksu);
    }

    public void stworzWykladowce(String imie,
                                 String nazwisko,
                                 String adresZamieszkania,
                                 String email,
                                 long pesel,
                                 float zarobki){
        wykladowca = new Wykladowca (
                imie,
                nazwisko,
                adresZamieszkania,
                email,
                pesel,
                zarobki);
    }

    public void stworzKierunekStudiow(String nazwaKierunku,
                                      int progWejsciowy,
                                      TypStudiow typStudiow){

        kierunekStudiow = new KierunekStudiow(nazwaKierunku, progWejsciowy, typStudiow);

    }

    public void stworzRocznik(int numerRocznika){

        rocznik = new Rocznik(numerRocznika);

    }

    public void stworzPrzedmiot(String nazwaPrzedmiotu, Wykladowca wykladowca, RodzajePrzedmiotow rodzaj){

        przedmiot = new Przedmiot(nazwaPrzedmiotu, wykladowca, rodzaj);

    }

    public void dodajKierunekDoRocznika(){

    }

    public void getRocznik(int numerRocznika){

        this.rocznik = new Rocznik();
        connect.laduj(this.rocznik, NazwyTablic.ROCZNIKI.getNazwa(), "WHERE numer_rocznika = "+numerRocznika);

    }

    public void dodajPrzedmiotDoKierunku(){

        kierunekStudiow.dodajPrzedmiot(przedmiot);
    }

    public String wyswietlKierunkiNaRoczniku() throws Exception {

       if (this.rocznik == null) throw new Exception("Nie zaladowano rocznika!\n");

       String[] nazwyKierunkow = connect.getColumn("rocznik_"+this.rocznik.getNumerRocznika(),"nazwa_kierunku","");
       return String.join("\n",nazwyKierunkow);

    }

    public void getKierunek(String nazwaKierunku){

        this.kierunekStudiow = new KierunekStudiow();
        connect.laduj(this.kierunekStudiow, this.rocznik.getNumerRocznika(), "WHERE nazwa_kierunku = '"+nazwaKierunku+"'");

    }

    public String wyswietlStudenta(){
        StringBuilder wynik = new StringBuilder();
        wynik.append("Imie: ").append(student.getImie()).append("\n");
        wynik.append("Nazwisko: ").append(student.getNazwisko()).append("\n");
        wynik.append("Adres: ").append(student.getAdresZamieszkania()).append("\n");
        wynik.append("PESEL: ").append(student.getPesel()).append("\n");
        wynik.append("email: ").append(student.getEmail()).append("\n");
        wynik.append("Numer telefonu: ").append(student.getNumerTelefonu()).append("\n");
        wynik.append("Numer indeksu: ").append(student.getNumerIndeksu()).append("\n");

        return wynik.toString();
    }
    public String wyswietlKierunekStudiow() throws Exception {

        if (this.kierunekStudiow == null) throw new Exception("Nie wybrano kierunku!");

        StringBuilder wynik = new StringBuilder();
        wynik.append("Nazwa kierunku: ").append(kierunekStudiow.getNazwaKierunku()).append("\n");
        wynik.append("Prog wejsciowy: ").append(kierunekStudiow.getProgWejsciowy()).append("\n");
        for (Integer indeks: kierunekStudiow.getListaStudentow()){
            this.student = new Student();
            connect.laduj(this.student, NazwyTablic.STUDENCI.getNazwa(), "WHERE numer_indeksu = '"+indeks+"'");
            wynik.append("\t").append(wyswietlStudenta()).append("\n");
        }

        return wynik.toString();
    }

    public void dodajOceneDoPrzedmiotu(double ocena){

    }

    public void iloscWszystkichStudentow(){

    }

    public void iloscStudentowNaKierunku(){

    }

    public void iloscStudentowNaKierunkuIRoku(){

    }

    public void ktoMaNajlepszaOceneZPrzedmiotu(){

    }

    public void jakaJestSredniaOcenaZPrzedmiotu(){

    }
}
