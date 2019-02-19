package Core;

public enum TypStudiow {
    LICENCJACKIE(3.0),
    INZYNIERSKIE(3.5),
    MAGISTERSKIE(1.5),
    DOKTORANCIE(1.5);

    private final double czasTrwaniaStudiow;

    TypStudiow(double czasTrwaniaStudiow){
        this.czasTrwaniaStudiow = czasTrwaniaStudiow;
    }

    public double getCzasTrwaniaStudiow() {
        return czasTrwaniaStudiow;
    }
}
