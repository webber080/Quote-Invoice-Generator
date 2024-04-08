package com.example.tcstest;

import com.example.tcstest.db_stuff.entity.InvoicesInfo;
import com.example.tcstest.db_stuff.entity.Users;
import com.example.tcstest.db_stuff.service.UsersService;
import com.example.tcstest.errors.ErrorModal;
import com.example.tcstest.helpers.invoiceAddedModal;
import com.example.tcstest.helpers.switchScene;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.property.AreaBreakType;
import com.itextpdf.layout.property.VerticalAlignment;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;

import java.io.*;

import com.itextpdf.io.image.ImageDataFactory;

import java.net.URL;
import java.util.Optional;
import java.util.stream.Stream;


import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.font.PdfFont;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.client.RestTemplate;

public class YourController {
    // This TableView will contain rows of type "Item" (custom class), and each row in the table represents an instance of the Item class.
    @FXML
    private TableView<Item> itemTable;

    // These TableColumn instances are parameterized with two types: Item and String/Integer/Double.
    // The first type parameter (Item) specifies the type of the items displayed in the TableView (i.e., the type of the rows).
    // The second type parameter (String/Integer/Double) specifies the type of data displayed in the column.
    // In the first case, the "nameColumn" displays String data, which corresponds to the name property of the Item class.
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

    // CUSTOMER/USER
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

    // COMPANY
    @FXML
    private TextField cCompanyField;
    @FXML
    private TextField cStreetField;
    @FXML
    private TextField cCityField;
    @FXML
    private TextField cPostalField;
    @FXML
    private TextField cPhoneField;
    @FXML
    private TextField cEmailField;
    @FXML
    private ImageView tcsLogoImageView;
    @FXML
    private ColorPicker headerColorPicker;
    private Color headerColor;


