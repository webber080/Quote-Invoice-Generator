package com.example.tcstest.model;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Item {

    private final SimpleStringProperty name;
    private final SimpleIntegerProperty quantity;
    private final SimpleDoubleProperty unitPrice;

    public Item(String name, int quantity, double unitPrice) {
        this.name = new SimpleStringProperty(name);
        this.quantity = new SimpleIntegerProperty(quantity);
        this.unitPrice = new SimpleDoubleProperty(unitPrice);
    }

    // Getters for the property values (for TableView binding)
    public SimpleStringProperty nameProperty() {
        return name;
    }

    public SimpleIntegerProperty quantityProperty() {
        return quantity;
    }



    public SimpleDoubleProperty unitPriceProperty() {
        return unitPrice;
    }

    // Regular getters and setters
    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public int getQuantity() {
        return quantity.get();
    }

    public void setQuantity(int quantity) {
        this.quantity.set(quantity);
    }

    public boolean isSpecialEntry() {
        // Implement your logic to identify a special entry.
        // For example, you may decide that items with a specific name are special.
        return this.getName().equals("Tax") || this.getName().equals("Total");
    }

    // You will also need to implement the static methods taxEntry and totalEntry
    // if they are indeed used in your application logic.
    public static Item taxEntry(double taxAmount) {
        // Create a special entry for tax
        return new Item("Tax", 0, taxAmount);
    }

    public static Item totalEntry(double totalAmount) {
        // Create a special entry for total
        return new Item("Total", 0, totalAmount);
    }

    public double getUnitPrice() {
        return unitPrice.get();
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice.set(unitPrice);
    }

    // Method to calculate line total for an individual item
    public double getLineTotal() {
        return this.quantity.get() * this.unitPrice.get();
    }
}
