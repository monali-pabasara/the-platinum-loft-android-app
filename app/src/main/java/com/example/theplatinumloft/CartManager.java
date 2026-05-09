package com.example.theplatinumloft;

import java.util.ArrayList;
import java.util.List;

/**
 * Single source of truth for the cart.
 * Provides clear(), clearCart() (wrapper), totals, and simple discount handling.
 */
public class CartManager {

    private static CartManager instance;

    // Live list used by adapters and fragments
    private final List<MenuItem> cartItems = new ArrayList<>();

    // Optional percentage discount (0.0 to 1.0). Default 0%.
    private double discountRate = 0.0;

    private CartManager() { }

    public static synchronized CartManager getInstance() {
        if (instance == null) instance = new CartManager();
        return instance;
    }

    // ----------------- CRUD -----------------

    public void addToCart(MenuItem item) {
        if (item != null) cartItems.add(item);
    }

    public void removeAt(int position) {
        if (position >= 0 && position < cartItems.size()) {
            cartItems.remove(position);
        }
    }

    /** Returns the live list used by the adapter */
    public List<MenuItem> getCartItems() {
        return cartItems;
    }

    public boolean isEmpty() {
        return cartItems.isEmpty();
    }

    /** New canonical clear */
    public void clear() {
        cartItems.clear();
    }

    /** Option A: compatibility wrapper so existing code calling clearCart() still works */
    public void clearCart() {
        clear();
    }

    // --------------- Totals -----------------

    public double getSubtotal() {
        double sum = 0.0;
        for (MenuItem m : cartItems) {
            sum += m.getPrice();   // assumes MenuItem#getPrice() exists
        }
        return sum;
    }

    /** Returns the monetary discount. Default 0 unless you set a rate. */
    public double getDiscount() {
        return getSubtotal() * discountRate;
    }

    public double getTotal() {
        return getSubtotal() - getDiscount();
    }

    // ------------- Discount controls (optional) -------------

    /** rate is clamped to [0,1] (e.g., 0.10 for 10% off) */
    public void setDiscountRate(double rate) {
        if (rate < 0) rate = 0;
        if (rate > 1) rate = 1;
        this.discountRate = rate;
    }

    public double getDiscountRate() {
        return discountRate;
    }
}
