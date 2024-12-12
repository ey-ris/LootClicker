package com.example.lootclicker.database.typeConverters;

import androidx.room.TypeConverter;

import java.util.ArrayList;

public class InventoryConverter {
    @TypeConverter
    public String convertInventoryToString(ArrayList<Integer> inventory){
        StringBuilder builder = new StringBuilder();

        for(int item : inventory){
            builder.append(item).append(",");
        }

        return builder.toString();
    }

    @TypeConverter
    public ArrayList<Integer> convertStringToInventory(String string){
        String[] itemIDs = string.split(",");
        ArrayList<Integer> inventory = new ArrayList<>();
        for(String id : itemIDs){
            inventory.add(Integer.parseInt(id));
        }

        return inventory;
    }
}
