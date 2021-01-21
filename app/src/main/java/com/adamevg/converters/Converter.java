package com.adamevg.converters;

import androidx.room.TypeConverter;

import com.adamevg.pojo.Specialty;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Converter {
    @TypeConverter
    public String listSpecialityToString(List<Specialty> specialities) {
        return new Gson().toJson(specialities);
//        JSONArray jsonArray = new JSONArray();
//        for(Speciality speciality : specialities){
//            JSONObject jsonObject = new JSONObject();
//            try {
//                jsonObject.put("specialty_id",speciality.getSpecialtyId());
//                jsonObject.put("name",speciality.getName());
//                jsonArray.put(jsonObject);
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//        }
//        return jsonArray.toString();
    }

    @TypeConverter
    public List<Specialty> stringToListSpeciality(String specialitiesAsString) {
        Gson gson = new Gson();
        ArrayList objects = gson.fromJson(specialitiesAsString, ArrayList.class);
        ArrayList<Specialty> specialities = new ArrayList<>();
        for (Object object : objects) {
            specialities.add(gson.fromJson(object.toString(), Specialty.class));
        }
        return specialities;
    }
}
