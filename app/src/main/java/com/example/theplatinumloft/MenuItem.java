package com.example.theplatinumloft;

public class MenuItem {
    private final String name;
    private final String description;
    private final String category;   // Breakfast, Lunch, Dinner, Beverages, Specials
    private final String dietary;    // e.g., Vegetarian, Gluten-free (optional)
    private final double price;
    private final int imageResId;

    public MenuItem(String name, String description, String category, String dietary,
                    double price, int imageResId) {
        this.name = name;
        this.description = description;
        this.category = category;
        this.dietary = dietary;
        this.price = price;
        this.imageResId = imageResId;
    }

    public String getName() { return name; }
    public String getDescription() { return description; }
    public String getCategory() { return category; }
    public String getDietary() { return dietary; }
    public double getPrice() { return price; }
    public int getImageResId() { return imageResId; }
}
