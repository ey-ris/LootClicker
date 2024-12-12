package com.example.lootclicker.sandbox;

import androidx.room.Ignore;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

public class Stat {
    private boolean isChanged;
    private final double baseValue;
    private double value;
    private final List<StatModifier> modifiers;

    public Stat(double initialValue) {
        isChanged = true;
        modifiers = new ArrayList<>(50);
        baseValue = initialValue;
    }

    public void AddModifier(StatModifier modifier) {
        modifiers.add(modifier);
    }

    public void RemoveModifier(StatModifier modifier) {
        modifiers.remove(modifier);
    }

    public double getValue() {
        return value;
    }
}
