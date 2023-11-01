package com.example.tcstest;

import com.itextpdf.io.image.ImageData;
import com.itextpdf.layout.borders.Border;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import com.example.tcstest.model.Item;
import javafx.collections.FXCollections;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.layout.property.UnitValue;
import com.itextpdf.layout.property.TextAlignment;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import java.io.File;
import com.itextpdf.io.image.ImageDataFactory;
import java.net.URL;



import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.font.PdfFont;

import java.io.FileOutputStream;
import java.io.IOException;

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
                if (item != null && Item.isSpecialEntry(item)) {
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
                return;  // User canceled the file chooser
            }

            PdfWriter writer = new PdfWriter(selectedFile.getAbsolutePath());
            PdfDocument pdfDocument = new PdfDocument(writer);
            Document document = new Document(pdfDocument);

            // Adding the image
            Image img = new Image("TCSlogo.png");
            ImageData imageData = ImageDataFactory.create(img.getUrl());
            com.itextpdf.layout.element.Image pdfImg = new com.itextpdf.layout.element.Image(imageData);

            // Address fields
            String[] addressLabels = {
                    "Company Name:", "Street Address:", "City/Province:",
                    "Postal Code:", "Phone Number:", "Email Address:"
            };

            String[] addressValues = {
                    companyField.getText(), streetField.getText(), cityField.getText(),
                    postalField.getText(), phoneField.getText(), emailField.getText()
            };

            // Using a table to align image and address side by side
            Table addressTable = new Table(new float[]{5, 1}); // 5 parts width for image, 1 part width for address
            addressTable.setWidth(UnitValue.createPercentValue(100));

            // Image cell (will span all the address lines)
            Cell imgCell = new Cell(addressLabels.length, 1);
            imgCell.add(pdfImg);
            imgCell.setBorder(Border.NO_BORDER);
            addressTable.addCell(imgCell);

            // Address cells
            for (int i = 0; i < addressLabels.length; i++) {
                Cell cell = new Cell();
                cell.add(new Paragraph(addressLabels[i] + " " + addressValues[i])
                        .setFixedLeading(10)); // Reduces vertical spacing
                cell.setBorder(Border.NO_BORDER);
                cell.setTextAlignment(TextAlignment.RIGHT);
                addressTable.addCell(cell);
            }

            document.add(addressTable);

            // Rest of the code for the items table
            Table pdfTable = new Table(itemTable.getColumns().size());
            pdfTable.setWidth(UnitValue.createPercentValue(100));

            PdfFont headerFont = PdfFontFactory.createFont("Helvetica-Bold");
            PdfFont cellFont = PdfFontFactory.createFont("Helvetica");

            for (TableColumn<Item, ?> col : itemTable.getColumns()) {
                Cell cell = new Cell();
                cell.add(new Paragraph(col.getText()).setFont(headerFont));
                cell.setBackgroundColor(ColorConstants.LIGHT_GRAY);
                cell.setTextAlignment(TextAlignment.CENTER);
                pdfTable.addCell(cell);
            }

            int rowNumber = 0;
            for (Item item : itemTable.getItems()) {
                rowNumber++;

                Cell nameCell = new Cell().add(new Paragraph(item.getName()).setFont(cellFont));
                Cell quantityCell = new Cell().add(new Paragraph(String.valueOf(item.getQuantity())).setFont(cellFont));
                Cell unitPriceCell = new Cell().add(new Paragraph(String.valueOf(item.getUnitPrice())).setFont(cellFont));
                Cell totalPriceCell = new Cell().add(new Paragraph(String.valueOf(item.getUnitPrice() * item.getQuantity())).setFont(cellFont));

                Cell[] cells = {nameCell, quantityCell, unitPriceCell, totalPriceCell};

                for (Cell cell : cells) {
                    if (rowNumber % 2 == 0) {
                        cell.setBackgroundColor(ColorConstants.LIGHT_GRAY);
                    } else {
                        cell.setBackgroundColor(ColorConstants.WHITE);
                    }
                    pdfTable.addCell(cell);
                }
            }

            document.add(pdfTable);
            document.close();

        } catch (IOException e) {
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
