package com.kingdorian.android.ecg_logboek;

import org.json.JSONObject;

import java.util.Date;

/**
 * Created by dorian on 9-5-16.
 */
public class HourEntry {

    private int id;
    private String description;

    public HourEntry(int id, String description) {
        this.id = id;
        this.description = description;
    }

    public String toJSON() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("id", id);
            jsonObject.put("description", description);
        } catch(Exception e) {
            e.printStackTrace();
        }
        return jsonObject.toString();
    }

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public void setId() {
        this.id = id;
    }

    public void setDescription(String description) {
        this.description = description;
    }


}
