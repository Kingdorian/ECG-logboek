package com.kingdorian.android.ecg_logboek;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by dorian on 11-5-16.
 */
public class EditEntryDialog extends DialogFragment {

    private ActivityData data;
    private int id;

    public EditEntryDialog(ActivityData data, int id) {
        super();
        this.data = data;
        this.id = id;
    }
    public EditEntryDialog() {super();}

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final View promptView = getActivity().getLayoutInflater().inflate(R.layout.prompt, null);
        if(data.getData()[1]!=null) {
            ((TextView) promptView.findViewById(R.id.editTextDialogUserInput)).setText(data.getData()[id].getDescription());
        }
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        dialogBuilder.setView(promptView);
        dialogBuilder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String input = ((EditText) promptView.findViewById(R.id.editTextDialogUserInput)).getText().toString();
                if(data.getData()[id]==null){
                    data.getData()[id] = new HourEntry(id, "");
                }
                data.getData()[id].setDescription(input);
            }
        });
        TextView subTitleView = (TextView)promptView.findViewById(R.id.subTitle);
        Resources res = getResources();
        String subTitle = res.getString(R.string.beforeTimeSubTitle);
        subTitle += data.getStartTime(id) + ":00" + res.getString(R.string.betweenTimeSubTitle);
        subTitle += data.getEndTime(id) + ":00" + res.getString(R.string.afterTimeSubTitle);
        subTitleView.setText(subTitle);
        return dialogBuilder.create();

    }

}
