package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import dto.CustomerDTO;
import dto.ItemDTO;
import dto.tm.CustomerTM;
import dto.tm.ItemTM;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.CustomerModel;
import model.impl.CustomerModelImpl;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CustomersController implements Initializable {
    @FXML
    private JFXButton btnDashboard;

    @FXML
    private JFXButton btnItems;

    @FXML
    private JFXButton btnOrders;

    @FXML
    private JFXButton btnCustomers;

    @FXML
    private JFXButton btnPlaceOrders;

    @FXML
    private JFXButton btnSettings;

    @FXML
    private JFXButton btnLogout;

    @FXML
    private JFXButton btnNotifications;

    @FXML
    private JFXButton btnEdit;

    @FXML
    private JFXTextField txtId;

    @FXML
    private JFXTextField txtName;

    @FXML
    private JFXTextField txtAddress;

    @FXML
    private JFXTextField txtSalary;

    @FXML
    private JFXTextField txtSearch;

    @FXML
    private TableView<CustomerTM> tblCustomers;

    @FXML
    private TableColumn colId;

    @FXML
    private TableColumn colName;

    @FXML
    private TableColumn colAddress;

    @FXML
    private TableColumn colSalary;

    @FXML
    private TableColumn colOption;

    @FXML
    private JFXButton btnSave;

    @FXML
    private JFXButton btnUpdate;

    private final CustomerModel customerModel = new CustomerModelImpl();

    public void notificationsButtonOnAction() {
    }

    public void logoutButtonOnAction() {
    }

    public void editButtonOnAction() {
    }

    public void settingsButtonOnAction() {
    }

    public void placeOrdersButtonOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/PlaceOrders.fxml"))));
        stage.show();
    }

    public void ordersButtonOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/Orders.fxml"))));
        stage.show();
    }

    public void customersButtonOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/Customers.fxml"))));
        stage.show();
    }

    public void itemsButtonOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/Items.fxml"))));
        stage.show();
    }

    public void dashboardButtonOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/Home.fxml"))));
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colSalary.setCellValueFactory(new PropertyValueFactory<>("salary"));
        colOption.setCellValueFactory(new PropertyValueFactory<>("btn"));
        loadCustomers();

        tblCustomers.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
            setData(newValue);
        });
    }

    private void loadCustomers() {
        ObservableList<CustomerTM> tmList = FXCollections.observableArrayList();
        try {
            List<CustomerDTO> dtoList = customerModel.allCustomers();
            for (CustomerDTO customerDTO : dtoList) {
                JFXButton btn = new JFXButton("Delete");
                CustomerTM customerTM = new CustomerTM(
                        customerDTO.getId(),
                        customerDTO.getName(),
                        customerDTO.getAddress(),
                        customerDTO.getSalary(),
                        btn
                );

                btn.setOnAction(actionEvent -> {
                    deleteCustomer(customerTM.getId());
                });

                tmList.add(customerTM);
            }

            tblCustomers.setItems(tmList);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void setData(CustomerTM newValue) {
        if (newValue != null) {
            txtId.setEditable(false);
            txtId.setText(newValue.getId());
            txtName.setText(newValue.getName());
            txtAddress.setText(newValue.getAddress());
            txtSalary.setText(String.valueOf(newValue.getSalary()));
        }
    }

    public void deleteCustomer(String id) {
        try {
            boolean isDeleted = customerModel.deleteCustomer(id);
            if (isDeleted) {
                new Alert(Alert.AlertType.INFORMATION, "Customer Deleted!").show();
                loadCustomers();
            } else {
                new Alert(Alert.AlertType.ERROR, "Something went wrong!").show();
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveCustomer() {
        if (isAnyInputDataInvalid()) {
            return;
        }
        try {
            boolean isSaved = customerModel.saveCustomer(new CustomerDTO(txtId.getText(),
                    txtName.getText(),
                    txtAddress.getText(),
                    Double.parseDouble(txtSalary.getText())
            ));
            if (isSaved) {
                new Alert(Alert.AlertType.INFORMATION, "Customer Saved!").show();
                loadCustomers();
                clearFields();
            }
        } catch (SQLIntegrityConstraintViolationException ex) {
            new Alert(Alert.AlertType.ERROR, "Duplicate Entry").show();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    private void clearFields() {
        tblCustomers.refresh();
        txtId.clear();
        txtName.clear();
        txtAddress.clear();
        txtSalary.clear();
        txtId.setEditable(true);
    }

    private boolean validateSalary() {
        double salary;
        try {
            salary = Double.parseDouble(txtSalary.getText());
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Invalid salary");
            alert.setHeaderText(null);
            alert.setContentText("Please enter a valid salary");
            alert.showAndWait();
            return false;
        }
        return true;
    }

    private boolean validateId() {
        Pattern pattern = Pattern.compile("^C[0-9]{3}$");
        Matcher matcher = pattern.matcher(txtId.getText());

        if (matcher.find() && matcher.group().equals(txtId.getText())) {
            return true;
        }

        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Invalid ID");
        alert.setHeaderText(null);
        alert.setContentText("Please enter a valid ID");
        alert.showAndWait();
        return false;
    }

    private boolean isAnyInputDataInvalid() {
        return !validateId() | !validateSalary();
    }

    public void updateCustomer() {
        if (isAnyInputDataInvalid()) {
            return;
        }
        try {
            boolean isUpdated = customerModel.updateCustomer(new CustomerDTO(txtId.getText(),
                    txtName.getText(),
                    txtAddress.getText(),
                    Double.parseDouble(txtSalary.getText())
            ));
            if (isUpdated) {
                new Alert(Alert.AlertType.INFORMATION, "Customer Updated!").show();
                loadCustomers();
                clearFields();
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveButtonOnAction() {
        saveCustomer();
    }

    public void updateButtonOnAction() {
        updateCustomer();
    }
}
