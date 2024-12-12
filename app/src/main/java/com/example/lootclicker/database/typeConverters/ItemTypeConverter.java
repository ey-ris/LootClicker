package com.example.lootclicker.database.typeConverters;

import androidx.room.TypeConverter;

import com.example.lootclicker.database.entities.Item;

public class ItemTypeConverter {
    @TypeConverter
    public String convertItemToString(Item item){
        StringBuilder builder = new StringBuilder();

        return builder.toString();
    }
}
