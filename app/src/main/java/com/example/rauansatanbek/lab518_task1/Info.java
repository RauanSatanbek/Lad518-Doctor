package com.example.rauansatanbek.lab518_task1;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Rauan Satanbek on 11.03.2017.
 */

public class Info {
    String  added_at, additional_info, address, comment, medtest, sex, time_from, time_to, when_date;
    boolean approved, is_child, is_loop;
    int answers_count, city, client, id, price, symptom, type, procedure;
    ArrayList<String> districts;
    ArrayList<String> specialities;
    Info(JSONObject obj) throws JSONException {
        districts = new ArrayList<>();
        specialities = new ArrayList<>();
        added_at = obj.getString("added_at");
        additional_info = obj.getString("additional_info");
        address = obj.getString("address");
        comment = obj.getString("comment");
        medtest = obj.getString("medtest");
        sex = obj.getString("sex");
        time_from = obj.getString("time_from");
        time_to = obj.getString("time_to");
        when_date = obj.getString("when_date");

        approved = obj.getBoolean("approved");
        is_child = obj.getBoolean("is_child");
        is_loop = obj.getBoolean("is_loop");

        JSONArray districts_array = obj.getJSONArray("districts");
        for(int j = 0; j < districts_array.length(); j++) {
            districts.add(districts_array.get(j).toString());
        }
        JSONArray specialities_array = obj.getJSONArray("specialities");
        for(int j = 0; j < specialities_array.length(); j++) {
            specialities.add(specialities_array.get(j).toString());
        }
        answers_count = isNull(obj.getString("answers_count"));
        city = isNull(obj.getString("city"));
        client = isNull(obj.getString("client"));
        id = isNull(obj.getString("id"));
        price = isNull(obj.getString("price"));
        answers_count = isNull(obj.getString("answers_count"));
        symptom = isNull(obj.getString("symptom"));
        type = isNull(obj.getString("type"));
        procedure = isNull(obj.getString("procedure"));

        Log.d("MyLogs", "type = " + type);
    }

    int isNull(String str) {
        return str.equals("null") ? -1 : Integer.parseInt(str);
    }

}
