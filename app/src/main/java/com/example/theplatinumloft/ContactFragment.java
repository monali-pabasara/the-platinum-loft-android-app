package com.example.theplatinumloft;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class ContactFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_contact, container, false);

        Button btnCall = v.findViewById(R.id.btnCall);
        Button btnEmail = v.findViewById(R.id.btnEmail);

        // TODO: put your real phone/email here
        final String phone = "+1-555-123-4567";
        final String email = "hello@platinumloft.example";

        btnCall.setOnClickListener(view -> {
            Intent dial = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phone));
            startActivity(dial);
        });

        btnEmail.setOnClickListener(view -> {
            Intent mail = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:" + email));
            mail.putExtra(Intent.EXTRA_SUBJECT, "Inquiry from The Platinum Loft app");
            startActivity(Intent.createChooser(mail, "Send email"));
        });

        return v;
    }
}
