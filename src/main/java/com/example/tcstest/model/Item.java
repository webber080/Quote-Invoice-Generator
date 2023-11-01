package com.example.tcstest.model;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Item {

    private final SimpleStringProperty name;
    private final SimpleIntegerProperty quantity;
    private final SimpleDoubleProperty unitPrice;
    private static final String TAX_LABEL = "Tax (13%)";
    private static final String TOTAL_LABEL = "Total (with Tax)";



    public Item(String name, int quantity, double unitPrice) {
        this.name = new SimpleStringProperty(name);
        this.quantity = new SimpleIntegerProperty(quantity);
        this.unitPrice = new SimpleDoubleProperty(unitPrice);
    }
    public static boolean isSpecialEntry(Item item) {
        return TAX_LABEL.equals(item.getName()) || TOTAL_LABEL.equals(item.getName());
    }

    public static Item taxEntry(double taxAmount) {
        return new Item(TAX_LABEL, 1, taxAmount);
    }

    public static Item totalEntry(double totalAmount) {
        return new Item(TOTAL_LABEL, 1, totalAmount);
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

    public double getUnitPrice() {
        return unitPrice.get();
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice.set(unitPrice);
    }
}
