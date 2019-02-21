
import Core.Dziekanat;
import Core.RodzajePrzedmiotow;
import Core.TypStudiow;

import java.util.Scanner;

public class Dzik {

    private Dziekanat dziekanat;

    private final opcjaMenu[] opcjeGlownegoMenu = {
            new opcjaMenu(1,"wczytaj", ()->this.wyswietlMenu(this.opcjeSubMenuWczytaj)),
            new opcjaMenu(2, "zapisz", ()->this.wyswietlMenu(this.opcjeSubMenuZapisz)),
            new opcjaMenu(3, "wyszukaj", ()->this.wyswietlMenu(this.opcjeSubMenuWyszukaj)),
            new opcjaMenu(4, "Zakoncz", null)
    };

    private final opcjaMenu[] opcjeSubMenuWyswietl = {
            new opcjaMenu(1,"wyswietl studenta", this::wyswietlStudenta),
            new opcjaMenu(2, "wyswietl kierunek", this::wyswietlKierunek),
            new opcjaMenu(3,"średnia ocena ucznia z przedmiotu", this::wyswietlSredniaOceneZPrzedmiotu),
            new opcjaMenu(4,"wyswietl wykladowce", this::wyswietlWykladowce),
            new opcjaMenu(5,"wyswietl wykladowcow", this::wyswietlWykladowcow),
            new opcjaMenu(6,"wyswietl oceny studenta", this::wyswietlOcenyStudenta),
            new opcjaMenu(6,"ilosc wszystkich studentow",this::wyswietlIloscWszystkichStudentow),
            new opcjaMenu(7,"ilosc studentow na kierunku", this::wyswietlIloscStudentowNaKierunku),
            new opcjaMenu(8,"ilosc studentow na I roku", this::iloscStudentowNaKierunkuIRoku),
            new opcjaMenu(9, "powrot", this::glowneMenu)
    };

    private final opcjaMenu[] opcjeSubMenuWczytaj = {
            new opcjaMenu(1, "wczytaj rocznik", this::wczytajRocznik),
            new opcjaMenu(2, "wczytaj kierunek", this::wczytajKierunek),
            new opcjaMenu(5, "powrot", this::glowneMenu)
    };

    private final opcjaMenu[] opcjeSubMenuZapisz = {
            new opcjaMenu(1, "zapisz rocznik", this::zapiszRocznik),
            new opcjaMenu(2, "zapisz kierunek", this::zapiszKierunek),
            new opcjaMenu(3, "zapisz przedmiot", this::zapiszPrzedmiot),
            new opcjaMenu(4, "dodaj przedmioty do kierunku", null),
            new opcjaMenu(5, "zapisz studenta do kierunku", null),
            new opcjaMenu(5, "zapisz Wykladowce", this::stworzWykladowce),
            new opcjaMenu(6, "dodaj studenta", this::dodajStudenta),
            new opcjaMenu(7, "powrot", this::glowneMenu)
    };

    private final opcjaMenu[] opcjeSubMenuWyszukaj = {
            new opcjaMenu(1, "Kto ma najlepsza ocene z przedmiotu", this::ktoMaNajlepszaOcene),
            new opcjaMenu(2, "powrot", this::glowneMenu)
    };

    private Scanner czytnik;

    public static void main(String[] args) {
        Dzik dzik = new Dzik();
        dzik.powitanie();
        dzik.glowneMenu();//.uruchomSubMenu();
    }

    private Dzik(){
        this.czytnik = new Scanner(System.in);
        this.dziekanat = new Dziekanat();
    }

    private void powitanie(){
        System.out.println("\n\tWitaj w systemie Dzik.");
    }

    private opcjaMenu glowneMenu(){
        System.out.println("\n\tMENU:");

        return wyswietlMenu(opcjeGlownegoMenu);
    }

    private void wyczyscKonsole(){
        /*try {
           Runtime.getRuntime().exec("clear");

        } catch (IOException e) {
            e.printStackTrace();
        }*/
        System.out.print("\033[H\033[2J");

    }

   private opcjaMenu wyswietlMenu(opcjaMenu[] opcje){
       wyczyscKonsole();
       System.out.println("\n");
       for(opcjaMenu opcja: opcje)
           System.out.println("\t\t"+opcja.getNumer()+". "+opcja.getNazwa());

      System.out.print("Wybor opcji: ");
      int wybor =  czytnik.nextInt();

       for(opcjaMenu opcja: opcje)
          if(opcja.getNumer() == wybor)
               opcja.uruchomSubMenu();

       return null;
    }

