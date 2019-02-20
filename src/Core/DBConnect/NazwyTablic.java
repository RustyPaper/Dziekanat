package Core.DBConnect;

public enum NazwyTablic {
    STUDENCI("studenci"),
    ROCZNIKI("roczniki");

    private String nazwa;

    private NazwyTablic(String nazwa){
        this.nazwa = nazwa;

    }

    public java.lang.String getNazwa() {
        return nazwa;
    }
}
