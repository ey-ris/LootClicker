package com.example.lootclicker.database.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.lootclicker.database.AppDatabase;
import com.example.lootclicker.sandbox.StatModifier;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity(tableName = AppDatabase.ITEM_TABLE)
public class Item {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;
    private int equipSlot;
    private List<StatModifier> modifiers;

    public Item(String name, int equipSlot, List<StatModifier> modifiers) {
        this.name = name;
        this.equipSlot = equipSlot;
        this.modifiers = modifiers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return id == item.id && Objects.equals(name, item.name) && equipSlot == item.equipSlot && Objects.equals(modifiers, item.modifiers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, equipSlot, modifiers);
    }

    public int getEquipSlot(){
        return equipSlot;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEquipSlot(int equipSlot) {
        this.equipSlot = equipSlot;
    }

    public List<StatModifier> getModifiers() {
        return modifiers;
    }

    public void setModifiers(List<StatModifier> modifiers) {
        this.modifiers = modifiers;
    }
}

