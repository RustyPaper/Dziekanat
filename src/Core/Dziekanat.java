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
        connect.createTable("rocznik_"+numerRocznika,new String[]{"nazwa_kierunku VARCHAR(255)","prog_wejsciowy INTEGER", "lista_studentow VARCHAR(255)", "lista_przedmiotow VARCHAR(255)","typ_studiow VARCHAR(255)"});
        connect.save(rocznik, NazwyTablic.ROCZNIKI.getNazwa());
    }

    public void stworzPrzedmiot(String nazwaPrzedmiotu, int idWykladowcy, RodzajePrzedmiotow rodzaj){

        przedmiot = new Przedmiot(nazwaPrzedmiotu, idWykladowcy, rodzaj);
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

    public void dodajPrzedmiotDoKierunku(int id){

        kierunekStudiow.dodajPrzedmiot(id);
    }

    public String wyswietlKierunkiNaRoczniku() throws Exception {

       if (this.rocznik == null) throw new Exception("Nie zaladowano rocznika!\n");

       String[][] nazwyKierunkow = connect.getColumn("rocznik_"+this.rocznik.getNumerRocznika(),new String[]{"nazwa_kierunku"},"");

       StringBuilder kierunkiNaRoczniku = new StringBuilder();

       for(String[] nazwa : nazwyKierunkow)
           kierunkiNaRoczniku.append(nazwa[0]).append("\n");

       return kierunkiNaRoczniku.toString();

    }

    public void getKierunek(String nazwaKierunku){

        this.kierunekStudiow = new KierunekStudiow();
        connect.laduj(this.kierunekStudiow, this.rocznik.getNumerRocznika(), "WHERE nazwa_kierunku = '"+nazwaKierunku+"'");

    }

    public String wyswietlStudenta(){

        return "Imie: " + student.getImie() + "\n" +
                "Nazwisko: " + student.getNazwisko() + "\n" +
                "Adres: " + student.getAdresZamieszkania() + "\n" +
                "PESEL: " + student.getPesel() + "\n" +
                "email: " + student.getEmail() + "\n" +
                "Numer telefonu: " + student.getNumerTelefonu() + "\n" +
                "Numer indeksu: " + student.getNumerIndeksu() + "\n";
    }
    public String wyswietlKierunekStudiow() throws Exception {

        if (this.kierunekStudiow == null) throw new Exception("Nie wybrano kierunku!");

        StringBuilder wynik = new StringBuilder();
        wynik.append("Nazwa kierunku: ").append(this.kierunekStudiow.getNazwaKierunku()).append("\n");
        wynik.append("Prog wejsciowy: ").append(this.kierunekStudiow.getProgWejsciowy()).append("\n");
        for (Integer indeks: kierunekStudiow.getListaStudentow()){
            this.student = new Student();
            connect.laduj(this.student, NazwyTablic.STUDENCI.getNazwa(), "WHERE id = '"+indeks+"'");
            wynik.append("\t").append(wyswietlStudenta()).append("\n");
        }

        return wynik.toString();
    }

    public void dodajOceneDoPrzedmiotu(int numerIndeksu, String nazwaPrzedmiotu ,double ocena){
        for(Przedmiot przedmiotStudenta: this.kierunekStudiow.getListaOcen().get(numerIndeksu)){
            if(przedmiotStudenta.getNazwaPrzedmiotu().equals(nazwaPrzedmiotu))
                przedmiotStudenta.dodajOcene(ocena);
        }
    }

    public String wyswietlWszystkiePrzedmioty(){
         String[][] przedmioty = connect.getColumn(NazwyTablic.PRZEDMIOTY.getNazwa(),new String[]{"id","nazwa_przedmiotu","rodzaj"},"");
         StringBuilder wynik = new StringBuilder();

         for(String[] przedmiotZBazy: przedmioty){
             wynik.append(przedmiotZBazy[0]).append(". ").append(przedmiotZBazy[1]).append(" ").append(przedmiotZBazy[2]).append("\n");
         }

         return wynik.toString();
    }

    public int iloscWszystkichStudentow(){
       String[][] studenci = connect.getColumn(NazwyTablic.STUDENCI.getNazwa(), new String[]{"imie"}, "");

       return studenci.length;
    }

    public int iloscStudentowNaKierunku(){
        return this.kierunekStudiow.getListaStudentow().size();
    }

    public int iloscStudentowNaKierunkuIRoku(){
        int sumaStudentow = 0;
        LocalDate data = LocalDate.now();
        int rocznikPierwszy = data.getYear() - 1;
        String[][] nazwyKierunkow = connect.getColumn("rocznik_"+rocznikPierwszy,new String[]{"nazwa_kierunku"},"");
        for (String[] nazwaKierunku: nazwyKierunkow){
            connect.laduj(this.kierunekStudiow, "rocznik_"+rocznikPierwszy, "WHERE nazwa_kierunku = '"+nazwaKierunku[0]+"'");
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

    public String znajdzUczniaPoIndeksie(int numerIndeksu){
        connect.laduj(this.student, NazwyTablic.STUDENCI.getNazwa(), "WHERE id = "+numerIndeksu);

        return this.wyswietlStudenta();
    }

    public String wyswietlRoczniki(){
        String[][] roczniki = connect.getColumn(NazwyTablic.ROCZNIKI.getNazwa(),new String[]{"numer_rocznika"}, "");

        StringBuilder numeryRocznikow = new StringBuilder();

        for(String[] numerRocznika : roczniki)
            numeryRocznikow.append(numerRocznika[0]).append("\n");

        return numeryRocznikow.toString();
    }

    public String wyswietlWykladowcow(){
        StringBuilder wykladowcy = new StringBuilder();

        String[][] wykladowcyZBazy = connect.getColumn(NazwyTablic.WYKLADOWCY.getNazwa(),new String[]{"id","imie","nazwisko"},"");

        for(String[] identyfikatorWykladowcy: wykladowcyZBazy )
            wykladowcy.append(identyfikatorWykladowcy[0])
                    .append(". ").append(identyfikatorWykladowcy[1])
                    .append(" ").append(identyfikatorWykladowcy[2])
                    .append("\n");

        return wykladowcy.toString();
    }

    public String wyswietlWykladowce(int id){
        connect.laduj(this.wykladowca,NazwyTablic.WYKLADOWCY.getNazwa(),"WHERE id="+id);

        return "Imie: " + this.wykladowca.getImie() + "\n" +
                "Nazwisko: " + this.wykladowca.getNazwisko() + "\n" +
                "Adres zamieszkania: " + this.wykladowca.getAdresZamieszkania() + "\n" +
                "PESEL: " + this.wykladowca.getPesel() + "\n" +
                "email: " + this.wykladowca.getEmail() + "\n" +
                "zarobki: " + this.wykladowca.getZarobki() + "\n";
    }

    public String wyswietlOceny(Integer numerIndeksu){

        StringBuilder wynik = new StringBuilder();

        if(this.kierunekStudiow.getListaOcen() != null)
            for(Przedmiot przedmiot :this.kierunekStudiow.getListaOcen().get(numerIndeksu)) {
                wynik.append(przedmiot.getNazwaPrzedmiotu()).append(": ");

                for(double ocena: przedmiot.getOceny())
                    wynik.append(ocena).append(", ");
            }

        return wynik.toString();
    }

    public void dodajStudentaDoKierunku(int numerIndeksu){
        this.kierunekStudiow.getListaStudentow().add(numerIndeksu);
    }

    public String wyswietlWszystkichStudentow(){
        StringBuilder studenci = new StringBuilder();

        String[][] studenciZBazy = connect.getColumn(NazwyTablic.STUDENCI.getNazwa(),new String[]{"id","imie","nazwisko"},"");

        for(String[] identyfikatorWykladowcy: studenciZBazy )
            studenci.append(identyfikatorWykladowcy[0])
                    .append(". ").append(identyfikatorWykladowcy[1])
                    .append(" ").append(identyfikatorWykladowcy[2])
                    .append("\n");

        return studenci.toString();
    }

}
