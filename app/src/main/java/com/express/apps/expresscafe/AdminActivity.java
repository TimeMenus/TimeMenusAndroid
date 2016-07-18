package com.express.apps.expresscafe;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.view.View.OnClickListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class AdminActivity extends Activity implements OnClickListener {

    private TextView dateView;
    private DatePickerDialog datePickerDialog;
    private SimpleDateFormat dateFormatter;
    private EditText itemName;
    private EditText itemDesc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        //for menu drop down item
        Spinner countryView = (Spinner) findViewById(R.id.menuitems);

        // Create an adapter from the string array resource and use
        // spinner_textview_align.xml for aligining and formatting
        ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.menu_arrays, R.layout.spinner_textview_align);
        // Set the layout to use for each dropdown item
        adapter.setDropDownViewResource(R.layout.spinner_textview_align);

        countryView.setAdapter(adapter);

//        For the calendar and date
        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);

        dateView = (TextView) findViewById(R.id.picked_date);

        setDateTimeField();

    }

    private void setDateTimeField() {
        dateView.setOnClickListener(this);
        Calendar newCalendar = Calendar.getInstance();
        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                dateView.setText(dateFormatter.format(newDate.getTime()));
            }
        }, newCalendar.get(Calendar.YEAR),newCalendar.get(Calendar.MONTH),newCalendar.get(Calendar.DAY_OF_MONTH));
    }

    @Override
    public void onClick(View view) {
        if(view == dateView) {
            datePickerDialog.show();
        }
    }

    //for text boxes


}