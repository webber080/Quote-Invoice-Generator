package com.example.tcstest;

import com.itextpdf.io.image.ImageData;
import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.property.AreaBreakType;
import com.itextpdf.layout.property.VerticalAlignment;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import com.example.tcstest.model.Item;
import javafx.collections.FXCollections;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.layout.property.UnitValue;
import com.itextpdf.layout.property.TextAlignment;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;

import java.io.*;

import com.itextpdf.io.image.ImageDataFactory;

import java.net.URL;
import java.util.stream.Stream;


import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.font.PdfFont;

public class YourController {

    public Label newItemLabel;
    @FXML
    private TableView<Item> itemTable;
    @FXML
    private TableColumn<Item, String> nameColumn;
    @FXML
    private TableColumn<Item, Integer> quantityColumn;
    @FXML
    private TableColumn<Item, Double> unitPriceColumn;
    @FXML
    private TableColumn<Item, Double> totalPriceColumn;
    @FXML
    private TextField nameField;
    @FXML
    private TextField quantityField;
    @FXML
    private TextField unitPriceField;
    @FXML
    private Button addButton;
    @FXML
    private Button exportButton;
    @FXML
    private TextField companyField;
    @FXML
    private TextField streetField;
    @FXML
    private TextField cityField;
    @FXML
    private TextField postalField;
    @FXML
    private TextField phoneField;
    @FXML
    private TextField emailField;
    @FXML
    private ImageView tcsLogoImageView;

    @FXML
    public void initialize() {
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        unitPriceColumn.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        itemTable.setItems(FXCollections.observableArrayList());
        totalPriceColumn.setCellValueFactory(cellData -> cellData.getValue().unitPriceProperty().multiply(cellData.getValue().quantityProperty()).asObject());
        itemTable.setRowFactory(tv -> new TableRow<Item>() {
            @Override
            protected void updateItem(Item item, boolean empty) {
                super.updateItem(item, empty);
                if (item != null && item.isSpecialEntry()) { // Call the method on the 'item' instance
                    setStyle("-fx-background-color: lightgray;");
                } else {
                    setStyle("");
                }
            }
        });

    }

    @FXML
    public void handleAddItemButton() {
        String name = nameField.getText();
        if (name.isEmpty() || !isNumeric(quantityField.getText()) || !isNumeric(unitPriceField.getText())) {
            return;
        }
        int quantity = Integer.parseInt(quantityField.getText());
        double unitPrice = Double.parseDouble(unitPriceField.getText());

        Item newItem = new Item(name, quantity, unitPrice);
        itemTable.getItems().add(newItem);
        itemTable.refresh();

        nameField.clear();
        quantityField.clear();
        unitPriceField.clear();
        itemTable.getItems().removeIf(Item::isSpecialEntry);

        double subtotal = itemTable.getItems().stream().mapToDouble(item -> item.getUnitPrice() * item.getQuantity()).sum();
        double tax = subtotal * 0.13;
        double totalWithTax = subtotal + tax;

        itemTable.getItems().addAll(
                Item.taxEntry(tax),
                Item.totalEntry(totalWithTax)
        );

        itemTable.refresh();
        updateTotalPrice();
    }

    private void updateTotalPrice() {
        double subtotal = itemTable.getItems().stream().mapToDouble(item -> item.getUnitPrice() * item.getQuantity()).sum();
        double tax = subtotal * 0.13;
        double totalWithTax = subtotal + tax;
    }

    public void exportToPDF() {
        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save PDF");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF Files", "*.pdf"));
            File selectedFile = fileChooser.showSaveDialog(itemTable.getScene().getWindow());

            if (selectedFile == null) {
                return; // User canceled the file chooser
            }

            PdfWriter writer = new PdfWriter(selectedFile.getAbsolutePath());
            PdfDocument pdfDocument = new PdfDocument(writer);
            Document document = new Document(pdfDocument);

            // Set the document to landscape orientation and margins to 0
            pdfDocument.setDefaultPageSize(new PageSize(PageSize.A4));
            document.setMargins(0, 0, 0, 0);

            // Initialize the header table with 1:2 ratio for logo to company details
            float[] columnWidths = {1, 2};
            Table headerTable = new Table(UnitValue.createPercentArray(columnWidths));
            headerTable.setWidth(UnitValue.createPercentValue(100)); // Table uses the full width of the page
            Color yellowColor = new DeviceRgb(255, 199, 51); // RGB equivalent of FFC733

