package Core;

import Core.DBConnect.Connect;
import Core.DBConnect.NazwyTablic;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

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
                              int numerTelefonu){
        student = new Student(imie,
                              nazwisko,
                              adresZamieszkania,
                              email,
                              pesel,
                              numerTelefonu);

        int numerIndeksu = connect.save(student, NazwyTablic.STUDENCI.getNazwa());
        this.student.setNumerIndeksu(numerIndeksu);

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

        int id = connect.save(wykladowca, NazwyTablic.WYKLADOWCY.getNazwa());
        this.wykladowca.setIdWykladowcy(id);
    }

    public void stworzKierunekStudiow(String nazwaKierunku,
                                      int progWejsciowy,
                                      TypStudiow typStudiow){

        kierunekStudiow = new KierunekStudiow(nazwaKierunku, progWejsciowy, typStudiow);

    }

    public void stworzRocznik(int numerRocznika){

        rocznik = new Rocznik(numerRocznika);
        connect.save(rocznik, NazwyTablic.ROCZNIKI.getNazwa());
    }

    public void stworzPrzedmiot(String nazwaPrzedmiotu, Wykladowca wykladowca, RodzajePrzedmiotow rodzaj){

        przedmiot = new Przedmiot(nazwaPrzedmiotu, wykladowca.getIdWykladowcy(), rodzaj);
        int id = connect.save(przedmiot, NazwyTablic.PRZEDMIOTY.getNazwa());
        przedmiot.setId(id);
    }

    public void dodajKierunekDoRocznika(){
        rocznik.dodajKierunek(this.kierunekStudiow.getIdKierunku());
        int id = connect.save(this.kierunekStudiow, "rocznik_"+ this.rocznik.getNumerRocznika());
        this.kierunekStudiow.setIdKierunku(id);
    }

    public void getRocznik(int numerRocznika){

        this.rocznik = new Rocznik();
        connect.laduj(this.rocznik, NazwyTablic.ROCZNIKI.getNazwa(), "WHERE numer_rocznika = "+numerRocznika);

    }

    public void dodajPrzedmiotDoKierunku(){

        kierunekStudiow.dodajPrzedmiot(przedmiot.getId());
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
        przedmiot.dodajOcene(ocena);
    }

    public int iloscWszystkichStudentow(){
       String[] studenci = connect.getColumn(NazwyTablic.STUDENCI.getNazwa(), "imie", "");

       return studenci.length;
    }

    public int iloscStudentowNaKierunku(){
        return this.kierunekStudiow.getListaStudentow().size();
    }

    public int iloscStudentowNaKierunkuIRoku(){
        int sumaStudentow = 0;
        LocalDate data = LocalDate.now();
        int rocznikPierwszy = data.getYear() - 1;
        String[] nazwyKierunkow = connect.getColumn("rocznik_"+rocznikPierwszy,"nazwa_kierunku","");
        for (String nazwaKierunku: nazwyKierunkow){
            connect.laduj(this.kierunekStudiow, "rocznik_"+rocznikPierwszy, "WHERE nazwa_kierunku = '"+nazwaKierunku+"'");
            sumaStudentow += this.kierunekStudiow.getListaStudentow().size();
        }

        return sumaStudentow;
    }

    public int ktoMaNajlepszaOceneZPrzedmiotu(String nazwaPrzedmiotu){
        Map<Integer,Float> srednie = new HashMap<>();
        for (int student : this.kierunekStudiow.getListaOcen().keySet()){
            srednie.put(student,this.jakaJestSredniaOcenaZPrzedmiotu(student,nazwaPrzedmiotu));
        }

        float najwyzszaOcena = 0;
        int indeksStudenta = 0;

        for(Map.Entry<Integer,Float> sredniaStudenta: srednie.entrySet()){
           if(sredniaStudenta.getValue() > najwyzszaOcena)
               indeksStudenta = sredniaStudenta.getKey();
        }

        return indeksStudenta;
    }

    public float jakaJestSredniaOcenaZPrzedmiotu(int numerIndeksu, String nazwaPrzedmiotu){
        float srednia = 0;
        for(Przedmiot przedmiot: this.kierunekStudiow.getListaOcen().get(numerIndeksu))
            if(przedmiot.getNazwaPrzedmiotu().equals(nazwaPrzedmiotu)) {
                this.przedmiot = przedmiot;
                break;
            }

        for(double ocena: this.przedmiot.getOceny())
            srednia += ocena;

        srednia /= this.przedmiot.getOceny().size();

        return srednia;
    }


}