    private void wczytajRocznik(){
        System.out.print(this.dziekanat.wyswietlRoczniki());
        System.out.print("\nWybierz rocznik: ");
        int rok = this.czytnik.nextInt();

        this.dziekanat.getRocznik(rok);

        System.out.println("Wczytano");
        wyswietlMenu(this.opcjeSubMenuWczytaj);
    }

    private void wczytajKierunek(){
        try {
            String kierunki = this.dziekanat.wyswietlKierunkiNaRoczniku();
            System.out.println(kierunki);
            System.out.print("\nWybierz kierunek: ");
            this.czytnik.nextLine();
            String kierunek = this.czytnik.nextLine();

            this.dziekanat.getKierunek(kierunek);
            System.out.println("Wczytano");

        } catch (Exception e) {
            //e.printStackTrace();
            System.out.println(e.toString());
        }
        wyswietlMenu(this.opcjeSubMenuWczytaj);
    }

    private void wyswietlKierunek(){
        try {
            String kierunek = dziekanat.wyswietlKierunekStudiow();
            System.out.println(kierunek);

        } catch (Exception e) {
            //e.printStackTrace();
            System.out.println(e.toString());
        }
        wyswietlMenu(this.opcjeSubMenuWczytaj);
    }

    private void ktoMaNajlepszaOcene(){
        System.out.print("\nWybierz przedmiot: ");
        this.czytnik.nextLine();
        String przedmiot = this.czytnik.nextLine();
        int indeks = dziekanat.ktoMaNajlepszaOceneZPrzedmiotu(przedmiot);
        String uczen = dziekanat.znajdzUczniaPoIndeksie(indeks);
        System.out.print("\nNajlepszym uczniem na tym kierunku z przedmiotu "+przedmiot+" jest \n"+uczen);
        wyswietlMenu(this.opcjeSubMenuWyszukaj);
    }

    private void zapiszKierunek(){
        this.czytnik.nextLine();
        System.out.println("\nPodaj nazwe kierunku: ");
        String nazwaKierunku = this.czytnik.nextLine();

        System.out.println("\nPodaj prog wejsciowy: ");
        int progWejsciowy = this.czytnik.nextInt();

        this.czytnik.nextLine();
        wybierzTypStudiow();
        System.out.print("\nWpisz Typ Studiow");
        String typStudiow = this.czytnik.nextLine();

        dziekanat.stworzKierunekStudiow(nazwaKierunku,progWejsciowy,TypStudiow.valueOf(typStudiow));



        dziekanat.dodajKierunekDoRocznika();
        wyswietlMenu(this.opcjeSubMenuZapisz);
    }

    private void zapiszRocznik(){
        System.out.print("Podaj numer rocznika: ");
        int numerRocznika = czytnik.nextInt();

        dziekanat.stworzRocznik(numerRocznika);
        wyswietlMenu(this.opcjeSubMenuZapisz);
    }

    private void dodajPrzedmiotDoKierunku(){
        //TODO Dokonczyc
        dziekanat.dodajPrzedmiotDoKierunku();
    }

    private void zapiszPrzedmiot(){
        this.czytnik.nextLine();

        System.out.print("\nWpisz nazwe przedmiotu: ");
        String nazwa = this.czytnik.nextLine();

        this.wyswietlWykladowcow();

        System.out.print("Wpisz id wykladowcy: ");
        int idWykladowcy = this.czytnik.nextInt();

        System.out.println("Rodzaje przedmiotu: ");
        for(RodzajePrzedmiotow rodzaj: RodzajePrzedmiotow.values())
            System.out.println(rodzaj);

        this.czytnik.nextLine();
        System.out.print("Wpisz id wykladowcy: ");
        String rodzajPrzedmiotu = this.czytnik.nextLine();

        dziekanat.stworzPrzedmiot(nazwa,idWykladowcy, RodzajePrzedmiotow.valueOf(rodzajPrzedmiotu));
    }

    private void stworzWykladowce(){
        czytnik.nextLine();

        System.out.print("\nWpisz imie: ");
        String imieWykladowcy = this.czytnik.nextLine();

        System.out.print("\nWpisz nazwisko: ");
        String nazwiskoWykladowcy = this.czytnik.nextLine();

        System.out.print("\nWpisz adres zamieszkania: ");
        String adresZamieszkania = this.czytnik.nextLine();

        System.out.print("\nWpisz email: ");
        String emailWykladowcy = this.czytnik.nextLine();

        System.out.print("\nWpisz pesel: ");
        long peselWykladowcy = this.czytnik.nextLong();


        System.out.print("\nWpisz zarobki: ");
        float zarobkiWykladowcy = this.czytnik.nextFloat();

        dziekanat.stworzWykladowce(imieWykladowcy, nazwiskoWykladowcy,adresZamieszkania,emailWykladowcy, peselWykladowcy, zarobkiWykladowcy);
        wyswietlMenu(this.opcjeSubMenuZapisz);
    }

