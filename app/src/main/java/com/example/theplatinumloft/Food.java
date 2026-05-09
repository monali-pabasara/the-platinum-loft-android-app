package com.example.theplatinumloft;

public class Food {
    private String name;
    private String description;
    private double price;
    private int imageResId;  // store image from drawable

    public Food(String name, String description, double price, int imageResId) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.imageResId = imageResId;
    }

    // Getters
    public String getName() { return name; }
    public String getDescription() { return description; }
    public double getPrice() { return price; }
    public int getImageResId() { return imageResId; }
}
