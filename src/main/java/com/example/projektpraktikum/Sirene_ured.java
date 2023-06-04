package com.example.projektpraktikum;
import java.util.List;

public class Sirene_ured {
    private List<Sirene> sveSirene;
    private List<Ured> uredi;
    private int id;

    public Sirene_ured(List<Sirene> sveSirene, List<Ured> uredi, int id) {
        this.sveSirene = sveSirene;
        this.uredi = uredi;
        this.id = id;
    }

    public void paljenjeSvihSirena() {
        for (Sirene sirena : sveSirene) {
            sirena.paljenje(sirena.getId_sirene());
        }
    }

    public void gasenjeSvihSirena() {
        for (Sirene sirena : sveSirene) {
            sirena.gasenje(sirena.getId_sirene());
        }
    }

    public void paljenjeSirene(int sirenaID) {
        for (Sirene sirena : sveSirene) {
            if (sirena.getId_sirene() == sirenaID) {
                sirena.paljenje(sirenaID);
                break;
            }
        }
    }

    public void gasenjeSirene(int sirenaID) {
        for (Sirene sirena : sveSirene) {
            if (sirena.getId_sirene() == sirenaID) {
                sirena.gasenje(sirenaID);
                break;
            }
        }
    }
    public void pratiStanjeSirena() {
        System.out.println("Stanje sirena na području Istarske županije:");
        for (Sirene sirena : sveSirene) {
            System.out.println("Sirena ID: " + sirena.getId_sirene() + ", Lokacija: " + sirena.getLokacija() +
                    ", Stanje: " + sirena.getStanje());
        }
    }
    public static void obavijestiCentarOPaljenju(int sirenaID) {
        System.out.println("Sirena s ID-em" + sirenaID + " se upalila ručno.");
    }
    public static void obavijestiCentarOGasenju(int sirenaID) {
        System.out.println("Sirena s ID-em" + sirenaID + " se ugasila ručno.");
    }

}
