package com.kingdorian.android.ecg_logboek;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by dorian on 9-5-16.
 */
public class AcitvityData {

    private static ArrayList<HourEntry> data = new ArrayList<HourEntry>();


    public static ArrayList<HourEntry> getData() {
        return data;
    }

    public static void addHourEntry(HourEntry hr) {
        data.add(hr);
    }

    public static void writeData() {
        JSONArray dataArray = new JSONArray();
        JSONObject jsonObj = new JSONObject();
        for(HourEntry entry : data) {
            dataArray.put(entry.toJSON());
        }
        //TODO Write to file http://developer.android.com/guide/topics/data/data-storage.html
        System.out.println(dataArray.toString());
    }

}
