package com.example.lootclicker.database.entities;

import android.util.Log;
import android.widget.Toast;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.lootclicker.database.AppDatabase;
import com.example.lootclicker.database.AppRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

@Entity(tableName = AppDatabase.PLAYER_TABLE)
public class Player {
    @PrimaryKey(autoGenerate = true)
    private int id;

    private long currency;
    private double clickStrength;//How much currency per click
    private double luckyStrike; //Chance of getting an item multiplied by x0.01. More lucky means more access to rarer items.
    private double critChance; //Chance to do more crits (crits = 3x clickStrength)
    private HashMap<Integer, Integer> equippedItems = new HashMap<>(3);
    private ArrayList<Integer> inventory = new ArrayList<>(20);
    private int userId;

    public Player(long currency, double clickStrength, double luckyStrike, double critChance, HashMap<Integer, Integer> equippedItems, ArrayList<Integer> inventory, int userId) {
        this.currency = currency;
        this.clickStrength = clickStrength;
        this.luckyStrike = luckyStrike;
        this.critChance = critChance;
        this.equippedItems = equippedItems;
        this.inventory = inventory;
        this.userId = userId;
    }

    //Todo: reuse some of this
    //    public double onClick() {
//        Random rand = new Random();
//        var tempClickStrength = clickStrength.getValue();
//        float luckyChance = rand.nextFloat();
//        float critChance = rand.nextFloat();
//
//        //If player critChance is higher than the rolled crit, then it's a crit.
//        if(this.critChance.getValue() >= critChance){
//            tempClickStrength = (tempClickStrength * 3d);
//            currency += (long)tempClickStrength;
//        }
//
//        //Lucky strike increases your chance of getting better items
////        if(luckyStrike.getValue()*0.01 >= luckyChance){
////            //Todo:Implement luckyloot
////        }
//
//        return tempClickStrength;
//    }

    //Generated methods


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return userId == player.userId && currency == player.currency && Double.compare(clickStrength, player.clickStrength) == 0 && Double.compare(luckyStrike, player.luckyStrike) == 0 && Double.compare(critChance, player.critChance) == 0 && Objects.equals(equippedItems, player.equippedItems) && Objects.equals(inventory, player.inventory);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, currency, clickStrength, luckyStrike, critChance, equippedItems, inventory);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public HashMap<Integer, Integer> getEquippedItems() {
        return equippedItems;
    }

    public void setEquippedItems(HashMap<Integer, Integer> equippedItems) {
        this.equippedItems = equippedItems;
    }

    public ArrayList<Integer> getInventory() {
        return inventory;
    }

    public void setInventory(ArrayList<Integer> inventory) {
        this.inventory = inventory;
    }

    public double getClickStrength() {
        return clickStrength;
    }

    public void setClickStrength(double clickStrength) {
        this.clickStrength = clickStrength;
    }

    public double getLuckyStrike() {
        return luckyStrike;
    }

    public void setLuckyStrike(double luckyStrike) {
        this.luckyStrike = luckyStrike;
    }

    public double getCritChance() {
        return critChance;
    }

    public void setCritChance(double critChance) {
        this.critChance = critChance;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public long getCurrency() {
        return currency;
    }

    public void setCurrency(long currency) {
        this.currency = currency;
    }
}

