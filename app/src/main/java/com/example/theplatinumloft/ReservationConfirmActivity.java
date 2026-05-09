package com.example.theplatinumloft;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class ReservationConfirmActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation_confirm);

        // Pull data
        String name   = getIntent().getStringExtra("name");
        String date   = getIntent().getStringExtra("date");
        String time   = getIntent().getStringExtra("time");
        String guests = getIntent().getStringExtra("guests");
        String notes  = getIntent().getStringExtra("notes");

        ((TextView)findViewById(R.id.txtName)).setText("Name: " + safe(name));
        ((TextView)findViewById(R.id.txtDate)).setText("Date: " + safe(date));
        ((TextView)findViewById(R.id.txtTime)).setText("Time: " + safe(time));
        ((TextView)findViewById(R.id.txtGuests)).setText("Guests: " + safe(guests));
        ((TextView)findViewById(R.id.txtNotes)).setText("Notes: " + (isEmpty(notes) ? "-" : notes));

        Button btnOk = findViewById(R.id.btnOk);
        btnOk.setOnClickListener(v -> {
            setResult(Activity.RESULT_OK); // tell fragment to clear fields
            finish(); // go back to Reservation page
        });
    }

    private boolean isEmpty(String s) { return s == null || s.trim().isEmpty(); }
    private String safe(String s) { return isEmpty(s) ? "-" : s; }
}
