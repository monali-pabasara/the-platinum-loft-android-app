package com.example.theplatinumloft;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class TrackingFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_tracking, container, false);

        EditText etOrderId = v.findViewById(R.id.etOrderId);
        Button btnCheck    = v.findViewById(R.id.btnCheck);
        TextView txtStatus = v.findViewById(R.id.txtStatus);

        btnCheck.setOnClickListener(view -> {
            String id = etOrderId.getText().toString().trim();
            if (id.isEmpty()) {
                txtStatus.setText("Please enter an Order ID.");
            } else {
                // Mock statuses (rotate for demo)
                int mod = Math.abs(id.hashCode()) % 4;
                String status = (mod == 0) ? "Received"
                        : (mod == 1) ? "Preparing"
                        : (mod == 2) ? "Ready for pickup"
                        : "Completed";
                txtStatus.setText("Order " + id + ": " + status);
            }
        });

        return v;
    }
}
