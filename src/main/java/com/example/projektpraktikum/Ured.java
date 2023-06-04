package com.example.projektpraktikum;

public class Ured {
    private int id_ureda;
    private String ime;

    public int getId_ureda() {
        return id_ureda;
    }

    public void setId_ureda(int id_ureda) {
        this.id_ureda = id_ureda;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    @Override
    public String toString() {
        return "Ured{" +
                "id_ureda=" + id_ureda +
                ", ime='" + ime + '\'' +
                '}';
    }
}

