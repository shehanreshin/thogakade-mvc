package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import dto.CustomerDTO;
import dto.OrderDTO;
import dto.tm.CustomerTM;
import dto.tm.OrderTM;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.OrderModel;
import model.impl.OrderModelImpl;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class OrdersController implements Initializable {
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
    private JFXTextField txtSearch;

    @FXML
    private TableView<OrderTM> tblOrders;

    @FXML
    private TableColumn colOrderId;

    @FXML
    private TableColumn colItemCode;

    @FXML
    private TableColumn colQty;

    @FXML
    private TableColumn colUnitPrice;

    private final OrderModel orderModel = new OrderModelImpl();

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
        colOrderId.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        colItemCode.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        loadOrders();
    }

    private void loadOrders() {
        ObservableList<OrderTM> tmList = FXCollections.observableArrayList();
        try {
            List<OrderDTO> dtoList = orderModel.allOrders();
            for (OrderDTO orderDTO : dtoList) {
                OrderTM orderTM = new OrderTM(
                        orderDTO.getOrderId(),
                        orderDTO.getItemCode(),
                        orderDTO.getQty(),
                        orderDTO.getUnitPrice()
                );
                tmList.add(orderTM);
            }

            tblOrders.setItems(tmList);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
