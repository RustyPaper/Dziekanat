import Core.Dziekanat;
import Core.RodzajePrzedmiotow;
import Core.TypStudiow;
import Core.Wykladowca;

public class Dzik {

    public static void main(String[] args) {
        Dziekanat dziekanat = new Dziekanat();

        /*dziekanat.stworzStudenta("Marcin", "Wrzeszczak", "Rynkowa 8/5, 53-335", "macius@jajko.ru", 930101983, 374829475, 347632746);
        dziekanat.stworzKierunekStudiow("Pszyrka", 581, TypStudiow.DOKTORANCIE);
        dziekanat.stworzRocznik(2018);
        dziekanat.stworzWykladowce("Stefan", "Paleta", "Ikeowa 911", "Paletnik@pala.pl", 93031111654L, 3200);
        //dziekanat.stworzPrzedmiot("Matma", "Stefan", RodzajePrzedmiotow.CWICZENIA );
        dziekanat.dodajPrzedmiotDoKierunku();
        dziekanat.dodajKierunekDoRocznika();
        */


        //System.out.println(dziekanat.wyswietlKierunekStudiow());
        dziekanat.getRocznik(2018);
        dziekanat.getKierunek("Informatyka");
        try {
            System.out.println(dziekanat.wyswietlKierunekStudiow());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
