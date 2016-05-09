package com.kingdorian.android.ecg_logboek;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created by dorian on 9-5-16.
 */
public class AcitvityData {

    private ArrayList<HourEntry> data = new ArrayList<HourEntry>();

    String FILENAME = "activityData";



    public ArrayList<HourEntry> getData() {
        return data;
    }

    public void addHourEntry(HourEntry hr) {
        data.add(hr);
    }

    public void writeData(Context ctx) {

        //TODO Write to file http://developer.android.com/guide/topics/data/data-storage.html
        try {
            FileOutputStream fos = ctx.openFileOutput(FILENAME, ctx.MODE_PRIVATE);
            fos.write(getDataJSON().getBytes());
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void readData(Context ctx) {
        try {
            FileInputStream fis = ctx.openFileInput(FILENAME);
            InputStreamReader isr = new InputStreamReader();
            BufferedReader buffered = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String line;
            while((line = buffered.readLine()) != null) {
                sb.append(line);
            }
            buffered.close();
            isr.close();
            System.out.println(sb.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getDataJSON() {
        JSONArray dataArray = new JSONArray();
        for(HourEntry entry : data) {
            dataArray.put(entry.toJSON());
        }
        System.out.println(dataArray.toString());
        return dataArray.toString();
    }

}
