package model;

import dto.OrderDTO;
import dto.OrderDetailDTO;

import java.sql.SQLException;
import java.util.List;

public interface OrderDetailModel {
    List<OrderDetailDTO> allOrders() throws SQLException, ClassNotFoundException;
    OrderDetailDTO searchOrder(OrderDetailDTO orderDetailDTO) throws SQLException, ClassNotFoundException;
    boolean saveOrderDetails(List<OrderDetailDTO> list) throws SQLException, ClassNotFoundException;
}
