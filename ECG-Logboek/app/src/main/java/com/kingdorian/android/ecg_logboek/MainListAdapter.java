package com.kingdorian.android.ecg_logboek;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by dorian on 19-4-16.
 */
public class MainListAdapter extends ArrayAdapter<HourEntry> {
    Context context;
    ArrayList<HourEntry> entries;
    ActivityData data;
    private static LayoutInflater inflater;


    /**
     * Constructs a new ProjectListItemAdapter
     * @param context the context of the listitem
     * @param resource the id of the rersource
     * @param projects the projects the listitem has to represent
     */
    public MainListAdapter(Context context, int resource, ArrayList<HourEntry> projects, ActivityData data) {
        super(context, resource, projects);
        entries = projects;
        this.data = data;
    }
    public MainListAdapter(Context context, int resource){
        super(context, resource);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if(v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.listitem, null);
        }
        HourEntry p = getItem(position);

        if(p == null) return null;

        TextView descriptionView = (TextView) v.findViewById(R.id.description);

        if(descriptionView != null){
            descriptionView.setText(entries.get(position).getDescription()+"");
        }
        TextView timeView = (TextView) v.findViewById(R.id.time);

        if(timeView != null){
            timeView.setText(data.getStartTime(entries.get(position).getId())+":00-"+data.getEndTime(entries.get(position).getId())+":00");
        }
        return v;
    }

    public void setData(ArrayList<HourEntry> he) {
        entries = he;
        notifyDataSetChanged();
    }

}