    @FXML
    public void initialize() {
        headerColor = new DeviceRgb(255, 199, 51);

        // When calling "new PropertyValueFactory<>("propertyName")", you're creating a PropertyValueFactory instance that will retrieve the value of the property with the specified name from each "Item" item in the TableView and display it in the corresponding TableColumn.
        // For example, the first line sets up the nameColumn to display the value of the "name" property from each Item object in the TableView.
        // When the TableView is populated with Item objects, the nameColumn will automatically display the "name" property value of each Item in its cells.
        // NOTE: The property names specified must correspond to the actual property names in the "Item" class
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        unitPriceColumn.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));

        // This callback (inside parentheses) is invoked by JavaFX whenever it needs to determine the value to display in a cell of the totalPriceColumn TableColumn
        totalPriceColumn.setCellValueFactory(cellData -> cellData.getValue().unitPriceProperty().multiply(cellData.getValue().quantityProperty()).asObject());

        // FXCollections.observableArrayList() creates an empty observable list.
        // This empty list is then set as the items of the itemTable, effectively clearing any existing items that might have been present in the table.
        itemTable.setItems(FXCollections.observableArrayList());

        // "TableRow" is a normal class
        // When rows are added, updated, or removed in the TableView, the updateItem method is called for each row affected by the change.
        // While "new TableRow<Item>() { ... }" involves creating a new instance of TableRow<Item>, it's not creating new rows in the TableView. Instead, it's defining the behavior and appearance of each existing row.
        // The TableRow class in JavaFX represents a single row within a TableView. When you call setRowFactory, you're essentially providing a factory method to customize the behavior of these rows.
        // This method (setRowFactory) is called by JavaFX to create a new TableRow instance for each row in the TableView, but it doesn't create new rows in the sense of adding more rows to the table.
        // The setup of "setRowFactory()" occurs only once, but inside the callback, its updateItem method may be invoked multiple times during the lifecycle of the TableView, depending on the events occurring within the table.
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
    public void handleBackToDashboard(ActionEvent event) throws IOException {
        switchScene.switchSceneHelper(event, YourController.class, "/fxml/dashboard.fxml", "Dashboard");
    }


    public void handleHeaderColorPicker() {
        getHeaderColor();
    }
    @FXML
    public void getHeaderColor() {
        headerColor = new DeviceRgb((float) headerColorPicker.getValue().getRed(), (float) headerColorPicker.getValue().getGreen(), (float) headerColorPicker.getValue().getBlue());;
        System.out.println(headerColor);
    }
    @FXML
    public void handleAddItemButton() {
        // Remove the 2 special rows first ("tax" and "total")
        itemTable.getItems().removeIf(Item::isSpecialEntry);

        // Validate item data before putting the new item to the table
        String name = nameField.getText();
        if (name.isEmpty() || !isNumeric(quantityField.getText()) || !isNumeric(unitPriceField.getText())) {
            return;
        }
        int quantity = Integer.parseInt(quantityField.getText());
        double unitPrice = Double.parseDouble(unitPriceField.getText());

        // Add new item to table
        Item newItem = new Item(name, quantity, unitPrice);
        itemTable.getItems().add(newItem);
        itemTable.refresh();

        // Clear text fields (at the bottom)
        nameField.clear();
        quantityField.clear();
        unitPriceField.clear();

        // Calculate the updated tax and total ; preparing to add the special rows to the table
        double subtotal = itemTable.getItems().stream().mapToDouble(item -> item.getUnitPrice() * item.getQuantity()).sum();
        double tax = subtotal * 0.13;
        double totalWithTax = subtotal + tax;

        // Add special rows to the table
        itemTable.getItems().addAll(
                Item.taxEntry(tax),
                Item.totalEntry(totalWithTax)
        );

        itemTable.refresh();
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

            saveInvoiceInfo();

            PdfWriter writer = new PdfWriter(selectedFile.getAbsolutePath());
            PdfDocument pdfDocument = new PdfDocument(writer);
            Document document = new Document(pdfDocument, new PageSize(PageSize.A4)); // Set the document size and orientation

            // Set the margins for the document
            document.setMargins(50, 50, 50, 50);

            // Create a full-width table for the yellow background
            Table backgroundTable = new Table(1);
            backgroundTable.setWidth(UnitValue.createPercentValue(100));
            Cell backgroundCell = new Cell().setBackgroundColor(headerColor).setHeight(100); // Set a fixed height for your header
            backgroundTable.addCell(backgroundCell);
            document.add(backgroundTable);

            // Now add the header content within the margins
            // Load the company logo
            ImageData imageData = ImageDataFactory.create(getClass().getResource("/images/logo.png").toExternalForm());
            Image pdfImg = new Image(imageData).setAutoScale(true);

            // Create a table for the header content with specified margins
            float[] columnWidths = {1, 2};
            Table headerContentTable = new Table(columnWidths).useAllAvailableWidth();

            // Add the logo to the header content table
            headerContentTable.addCell(new Cell().add(pdfImg).setBorder(Border.NO_BORDER).setPaddingLeft(50).setPaddingTop(20).setPaddingBottom(20).setPaddingRight(20));

            // Add the company info to the header content table
            Paragraph companyInfo = new Paragraph()
                    .add(cCompanyField.getText() + "\n")
                    .add(cStreetField.getText() + "\n")
                    .add(cCityField.getText() + "\n")
                    .add(cPostalField.getText() + "\n")
                    .add(cPhoneField.getText() + "\n")
                    .add(cEmailField.getText() + "\n")
                    .setMultipliedLeading(1.0f);
            headerContentTable.addCell(new Cell().add(companyInfo).setBorder(Border.NO_BORDER).setPaddingRight(50).setPaddingTop(20).setPaddingBottom(20).setPaddingLeft(20));

            // Set the relative positioning to the background table
            headerContentTable.setRelativePosition(0, -100, 0, 0); // Adjust this based on the exact height of your background

            // Add the header content table to the document
            document.add(headerContentTable);

            // Add the Bill To: section
            Paragraph billTo = new Paragraph("Bill To:\n")
                    .add(companyField.getText() + "\n")
                    .add(streetField.getText() + "\n")
                    .add(cityField.getText() + "\n")
                    .add(postalField.getText() + "\n")
                    .add(phoneField.getText() + "\n")
                    .add(emailField.getText() + "\n")
                    .setBold();
            document.add(billTo);

            // Create the item details table with specific column widths
            Table itemDetailsTable = new Table(new float[]{5, 1, 1, 1});
            itemDetailsTable.setWidth(UnitValue.createPercentValue(100)); // Use 100% of page width

            // Add headers to the table
            Stream.of("Item", "Quantity", "Price", "Total Price").forEach(headerTitle -> {
                itemDetailsTable.addHeaderCell(new Cell().add(new Paragraph(headerTitle)).setBackgroundColor(ColorConstants.LIGHT_GRAY));
            });

            // Add item rows to the table
            for (Item item : itemTable.getItems()) {
                itemDetailsTable.addCell(new Paragraph(item.getName()));
                itemDetailsTable.addCell(new Paragraph(String.valueOf(item.getQuantity())));
                itemDetailsTable.addCell(new Paragraph(String.format("$%.2f", item.getUnitPrice())));
                itemDetailsTable.addCell(new Paragraph(String.format("$%.2f", item.getLineTotal())));
            }

            // Add the item details table to the document
            document.add(itemDetailsTable);

            // Add subtotal, tax, and total
            double subtotal = itemTable.getItems().stream().mapToDouble(Item::getLineTotal).sum();
            double tax = subtotal * 0.13;
            double total = subtotal + tax;

            Table totalTable = new Table(new float[]{2, 1});
            totalTable.setWidth(UnitValue.createPercentValue(100)); // Use 100% of page width
            totalTable.addCell(new Cell().add(new Paragraph("Subtotal")));
            totalTable.addCell(new Cell().add(new Paragraph(String.format("$%.2f", subtotal))));
            totalTable.addCell(new Cell().add(new Paragraph("Tax (13%)")));
            totalTable.addCell(new Cell().add(new Paragraph(String.format("$%.2f", tax))));
            totalTable.addCell(new Cell().add(new Paragraph("Total")));
            totalTable.addCell(new Cell().add(new Paragraph(String.format("$%.2f", total))));

            // Add the total table to the document
            document.add(totalTable);

            // Add footer
            Paragraph footer = new Paragraph("Thank you for your business!")
                    .setTextAlignment(TextAlignment.CENTER);
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

    private void saveInvoiceInfo(){
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8080/users/" + Main.currentID;
        ResponseEntity<Users> response = restTemplate.getForEntity(url, Users.class, Main.currentID);

        if (response.getStatusCode() == HttpStatus.OK) {
            Users user = response.getBody();
            InvoicesInfo invoicesInfo = new InvoicesInfo();

            invoicesInfo.setUsersInfo(user);

            // Set COMPANY values using setters
            invoicesInfo.setCName(cCompanyField.getText());
            invoicesInfo.setCStreetAddress(cStreetField.getText());
            invoicesInfo.setCCityProvince(cCityField.getText());
            invoicesInfo.setCPostalCode(cPostalField.getText());
            invoicesInfo.setCPhone(cPhoneField.getText());
            invoicesInfo.setCEmail(cEmailField.getText());

            // Set CUSTOMER/USER values using setters
            invoicesInfo.setUName(companyField.getText());
            invoicesInfo.setUStreetAddress(streetField.getText());
            invoicesInfo.setUCityProvince(cityField.getText());
            invoicesInfo.setUPostalCode(postalField.getText());
            invoicesInfo.setUPhone(phoneField.getText());
            invoicesInfo.setUEmail(emailField.getText());

            // Prepare HTTP headers
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            // Prepare HTTP request entity
            HttpEntity<InvoicesInfo> requestEntity = new HttpEntity<>(invoicesInfo, headers);

            // Send POST request
            RestTemplate rTemplate = new RestTemplate();
            String postURL = "http://localhost:8080/invoices";
            InvoicesInfo createdInvoice = rTemplate.postForObject(postURL, requestEntity, InvoicesInfo.class);

            if (createdInvoice != null) {
                // Handle success (e.g., update UI, display success message)
                invoiceAddedModal.showSuccessModal("Successfully created invoice!");
                System.out.println("Invoice created successfully with ID: " + createdInvoice.getInvoiceID());
            } else {
                // Handle failure (e.g., display error message)
                ErrorModal.showErrorModal("Couldn't create modal");
            }
        }

    }

}
