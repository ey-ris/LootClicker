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
    private int userId;


    public Player(long currency, double clickStrength, double luckyStrike, double critChance, int userId) {
        this.currency = currency;
        this.clickStrength = clickStrength;
        this.luckyStrike = luckyStrike;
        this.critChance = critChance;
        this.userId = userId;
    }

    //Generated methods


    @Override
    public String toString() {
        return "Player{" +
                "userId=" + userId +
                ", score=" + currency +
                ", clickStrength=" + clickStrength +
                ", luckyStrike=" + luckyStrike +
                ", critChance=" + critChance +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return id == player.id && currency == player.currency && Double.compare(clickStrength, player.clickStrength) == 0 && Double.compare(luckyStrike, player.luckyStrike) == 0 && Double.compare(critChance, player.critChance) == 0 && userId == player.userId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, currency, clickStrength, luckyStrike, critChance, userId);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

