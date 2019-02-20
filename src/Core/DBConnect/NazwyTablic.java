package Core.DBConnect;

public enum NazwyTablic {
    STUDENCI("studenci"),
    ROCZNIKI("roczniki"),
    PRZEDMIOTY("przedmioty"),
    WYKLADOWCY("wykladowcy");

    private String nazwa;

    private NazwyTablic(String nazwa){
        this.nazwa = nazwa;

    }

    public java.lang.String getNazwa() {
        return nazwa;
    }
}
