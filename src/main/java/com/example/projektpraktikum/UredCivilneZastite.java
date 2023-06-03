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
            sirena.paljenje();
        }
    }

    public void gasenjeSvihSirena() {
        for (Sirene sirena : sveSirene) {
            sirena.gasenje();
        }
    }

}
