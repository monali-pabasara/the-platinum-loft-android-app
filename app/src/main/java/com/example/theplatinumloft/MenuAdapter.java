package com.example.theplatinumloft;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.MenuViewHolder> {

    private final Context context;
    private final List<MenuItem> menuList;

    public MenuAdapter(Context context, List<MenuItem> menuList) {
        this.context = context;
        this.menuList = menuList;
    }

    @NonNull @Override
    public MenuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_menu, parent, false);
        return new MenuViewHolder(v);
    }

    @Override public void onBindViewHolder(@NonNull MenuViewHolder h, int position) {
        MenuItem item = menuList.get(position);

        h.txtName.setText(item.getName());
        h.txtDesc.setText(item.getDescription());
        h.imgFood.setImageResource(item.getImageResId());
        h.txtPrice.setText(String.format("$%.2f", item.getPrice()));

        // local state per row (reset on bind)
        final double basePrice = item.getPrice();
        h.qty = 1;
        h.chkAddon.setChecked(false);
        h.txtQty.setText("1");
        h.txtPrice.setText(String.format("$%.2f", basePrice));

        h.btnMinus.setOnClickListener(v -> {
            if (h.qty > 1) {
                h.qty--;
                h.txtQty.setText(String.valueOf(h.qty));
                double p = basePrice + (h.chkAddon.isChecked() ? 1.0 : 0.0);
                h.txtPrice.setText(String.format("$%.2f", p));
            }
        });

        h.btnPlus.setOnClickListener(v -> {
            h.qty++;
            h.txtQty.setText(String.valueOf(h.qty));
            double p = basePrice + (h.chkAddon.isChecked() ? 1.0 : 0.0);
            h.txtPrice.setText(String.format("$%.2f", p));
        });

        h.chkAddon.setOnCheckedChangeListener((b, checked) -> {
            double p = basePrice + (checked ? 1.0 : 0.0);
            h.txtPrice.setText(String.format("$%.2f", p));
        });

        h.btnAddCart.setOnClickListener(v -> {
            boolean addon = h.chkAddon.isChecked();
            double addonCost = addon ? 1.0 : 0.0;

            for (int i = 0; i < h.qty; i++) {
                // create a priced copy so cart can show correct amount
                MenuItem copy = new MenuItem(
                        item.getName() + (addon ? " +Extra" : ""),
                        item.getDescription(),
                        item.getCategory(),
                        item.getDietary(),
                        basePrice + addonCost,
                        item.getImageResId()
                );
                CartManager.getInstance().addToCart(copy);
            }
            Toast.makeText(context,
                    item.getName() + " x" + h.qty + (addon ? " (+Extra)" : "") + " added",
                    Toast.LENGTH_SHORT).show();
        });
    }

    @Override public int getItemCount() { return menuList.size(); }

    static class MenuViewHolder extends RecyclerView.ViewHolder {
        ImageView imgFood;
        TextView txtName, txtDesc, txtPrice, txtQty;
        ImageButton btnMinus, btnPlus;
        CheckBox chkAddon;
        Button btnAddCart;
        int qty = 1;

        MenuViewHolder(@NonNull View itemView) {
            super(itemView);
            imgFood  = itemView.findViewById(R.id.imgFood);
            txtName  = itemView.findViewById(R.id.txtFoodName);
            txtDesc  = itemView.findViewById(R.id.txtFoodDesc);
            txtPrice = itemView.findViewById(R.id.txtFoodPrice);
            txtQty   = itemView.findViewById(R.id.txtQty);
            chkAddon = itemView.findViewById(R.id.chkAddon);
            btnMinus = itemView.findViewById(R.id.btnMinus);
            btnPlus  = itemView.findViewById(R.id.btnPlus);
            btnAddCart = itemView.findViewById(R.id.btnAddCart);
        }
    }
}
