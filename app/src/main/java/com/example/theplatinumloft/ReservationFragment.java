package com.example.theplatinumloft;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.Calendar;
import java.util.Locale;

public class ReservationFragment extends Fragment {

    private EditText inputName, inputDate, inputTime, inputGuests, inputNotes;

    // Launcher to receive "OK" from confirm page and clear fields
    private final ActivityResultLauncher<Intent> confirmLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    clearFields();
                }
            });

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_reservation, container, false);

        inputName   = v.findViewById(R.id.inputName);
        inputDate   = v.findViewById(R.id.inputDate);
        inputTime   = v.findViewById(R.id.inputTime);
        inputGuests = v.findViewById(R.id.inputGuests);
        inputNotes  = v.findViewById(R.id.inputNotes);

        // Show date picker
        inputDate.setOnClickListener(view -> showDatePicker());
        // Show time picker
        inputTime.setOnClickListener(view -> showTimePicker());

        v.findViewById(R.id.btnReserve).setOnClickListener(view -> onReserveClicked());
        return v;
    }

    private void showDatePicker() {
        final Calendar c = Calendar.getInstance();
        int y = c.get(Calendar.YEAR);
        int m = c.get(Calendar.MONTH);
        int d = c.get(Calendar.DAY_OF_MONTH);

        new DatePickerDialog(requireContext(), (picker, yy, mm, dd) -> {
            // format yyyy-MM-dd
            String date = String.format(Locale.US, "%04d-%02d-%02d", yy, mm + 1, dd);
            inputDate.setText(date);
        }, y, m, d).show();
    }

    private void showTimePicker() {
        final Calendar c = Calendar.getInstance();
        int hh = c.get(Calendar.HOUR_OF_DAY);
        int mm = c.get(Calendar.MINUTE);

        new TimePickerDialog(requireContext(), (picker, hour, minute) -> {
            // format HH:mm
            String time = String.format(Locale.US, "%02d:%02d", hour, minute);
            inputTime.setText(time);
        }, hh, mm, true /* 24h */).show();
    }

    private void onReserveClicked() {
        String name   = inputName.getText().toString().trim();
        String date   = inputDate.getText().toString().trim();
        String time   = inputTime.getText().toString().trim();
        String guests = inputGuests.getText().toString().trim();
        String notes  = inputNotes.getText().toString().trim();

        // simple validation
        if (TextUtils.isEmpty(name))   { inputName.setError("Required"); return; }
        if (TextUtils.isEmpty(date))   { inputDate.setError("Required"); return; }
        if (TextUtils.isEmpty(time))   { inputTime.setError("Required"); return; }
        if (TextUtils.isEmpty(guests)) { inputGuests.setError("Required"); return; }

        // brief toast
        Toast.makeText(requireContext(), "Reservation saved", Toast.LENGTH_SHORT).show();

        // jump to confirmation; clear fields when we come back
        Intent i = new Intent(requireContext(), ReservationConfirmActivity.class);
        i.putExtra("name", name);
        i.putExtra("date", date);
        i.putExtra("time", time);
        i.putExtra("guests", guests);
        i.putExtra("notes", notes);
        confirmLauncher.launch(i);
    }

    private void clearFields() {
        inputName.setText("");
        inputDate.setText("");
        inputTime.setText("");
        inputGuests.setText("");
        inputNotes.setText("");
        inputName.clearFocus();
    }
}
