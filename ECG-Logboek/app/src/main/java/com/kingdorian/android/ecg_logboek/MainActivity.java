package com.kingdorian.android.ecg_logboek;

import android.app.AlertDialog;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        ListView lView = (ListView) findViewById(R.id.actList);
//        String[] data = {"Ding1", "Ding2", "Nog een ding", "Ander ding?", "Thingy", "Optie"};
//        final CheckBoxArrayAdapter adapter = new CheckBoxArrayAdapter(this, R.layout.listitem, data);
//        System.out.println("hey1");
//        lView.setAdapter(adapter);

        LayoutInflater li = LayoutInflater.from(this);
        View promptView = li.inflate(R.layout.prompt, null);
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        dialogBuilder.setView(promptView);
        dialogBuilder.setCancelable(false);
        dialogBuilder.setPositiveButton("ok", null);

        AlertDialog dialog = dialogBuilder.create();
        dialog.show();

    }
    @Override
    protected void onPause() {
        super.onPause();
    }
    private class CheckBoxArrayAdapter extends ArrayAdapter<String> {

        Context context;
        String[] data;
        int viewResId;
        private LayoutInflater inflater = null;

        public CheckBoxArrayAdapter(Context context, int textViewResourceId, String[] data){
            super(context, textViewResourceId);
            System.out.println("hey3");
            this.context = context;
            this.data = data;
            this.viewResId = textViewResourceId;
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);


        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            System.out.println("hey2");
            View view = convertView;
            if (view == null ) {
                view = inflater.inflate(viewResId, null);
            }
            TextView text = (TextView)view.findViewById(R.id.name);
            text.setText(data[position]);
            return view;
        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return data.length;
        }

        @Override
        public String getItem(int position) {
            // TODO Auto-generated method stub
            return data[position];
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return position;
        }


    }

}
