package com.example.theplatinumloft;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class MenuFragment extends Fragment {

    private RecyclerView recycler;
    private EditText edtSearch;
    private TabLayout tabs;

    private final List<MenuItem> fullList = new ArrayList<>();
    private final List<MenuItem> filtered = new ArrayList<>();
    private MenuAdapter adapter;

    @Nullable @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_menu, container, false);

        recycler = v.findViewById(R.id.recyclerMenu);
        edtSearch = v.findViewById(R.id.edtSearch);
        tabs = v.findViewById(R.id.tabs);

        recycler.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new MenuAdapter(requireContext(), filtered);
        recycler.setAdapter(adapter);

        setupTabs();
        seedMenuData();   // load your 10 foods
        applyFilter();    // show "All" by default

        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) { applyFilter(); }
            @Override public void afterTextChanged(Editable s) {}
        });

        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override public void onTabSelected(TabLayout.Tab tab) { applyFilter(); }
            @Override public void onTabUnselected(TabLayout.Tab tab) {}
            @Override public void onTabReselected(TabLayout.Tab tab) { applyFilter(); }
        });

        return v;
    }

    private void setupTabs() {
        tabs.addTab(tabs.newTab().setText("All"));
        tabs.addTab(tabs.newTab().setText("Breakfast"));
        tabs.addTab(tabs.newTab().setText("Lunch"));
        tabs.addTab(tabs.newTab().setText("Dinner"));
        tabs.addTab(tabs.newTab().setText("Beverages"));
        tabs.addTab(tabs.newTab().setText("Specials"));
        tabs.getTabAt(0).select();
    }

    /** Replace these with your real categories/descriptions/prices */
    private void seedMenuData() {
        // images already in res/drawable per your screenshot
        fullList.clear();

        fullList.add(new MenuItem("Breakfast Board",
                "Cheeses, fruits, breads & spreads.",
                "Breakfast", "", 19.95, R.drawable.breakfast_board));
        fullList.add(new MenuItem("Burger",
                "Juicy beef patty, cheese, house sauce.",
                "Lunch", "", 9.90, R.drawable.burger));
        fullList.add(new MenuItem("Pizza",
                "Stone-baked, mozzarella & basil.",
                "Dinner", "", 11.50, R.drawable.flatbread));
        fullList.add(new MenuItem("Pasta",
                "Creamy linguine with herbs.",
                "Dinner", "", 12.50, R.drawable.pasta));
        fullList.add(new MenuItem("Salmon",
                "Pan-seared salmon, lemon butter.",
                "Dinner", "Gluten-free", 14.95, R.drawable.salmon));
        fullList.add(new MenuItem("Moussaka",
                "Layers of eggplant & lamb, béchamel.",
                "Dinner", "", 13.95, R.drawable.moussaka));
        fullList.add(new MenuItem("Tagine",
                "Moroccan spiced lamb tagine.",
                "Specials", "", 22.95, R.drawable.tagine));
        fullList.add(new MenuItem("Tapas Trio",
                "Chef’s selection small plates.",
                "Specials", "Vegetarian", 16.50, R.drawable.tapas));
        fullList.add(new MenuItem("Dessert",
                "Assorted mini desserts.",
                "Beverages", "", 10.50, R.drawable.dessert));
        fullList.add(new MenuItem("Martini",
                "Classic martini (21+).",
                "Beverages", "", 12.00, R.drawable.martini));
    }

    private void applyFilter() {
        String q = edtSearch.getText().toString().trim().toLowerCase();
        String cat = tabs.getTabAt(tabs.getSelectedTabPosition()).getText().toString();

        filtered.clear();
        for (MenuItem m : fullList) {
            boolean catOk = cat.equals("All") || m.getCategory().equalsIgnoreCase(cat);
            boolean textOk = q.isEmpty()
                    || m.getName().toLowerCase().contains(q)
                    || m.getDescription().toLowerCase().contains(q)
                    || m.getDietary().toLowerCase().contains(q);
            if (catOk && textOk) filtered.add(m);
        }
        adapter.notifyDataSetChanged();
    }
}
