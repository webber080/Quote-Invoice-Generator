package com.example.tcstest.model;

import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;

public class InvoicesRow {
    private final Hyperlink invoiceHyperlink;
    private final Label createdAt;
    private final Button deleteBtn;

    public InvoicesRow(String invoiceHyperlink, String createdAt) {
        this.invoiceHyperlink = new Hyperlink(invoiceHyperlink);
        this.createdAt = new Label(createdAt);
        this.deleteBtn = new Button("Delete");
    }

    // Getters for the property values (for TableView binding)
    public Hyperlink invoiceHyperlinkProperty() {
        return invoiceHyperlink;
    }
    public Label dateProperty() {
        return createdAt;
    }
    public Button deleteBtnProperty() {
        return deleteBtn;
    }


    // Regular getters and setters
//    public String getName() {
//        return name.get();
//    }
//    public void setName(String name) {
//        this.name.set(name);
//    }
//
//    public int getQuantity() {
//        return quantity.get();
//    }
//    public void setQuantity(int quantity) {
//        this.quantity.set(quantity);
//    }
//
//    public double getUnitPrice() {
//        return unitPrice.get();
//    }
//    public void setUnitPrice(double unitPrice) {
//        this.unitPrice.set(unitPrice);
//    }
}