// Set the background color for the header table using the new Color object
            headerTable.setBackgroundColor(yellowColor);

            // Load the company logo
            ImageData imageData = ImageDataFactory.create(getClass().getResource("/TCSlogo.png").toExternalForm());
            Image pdfImg = new Image(imageData);

            // Create a cell for the logo with padding and no border
            Cell logoCell = new Cell().add(pdfImg.setAutoScale(true)).setBorder(Border.NO_BORDER);
            logoCell.setPaddingLeft(20); // Set your left padding
            logoCell.setPaddingTop(20); // Set your top padding
            logoCell.setPaddingBottom(20); // Set your bottom padding

            // Add the logo cell to the header table
            headerTable.addCell(logoCell);

            // Company Info cell with padding and no border
            Paragraph companyInfo = new Paragraph("REDACTED")
                    .setMultipliedLeading(1.0f);
            Cell companyInfoCell = new Cell().add(companyInfo).setBorder(Border.NO_BORDER);
            companyInfoCell.setPaddingRight(20); // Set your right padding
            companyInfoCell.setPaddingTop(20); // Set your top padding
            companyInfoCell.setPaddingBottom(20); // Set your bottom padding

            // Add the company info cell to the header table
            headerTable.addCell(companyInfoCell);

            // Add the header table to the document
            document.add(headerTable);

// Now set the margins for the new area where the rest of the content will be placed
            document.setMargins(50, 50, 50, 50);
            // Add customer info below company info
            Paragraph customerInfo = new Paragraph()
                    .add("Bill To:\n")
                    .add(companyField.getText() + "\n")
                    .add(streetField.getText() + "\n")
                    .add(cityField.getText() + "\n")
                    .add(postalField.getText() + "\n")
                    .add(phoneField.getText() + "\n")
                    .add(emailField.getText() + "\n")
                    .setBold()
                    .setMarginTop(10); // Space above customer info
            document.add(customerInfo);

            // Add a line break
            document.add(new Paragraph("\n"));

            // Generate table with item details
            float[] newColumnWidths = {5, 1, 1, 1}; // Adjust column widths as necessary
            Table itemDetailsTable = new Table(newColumnWidths);
            itemDetailsTable.setWidth(UnitValue.createPercentValue(100));

            // Add headers
            Stream.of("Item", "Quantity", "Price", "Total Price").forEach(headerTitle -> {
                Cell header = new Cell();
                header.setBackgroundColor(ColorConstants.LIGHT_GRAY);
                header.add(new Paragraph(headerTitle));
                itemDetailsTable.addHeaderCell(header);
            });

            // Add item rows
            for (Item item : itemTable.getItems()) {
                itemDetailsTable.addCell(new Cell().add(new Paragraph(item.getName())));
                itemDetailsTable.addCell(new Cell().add(new Paragraph(String.valueOf(item.getQuantity()))));
                itemDetailsTable.addCell(new Cell().add(new Paragraph(String.format("$%.2f", item.getUnitPrice()))));
                itemDetailsTable.addCell(new Cell().add(new Paragraph(String.format("$%.2f", item.getLineTotal()))));
            }

            document.add(itemDetailsTable);

            // Calculate the subtotal, tax, and total
            double subtotal = itemTable.getItems().stream().mapToDouble(Item::getLineTotal).sum();
            double tax = subtotal * 0.13;
            double total = subtotal + tax;

            // Add subtotal, tax, and total rows to the document
            Table totalTable = new Table(UnitValue.createPercentArray(new float[]{2, 1}));
            totalTable.setWidth(UnitValue.createPercentValue(100)).setMarginTop(10);

            totalTable.addCell(new Cell().add(new Paragraph("Subtotal")));
            totalTable.addCell(new Cell().add(new Paragraph(String.format("$%.2f", subtotal))));
            totalTable.addCell(new Cell().add(new Paragraph("Tax (13%)")));
            totalTable.addCell(new Cell().add(new Paragraph(String.format("$%.2f", tax))));
            totalTable.addCell(new Cell().add(new Paragraph("Total")));
            totalTable.addCell(new Cell().add(new Paragraph(String.format("$%.2f", total))));

            document.add(totalTable);

            // Optionally, add footer content
            Paragraph footer = new Paragraph("Thank you for your business!")
                    .setTextAlignment(TextAlignment.CENTER)
                    .setMarginTop(20);
            document.add(footer);

            // Close the document
            document.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }






    private boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
