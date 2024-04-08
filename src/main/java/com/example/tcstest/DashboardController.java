package com.example.tcstest;

import com.example.tcstest.db_stuff.entity.InvoicesInfo;
import com.example.tcstest.db_stuff.service.InvoicesInfoService;
import com.example.tcstest.model.InvoicesRow;
import com.example.tcstest.model.Item;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.util.List;

import static com.example.tcstest.helpers.switchScene.switchSceneHelper;

public class DashboardController {
    // This TableView will contain rows of type "InvoicesRow" (custom class), and each row in the table represents an instance of the InvoicesRow class.
    @FXML
    private TableView<InvoicesRow> invoicesRow;

    @FXML
    private TableColumn<InvoicesRow, Hyperlink> invoicesColumn;
    @FXML
    private TableColumn<InvoicesRow, Label> dateColumn;
    @FXML
    private TableColumn<InvoicesRow, Button> deleteColumn;

//    private final InvoicesInfoService invoicesInfoService;

//    public DashboardController(InvoicesInfoService invoicesInfoService) {
//        this.invoicesInfoService = invoicesInfoService;
//    }

    public void initialize() {
        invoicesColumn.setCellValueFactory(new PropertyValueFactory<>("invoiceHyperlink"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("createdAt"));
        deleteColumn.setCellValueFactory(new PropertyValueFactory<>("deleteBtn"));
    }

//    private void populateInvoicesTable() {
//        // Fetch data from the database
//        List<InvoicesInfo> invoicesInfoList = invoicesInfoService.getAllInvoices();
//
//        // Clear existing items in the table
//        invoicesRow.getItems().clear();
//
//        // Populate the table with fetched data
//        for (InvoicesInfo invoicesInfo : invoicesInfoList) {
//            InvoicesRow row = new InvoicesRow(invoicesInfo.getCName() + " : " + invoicesInfo.getUName(), invoicesInfo.getCreatedAt());
//            invoicesRow.getItems().add(row);
//        }
//    }

    @FXML
    public void handleCreateNewInvoice(ActionEvent event) throws IOException {
        switchSceneHelper(event, DashboardController.class, "/fxml/hello-view.fxml", "Quote/Invoice Generator");
    }

    @FXML
    public void handleLogout(ActionEvent event) throws IOException {
        switchSceneHelper(event, DashboardController.class, "/fxml/login.fxml", "Quote/Invoice Generator Login");
    }


}
