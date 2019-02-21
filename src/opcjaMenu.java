public class opcjaMenu {

    private int numer;
    private String nazwa;
    private MenuListener listener;

    public opcjaMenu(int numer, String nazwa, MenuListener listener){
        this.nazwa = nazwa;
        this.numer = numer;
        this.listener = listener;
    }

    public int getNumer() {
        return numer;
    }

    public String getNazwa() {
        return nazwa;
    }

    public void uruchomSubMenu(){
        listener.run();
    }
}