    private void wybierzTypStudiow(){
        System.out.println("Typ studiow: ");
        for(TypStudiow typ : TypStudiow.values())
            System.out.println(typ);
    }

    private void wyswietlOcenyStudenta(){
        //this.czytnik.nextLine();

        System.out.print("Podaj numer indeksu studenta: ");
        int numerIndeksu =czytnik.nextInt();
        String oceny = dziekanat.wyswietlOceny(numerIndeksu);

        System.out.println(oceny);
        wyswietlMenu(this.opcjeSubMenuWyswietl);
    }

    private void wyswietlWykladowcow(){
        String wykladowcy = dziekanat.wyswietlWykladowcow();

        System.out.println(wykladowcy);
        wyswietlMenu(this.opcjeSubMenuWyswietl);
    }

    private void wyswietlWykladowce(){
        System.out.print("\nPodaj id wykladowcy: ");
        int id = czytnik.nextInt();

       String wykladowca =  dziekanat.wyswietlWykladowce(id);
       System.out.println(wykladowca);
       wyswietlMenu(this.opcjeSubMenuWyswietl);
    }

    private void wyswietlStudenta(){
        System.out.print("\nPodaj id wykladowcy: ");
        int id = czytnik.nextInt();

        String student =  dziekanat.znajdzUczniaPoIndeksie(id);
        System.out.println(student);
        wyswietlMenu(this.opcjeSubMenuWyswietl);
    }

    private void wyswietlSredniaOceneZPrzedmiotu(){
        System.out.print("\nPodaj numer indeksu studenta: ");
        int numerIndeksu = czytnik.nextInt();

        czytnik.nextLine();
        System.out.print("\nPodaj nazwe przedmiotu: ");
        String nazwaPrzedmiotu = czytnik.nextLine();
        float srednia = dziekanat.jakaJestSredniaOcenaZPrzedmiotu(numerIndeksu, nazwaPrzedmiotu);

        System.out.println("\nSrednia ocena z "+nazwaPrzedmiotu+" to: "+srednia);
        wyswietlMenu(this.opcjeSubMenuWyswietl);
    }

    private void dodajStudenta(){
        this.czytnik.nextLine();

        System.out.print("\nWpisz imie: ");
        String imieWykladowcy = this.czytnik.nextLine();

        System.out.print("\nWpisz nazwisko: ");
        String nazwiskoWykladowcy = this.czytnik.nextLine();

        System.out.print("\nWpisz adres zamieszkania: ");
        String adresZamieszkania = this.czytnik.nextLine();

        System.out.print("\nWpisz email: ");
        String emailWykladowcy = this.czytnik.nextLine();

        System.out.print("\nWpisz pesel: ");
        long peselWykladowcy = this.czytnik.nextLong();


        System.out.print("\nWpisz numer telefonu: ");
        int numerTelefonu = this.czytnik.nextInt();

        dziekanat.stworzStudenta(imieWykladowcy, nazwiskoWykladowcy,adresZamieszkania,emailWykladowcy, peselWykladowcy, numerTelefonu);
        wyswietlMenu(this.opcjeSubMenuZapisz);
    }

    private void wyswietlIloscWszystkichStudentow(){
        int ilosc =(this.dziekanat.iloscWszystkichStudentow());

        System.out.println("Ilosc wszystkich studentow wynosi: "+ilosc);

        wyswietlMenu(this.opcjeSubMenuWyswietl);
    }

    private void wyswietlIloscStudentowNaKierunku(){
        int ilosc =(this.dziekanat.iloscStudentowNaKierunku());

        System.out.println("Ilosc wszystkich studentow na kierunku wynosi: "+ilosc);

        wyswietlMenu(this.opcjeSubMenuWyswietl);
    }

    public void iloscStudentowNaKierunkuIRoku(){
        int ilosc =(this.dziekanat.iloscStudentowNaKierunkuIRoku());

        System.out.println("Ilosc wszystkich studentow na I roku wynosi: "+ilosc);

        wyswietlMenu(this.opcjeSubMenuWyswietl);
    }
}

