package com.example.lootclicker.sandbox;

import java.util.Objects;


public class StatModifier{
    private int targetedStat;
    private double value;
    private boolean isMultiplicative;

    public StatModifier(int targetedStat,double value, boolean isMultiplicative){
        this.targetedStat = targetedStat;
        this.value = value;
        this.isMultiplicative = isMultiplicative;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StatModifier that = (StatModifier) o;
        return targetedStat == that.targetedStat && Double.compare(value, that.value) == 0 && isMultiplicative == that.isMultiplicative;
    }

    @Override
    public int hashCode() {
        return Objects.hash(targetedStat, value, isMultiplicative);
    }

    public int getTargetedStat() {
        return targetedStat;
    }

    public void setTargetedStat(int targetedStat) {
        this.targetedStat = targetedStat;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public boolean isMultiplicative() {
        return isMultiplicative;
    }

    public void setMultiplicative(boolean multiplicative) {
        isMultiplicative = multiplicative;
    }
}