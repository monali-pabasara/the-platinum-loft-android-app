package com.example.theplatinumloft;  // <- use your exact package

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.VH> {

    private final Context context;
    private final List<MenuItem> items;
    private final Runnable onChange; // callback to tell fragment to refresh total

    public CartAdapter(Context context, List<MenuItem> items, Runnable onChange) {
        this.context = context;
        this.items = items;
        this.onChange = onChange;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_cart, parent, false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VH h, int position) {
        MenuItem item = items.get(position);
        h.txtName.setText(item.getName());
        h.txtPrice.setText(String.format("$%.2f", item.getPrice()));
        h.img.setImageResource(item.getImageResId());

        h.btnRemove.setOnClickListener(v -> {
            int pos = h.getAdapterPosition();
            if (pos != RecyclerView.NO_POSITION) {
                // remove from the single source of truth
                CartManager.getInstance().getCartItems().remove(pos);
                notifyItemRemoved(pos);
                if (onChange != null) onChange.run();
            }
        });
    }

    @Override
    public int getItemCount() { return items.size(); }

    static class VH extends RecyclerView.ViewHolder {
        ImageView img;
        TextView txtName, txtPrice;
        Button btnRemove;

        VH(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.imgCartFood);
            txtName = itemView.findViewById(R.id.txtCartName);
            txtPrice = itemView.findViewById(R.id.txtCartPrice);
            btnRemove = itemView.findViewById(R.id.btnRemove);
        }
    }
}
