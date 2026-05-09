package com.example.theplatinumloft;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.appbar.MaterialToolbar;

public class MainActivity extends AppCompatActivity {

    private MaterialToolbar toolbar;
    private View homePanel;
    private View fragmentContainer;

    // rows
    private LinearLayout rowMenu, rowCart, rowReservation, rowTracking, rowContact;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("The Platinum Loft");
        toolbar.setNavigationIcon(null); // no back arrow on home

        homePanel = findViewById(R.id.homePanel);
        fragmentContainer = findViewById(R.id.fragment_container);

        rowMenu        = findViewById(R.id.rowMenu);
        rowCart        = findViewById(R.id.rowCart);
        rowReservation = findViewById(R.id.rowReservation);
        rowTracking    = findViewById(R.id.rowTracking);
        rowContact     = findViewById(R.id.rowContact);

        rowMenu.setOnClickListener(v -> openFragment(new MenuFragment(), "Menu"));
        rowCart.setOnClickListener(v -> openFragment(new CartFragment(), "Cart"));
        rowReservation.setOnClickListener(v -> openFragment(new ReservationFragment(), "Reservation"));
        rowTracking.setOnClickListener(v -> openFragment(new TrackingFragment(), "Tracking Order"));
        rowContact.setOnClickListener(v -> openFragment(new ContactFragment(), "Contact"));

        // When back stack changes, decide whether to show home or a back arrow
        getSupportFragmentManager().addOnBackStackChangedListener(this::updateUiForBackstack);
    }

    private void openFragment(Fragment fragment, String title) {
        homePanel.setVisibility(View.GONE);
        fragmentContainer.setVisibility(View.VISIBLE);

        toolbar.setTitle(title);
        // show back arrow to return to Home
        toolbar.setNavigationIcon(androidx.appcompat.R.drawable.abc_ic_ab_back_material);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(title)
                .commit();
    }

    private void updateUiForBackstack() {
        int count = getSupportFragmentManager().getBackStackEntryCount();
        if (count == 0) {
            // we are on HOME
            fragmentContainer.setVisibility(View.GONE);
            homePanel.setVisibility(View.VISIBLE);
            toolbar.setTitle("The Platinum Loft");
            toolbar.setNavigationIcon(null);
        } else {
            // inside a fragment
            toolbar.setNavigationIcon(androidx.appcompat.R.drawable.abc_ic_ab_back_material);
        }
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
    }
}
