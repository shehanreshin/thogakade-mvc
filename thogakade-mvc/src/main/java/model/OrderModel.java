package model;

import dto.OrderDTO;

import java.sql.SQLException;

public interface OrderModel {
    boolean saveOrder(OrderDTO orderDTO) throws SQLException, ClassNotFoundException;
    OrderDTO getLastOrder() throws SQLException, ClassNotFoundException;
}
