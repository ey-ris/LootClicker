package com.example.lootclicker.database.typeConverters;

import androidx.room.TypeConverter;

import com.example.lootclicker.sandbox.StatModifier;

import java.util.ArrayList;
import java.util.List;

public class ItemModifiersConverter {
    @TypeConverter
    public String convertModifiersToString(List<StatModifier> modifiers){
        StringBuilder builder = new StringBuilder();

        for(StatModifier mod : modifiers){
            int targetedStat = mod.getTargetedStat();
            double value = mod.getValue();
            int isMultiplicative = mod.isMultiplicative() ? 1 : 0;
            builder.append(",").append(targetedStat).append(":").append(value).append(":").append(isMultiplicative);
        }
        builder = builder.delete(0,1);

        return builder.toString();
    }

    @TypeConverter
    public List<StatModifier> convertStringToModifiers(String string){
        List<StatModifier> modifiers = new ArrayList<>();
        if(string.isEmpty()){
            return modifiers;
        }
        String[] splitString = string.split(",");

        for(String values : splitString){
            String[] separation = values.split(":");
            int targetedStat = Integer.parseInt(separation[0]);
            double value = Double.parseDouble(separation[1]);
            boolean multi = Integer.parseInt(separation[2]) == 1;

            modifiers.add(new StatModifier(targetedStat, value, multi));
        }

        return modifiers;
    }
}
