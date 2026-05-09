package com.example.theplatinumloft;

import java.util.ArrayList;
import java.util.List;

public class Cart {
    private List<MenuItem> items;

    public Cart() {
        items = new ArrayList<>();
    }

    public void addItem(MenuItem item) {
        items.add(item);
    }

    public void removeItem(MenuItem item) {
        items.remove(item);
    }

    public List<MenuItem> getItems() {
        return items;
    }

    public double getTotalPrice() {
        double total = 0;
        for (MenuItem item : items) {
            total += item.getPrice();
        }
        return total;
    }

    public void clear() {
        items.clear();
    }
}
