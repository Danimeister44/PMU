package com.example.zadatakjson;

public class Zemljiste {
    String grad;
    String ulica;
    int broj;
    int povrsina;
    String tip;
    int id;
    String kvalitet;
    String privatno;
    String dugovanje;
    int telefon;

    public Zemljiste(String grad, String ulica, int broj, int povrsina, String tip, int id, String kvalitet, String privatno, String dugovanje, int telefon) {
        this.grad = grad;
        this.ulica = ulica;
        this.broj = broj;
        this.povrsina = povrsina;
        this.tip = tip;
        this.id = id;
        this.kvalitet = kvalitet;
        this.privatno = privatno;
        this.dugovanje = dugovanje;
        this.telefon = telefon;
    }
}
