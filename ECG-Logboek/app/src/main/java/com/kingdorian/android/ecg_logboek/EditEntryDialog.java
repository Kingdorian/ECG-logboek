package com.kingdorian.android.ecg_logboek;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LayoutAnimationController;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by dorian on 11-5-16.
 */
public class EditEntryDialog extends DialogFragment {

    private HourEntry[] data = ActivityData.getData();

    private int hourId;

    public EditEntryDialog() {super();}

    public static EditEntryDialog newInstance(int id) {
        EditEntryDialog dialog = new EditEntryDialog();
        Bundle args = new Bundle();
        args.putInt("hourId", id);
        dialog.setArguments(args);

        return dialog;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        hourId = getArguments().getInt("hourId");
    }

    @Override
    public AlertDialog onCreateDialog(Bundle savedInstanceState) {

        System.out.println("Starting dialog!");
        final View promptView = getActivity().getLayoutInflater().inflate(R.layout.prompt, null);
        if(data[hourId]!=null) {
            ((TextView) promptView.findViewById(R.id.editTextDialogUserInput)).setText(data[hourId].getDescription());
        }
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        dialogBuilder.setView(promptView);
        dialogBuilder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String input = ((EditText) promptView.findViewById(R.id.editTextDialogUserInput)).getText().toString();
                if(data[hourId]==null){
                    data[hourId] = new HourEntry(hourId, "");
                }
                data[hourId].setDescription(input);
                MainActivity.adapter.notifyDataSetChanged();
            }
        });
        TextView subTitleView = (TextView)promptView.findViewById(R.id.subTitle);
        Resources res = getResources();
        // TODO change to string res
        String subTitle = res.getString(R.string.beforeTimeSubTitle);
        subTitle += ActivityData.getStartTime(hourId) + ":00" + res.getString(R.string.betweenTimeSubTitle);
        subTitle += ActivityData.getEndTime(hourId) + ":00" + res.getString(R.string.afterTimeSubTitle);
        subTitleView.setText(subTitle);

        return dialogBuilder.create();
    }

}
