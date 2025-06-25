package com.example.zadatakliste;

public class Drzava {
    private String code;
    String name;
    String capital;
    int imageResId;

    public Drzava(String code, String name, String capital, int imageResId)
    {
        this.code = code;
        this.name = name;
        this.capital = capital;
        this.imageResId = imageResId;
    }
    public String getCode() {return code;}
    public String getName(){return name;}
    public String getCapital(){return capital;}
    public int getImageResId() {return imageResId;}
}
