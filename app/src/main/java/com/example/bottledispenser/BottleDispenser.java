package com.example.bottledispenser;

import android.util.Log;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.IOError;
import java.util.ArrayList;
import java.util.Locale;

public class BottleDispenser {
    private int bottles;
    private double money;
    private ArrayList<Bottle> bottle_array;

    // singleton, eli näitä voi olla vain yksi:
    private static BottleDispenser dispenser = new BottleDispenser();

    public static BottleDispenser getInstance() {
        // pääohjelmasta kutsutaan tätä
        return dispenser;
    }

    private BottleDispenser() {
        // privaatti rakentaja
        bottles = 5;
        money = 0;

        // Taulukon alustus (initializing)
        bottle_array = new ArrayList<Bottle>();

        // lisätään pulloja, omat nimet
        bottle_array.add(new Bottle());
        bottle_array.add(new Bottle("Pepsi Max", "Pepsi", 0.3, 1.5, 2.2));
        bottle_array.add(new Bottle("Coca-Cola Zero", "Coca-Cola", 0.3, 0.5, 2.0));
        bottle_array.add(new Bottle("Coca-Cola Zero", "Coca-Cola", 0.3, 1.5, 2.5));
        bottle_array.add(new Bottle("Fanta Zero", "Fanta", 0.3, 0.5, 1.95));

    }

    public void addMoney(TextView msg, int amnt) {
        money += amnt;
        msg.setText("Klink! Added more money!");
    }

    public void buyBottle(int choice, TextView msg) {
        //pullojen määrä ei voi olla negatiivinen ja rahatta ei voi ostaa

        // alla otetaan pullolistasta pullo ja pyydetään sitten sen hintaa
        if (money == 0 || money < (bottle_array.get(choice-1)).getPrice()) {
            msg.setText("Add money first!");

        } else if (bottles == 0) {
            msg.setText("Out of bottles!");

        } else {
            bottles -= 1;

            // error handling?
            Bottle x = bottle_array.get(choice - 1);

            money -= x.getPrice();
            // pullon nimi saadaan oliosta bottle_array
            msg.setText("KACHUNK! " + (bottle_array.get(choice - 1)).getName()
                    + " came out of the dispenser!");

            // pullon poisto listasta:
            bottle_array.remove(choice - 1);
        }
    }

    public void returnMoney(TextView msg) {
        //locale.FRANCE käyttää floatissa pilkkua
        msg.setText("Klink klink. Money came out! You got "
                + String.format(Locale.FRANCE, "%.2f", money) + "€ back");
        money = 0;
    }

    public ArrayList<String> list() {
        // lisätään arrayn pullot järjestyksessä string arrayhyn ja palautetaan array
        ArrayList<String> s = new ArrayList<String>();

        for (int i=0; i<bottles; i++) {
            s.add((i+1) + ". Name: " + (bottle_array.get(i)).getName()
                    + "\n\tSize: " + (bottle_array.get(i)).getSize()
                    + "\tPrice: " + (bottle_array.get(i)).getPrice() + "\n");
        }

        return s;
    }

    public ArrayList<Bottle> listOlio() {
        return  bottle_array;
    }

    public double showMoney() {
        return money;
    }
}