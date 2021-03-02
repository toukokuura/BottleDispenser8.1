package com.example.bottledispenser;

public class Bottle {
    private String name;
    private String manufacturer;
    private double total_energy;
    private double size;
    private double price;

    // oletusnimet
    public Bottle(){
        name = "Pepsi Max";
        manufacturer = "Pepsi";
        total_energy = 0.3;
        size = 0.5;
        price = 1.80;
    }

    // annetut nimet
    public Bottle(String n, String manuf, double totE, double s, double p){
        name = n;
        manufacturer = manuf;
        total_energy = totE;
        size = s;
        price = p;
    }

    @Override
    public String toString() {
        String s = name + size + price;
        return s;
    }

    public String getName(){
        return name;
    }

    public String getManufacturer(){
        return manufacturer;
    }

    public double getEnergy(){
        return total_energy;
    }

    public double getSize(){
        return size;
    }

    public double getPrice(){
        return price;
    }

}
