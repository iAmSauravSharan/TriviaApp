package com.sauravsharan.appscrip.data.util

import androidx.room.TypeConverter
import com.google.gson.Gson

import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type


class MapConverter {

    @TypeConverter
    fun fromString(value: String?): HashMap<Long?, String?>? {
        val mapType: Type = object :
            TypeToken<HashMap<Long?, String?>?>() {}.type
        return Gson().fromJson<HashMap<Long?, String?>>(
            value,
            mapType
        )
    }

    @TypeConverter
    fun fromStringMap(map: HashMap<Long?, String?>?): String? {
        val gson = Gson()
        return gson.toJson(map)
    }

}