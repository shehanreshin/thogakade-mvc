package model;

import dto.CustomerDTO;
import dto.OrderDTO;

import java.sql.SQLException;
import java.util.List;

public interface OrderModel {
    List<OrderDTO> allOrders() throws SQLException, ClassNotFoundException;
    OrderDTO searchOrder(OrderDTO orderDTO) throws SQLException, ClassNotFoundException;
}
