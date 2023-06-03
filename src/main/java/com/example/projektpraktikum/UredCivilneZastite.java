package com.example.projektpraktikum;
import java.util.List;

public class UredCivilneZastite {
    private String ime;
    private List<Sirene> sveSirene;

    public UredCivilneZastite(String ime, List<Sirene> sveSirene) {
        this.ime = ime;
        this.sveSirene = sveSirene;
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
    public static void obavijestiCentarUPuli(int sirenaID) {
        System.out.println("Sirena s ID-em" + sirenaID + "se upalila ručno.");
    }

}
