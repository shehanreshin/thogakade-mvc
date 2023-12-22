package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import dto.ItemDTO;
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
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.ItemModel;
import model.impl.ItemModelImpl;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class ItemsController implements Initializable {
    @FXML
    private TableView<ItemTM> tblItems;
    @FXML
    private TableColumn colCode;
    @FXML
    private TableColumn colDescription;
    @FXML
    private TableColumn colUnitPrice;
    @FXML
    private TableColumn colQtyOnHand;
    @FXML
    private TableColumn colOption;
    @FXML
    private JFXButton btnNotifications;
    @FXML
    private JFXButton btnLogout;
    @FXML
    private JFXButton btnEdit;
    @FXML
    private JFXButton btnSettings;
    @FXML
    private JFXButton btnPlaceOrders;
    @FXML
    private JFXButton btnCustomers;
    @FXML
    private JFXButton btnOrders;
    @FXML
    private JFXButton btnItems;
    @FXML
    private JFXButton btnDashboard;
    @FXML
    private JFXTextField txtCode;
    @FXML
    private JFXTextField txtDescription;
    @FXML
    private JFXTextField txtQty;
    @FXML
    private JFXTextField txtUnitPrice;

    private final ItemModel itemModel = new ItemModelImpl();

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

    private void loadItems() {
        ObservableList<ItemTM> tmList = FXCollections.observableArrayList();
        try {
            List<ItemDTO> dtoList = itemModel.allItems();
            for (ItemDTO itemDTO : dtoList) {
                JFXButton btn = new JFXButton("Delete");
                ItemTM itemTm = new ItemTM(
                        itemDTO.getCode(),
                        itemDTO.getDesc(),
                        itemDTO.getUnitPrice(),
                        itemDTO.getQty(),
                        btn
                );

                btn.setOnAction(actionEvent -> {
                    deleteItem(itemTm.getCode());
                });

                tmList.add(itemTm);
            }

            tblItems.setItems(tmList);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteItem(String id) {
        try {
            boolean isDeleted = itemModel.deleteItem(id);
            if (isDeleted) {
                new Alert(Alert.AlertType.INFORMATION, "Item Deleted!").show();
                loadItems();
            } else {
                new Alert(Alert.AlertType.ERROR, "Something went wrong!").show();
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colCode.setCellValueFactory(new PropertyValueFactory<>("code"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("desc"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colQtyOnHand.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colOption.setCellValueFactory(new PropertyValueFactory<>("btn"));
        loadItems();

        tblItems.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
            setData(newValue);
        });
    }

    private void setData(ItemTM newValue) {
        if (newValue != null) {
            txtCode.setEditable(false);
            txtCode.setText(newValue.getCode());
            txtDescription.setText(newValue.getDesc());
            txtUnitPrice.setText(String.valueOf(newValue.getUnitPrice()));
            txtQty.setText(String.valueOf(newValue.getQty()));
        }
    }
}
