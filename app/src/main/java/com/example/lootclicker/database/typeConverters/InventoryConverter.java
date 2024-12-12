package com.example.lootclicker.database.typeConverters;

import androidx.room.TypeConverter;

import java.util.ArrayList;

public class InventoryConverter {
    @TypeConverter
    public String convertInventoryToString(ArrayList<Integer> inventory){
        StringBuilder builder = new StringBuilder();

        for(int item : inventory) {
            builder.append(",").append(item);
        }
        builder = builder.delete(0,1);
        return builder.toString();
    }

    @TypeConverter
    public ArrayList<Integer> convertStringToInventory(String string){
        ArrayList<Integer> inventory = new ArrayList<>();
        if(string.isEmpty()){
            return inventory;
        }
        String[] itemIDs = string.split(",");
        for(String id : itemIDs){
            inventory.add(Integer.parseInt(id));
        }

        return inventory;
    }
}
