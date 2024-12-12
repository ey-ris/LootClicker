package com.example.lootclicker.database.typeConverters;

import androidx.room.TypeConverter;

import com.example.lootclicker.database.AppDatabase;
import com.example.lootclicker.database.AppRepository;
import com.example.lootclicker.database.entities.Item;

import java.util.HashMap;

public class EquipmentConverter {
    @TypeConverter
    public String convertEquipmentToString(HashMap<Integer, Integer> equipment){
        StringBuilder builder = new StringBuilder();
        for(Integer key : equipment.keySet()){
            builder.append(key).append(":").append(equipment.get(key)).append(";");
        }
        return builder.toString();
    }

    @TypeConverter
    public HashMap<Integer, Integer> convertStringToEquipment(String string){
        String[] separate = string.split(";");
        HashMap<Integer, Integer> equipment = new HashMap<>();
        for(var s : separate){
            String[] kvp = s.split(":");
            int slot = Integer.parseInt(kvp[0]);
            int itemId = Integer.parseInt(kvp[1]);
            equipment.put(slot, itemId);
        }
        return equipment;
    }
}
