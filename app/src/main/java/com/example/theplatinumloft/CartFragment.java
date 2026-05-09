package com.example.theplatinumloft;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CartFragment extends Fragment {

    private RecyclerView recyclerCart;
    private TextView txtSubtotal, txtDiscount, txtTotal;
    private Button btnClearCart, btnCheckout;
    private CartAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_cart, container, false);

        recyclerCart = v.findViewById(R.id.recyclerCart);
        txtSubtotal  = v.findViewById(R.id.txtSubtotal);
        txtDiscount  = v.findViewById(R.id.txtDiscount);
        txtTotal     = v.findViewById(R.id.txtTotal);
        btnClearCart = v.findViewById(R.id.btnClearCart);
        btnCheckout  = v.findViewById(R.id.btnCheckout);

        recyclerCart.setLayoutManager(new LinearLayoutManager(requireContext()));
        List<MenuItem> items = CartManager.getInstance().getCartItems();
        adapter = new CartAdapter(requireContext(), items, this::updateTotals);
        recyclerCart.setAdapter(adapter);

        updateTotals();

        btnClearCart.setOnClickListener(vw -> {
            if (items.isEmpty()) {
                Toast.makeText(requireContext(), "Cart is already empty", Toast.LENGTH_SHORT).show();
                return;
            }
            CartManager.getInstance().clearCart();
            adapter.notifyDataSetChanged();
            updateTotals();
        });

        btnCheckout.setOnClickListener(vw -> {
            if (items.isEmpty()) {
                Toast.makeText(requireContext(), "Your cart is empty", Toast.LENGTH_SHORT).show();
                return;
            }
            double subtotal = CartManager.getInstance().getSubtotal();
            double discount = CartManager.getInstance().getDiscount(); // 0 if unused
            double total    = CartManager.getInstance().getTotal();

            String msg = String.format(
                    "Subtotal: $%.2f\nDiscount: $%.2f\nTotal: $%.2f\n\nTap OK to continue.",
                    subtotal, discount, total
            );

            AlertDialog dlg = new AlertDialog.Builder(requireContext())
                    .setTitle("Checkout")
                    .setMessage(msg)
                    .setCancelable(false) // <-- stays until OK is tapped
                    .setPositiveButton("OK", (d, i) -> {
                        // Optional: clear cart AFTER confirming
                        // CartManager.getInstance().clearCart();
                        // adapter.notifyDataSetChanged();
                        // updateTotals();
                        d.dismiss();
                    })
                    .create();
            dlg.show();
        });

        return v;
    }

    private void updateTotals() {
        double subtotal = CartManager.getInstance().getSubtotal();
        double discount = CartManager.getInstance().getDiscount(); // keep 0 if not used
        double total    = CartManager.getInstance().getTotal();

        txtSubtotal.setText(String.format("Subtotal: $%.2f", subtotal));
        txtDiscount.setText(String.format("Discount: $%.2f", discount));
        txtTotal.setText(String.format("Total: $%.2f", total));
    }
}
