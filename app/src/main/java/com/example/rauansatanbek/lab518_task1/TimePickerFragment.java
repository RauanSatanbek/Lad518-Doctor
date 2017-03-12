package com.example.rauansatanbek.lab518_task1;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

/**
 * Created by Rauan Satanbek on 11.03.2017.
 */

public class TimePickerFragment extends DialogFragment implements AlertDialog.OnClickListener{
    TimePicker timePicker;
    View view; EditText doctorFromTime,doctorToTime;
    TextView respond;
    final int FROM_TIME = 1, TO_TIME = 2;
    EditText time;
    TimePickerFragment(EditText time, EditText doctorFromTime, EditText doctorToTime, TextView respond) {
        this.time = time;
        this.doctorFromTime = doctorFromTime;
        this.doctorToTime = doctorToTime;
        this.respond = respond;
    }
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        view = inflater.inflate(R.layout.time_picker, null);
        builder.setView(view);
        builder.setPositiveButton("Ок", this);
        builder.setNegativeButton("Отмена", this);
        return builder.create();
    }

    @Override
    public void onStart() {
        super.onStart();
        ((AlertDialog) getDialog()).getButton(DialogInterface.BUTTON_POSITIVE).setTextColor(Color.parseColor("#4E1686"));
        ((AlertDialog) getDialog()).getButton(DialogInterface.BUTTON_NEGATIVE).setTextColor(Color.parseColor("#4E1686"));
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        switch (which) {
            case -1:
                timePicker = (TimePicker) view.findViewById(R.id.timePicker);
                int hour = timePicker.getCurrentHour();
                int minute = timePicker.getCurrentMinute();
                time.setText(addZero(hour) + ":" + addZero(minute));
                Log.d("MyLogs", "HII");
                if(!doctorFromTime.getText().toString().equals("") && !doctorToTime.getText().toString().equals("")) {
                    respond.setBackgroundResource(R.drawable.btn_price);
                    respond.setLinksClickable(true);
                }
                break;
            case -2:
                break;
        }
    }
    //10:1 -> 10:01
        public String addZero(int number) {
            if(number <= 9 && number >= 0) return "0" + number;
            return "" + number;
        }
}