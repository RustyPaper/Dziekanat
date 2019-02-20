
import Core.Dziekanat;

import java.io.IOException;
import java.util.Scanner;

public class Dzik {

    private Dziekanat dziekanat;

    private final opcjaMenu[] opcjeGlownegoMenu = {
            new opcjaMenu(1,"wczytaj", ()->this.wyswietlMenu(this.opcjeSubMenuWczytaj)),
            new opcjaMenu(2, "zapisz", ()->this.wyswietlMenu(this.opcjeSubMenuZapisz)),
            new opcjaMenu(3, "wyszukaj", null),
            new opcjaMenu(4, "Zakoncz", null)
    };

    private final opcjaMenu[] opcjeSubMenuWczytaj = {
            new opcjaMenu(1, "wczytaj rocznik", this::wczytajRocznik),
            new opcjaMenu(2, "wczytaj kierunek", this::wczytajKierunek),
            new opcjaMenu(3, "wyswietl kierunek", this::wyswietlKierunek),
            new opcjaMenu(4,"wyswietl wszystkich studentow", null),
            new opcjaMenu(5, "powrot", this::glowneMenu)
    };

    private final opcjaMenu[] opcjeSubMenuZapisz = {
            new opcjaMenu(1, "zapisz rocznik", null),
            new opcjaMenu(2, "zapisz kierunek", null),
            new opcjaMenu(3, "zapisz studenta do kierunku", null),
            new opcjaMenu(4, "dodaj studenta", null),
            new opcjaMenu(5, "powrot", this::glowneMenu)
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
        try {
            Runtime.getRuntime().exec("clear");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

   private opcjaMenu wyswietlMenu(opcjaMenu[] opcje){
       wyczyscKonsole();

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
        //this.dziekanat.wyswietlRoczniki();
        System.out.print("\nWybierz rocznik: ");
        int rok = this.czytnik.nextInt();

        this.dziekanat.getRocznik(rok);

        System.out.println("Wczytano");
        wyswietlMenu(this.opcjeSubMenuWczytaj);
    }

    private void wczytajKierunek(){
        try {
            this.dziekanat.wyswietlKierunkiNaRoczniku();
            System.out.print("\nWybierz kierunek: ");
            this.czytnik.next();
            String kierunek = this.czytnik.nextLine();

            this.dziekanat.getKierunek(kierunek);
            System.out.println("Wczytano");

        } catch (Exception e) {
            e.printStackTrace();
        }
        wyswietlMenu(this.opcjeSubMenuWczytaj);
    }

    private void wyswietlKierunek(){
        try {
            dziekanat.wyswietlKierunekStudiow();

        } catch (Exception e) {
            e.printStackTrace();
        }
        wyswietlMenu(this.opcjeSubMenuWczytaj);
    }
}
