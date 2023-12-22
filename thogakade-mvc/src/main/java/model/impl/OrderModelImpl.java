package model.impl;

import db.DBConnection;
import dto.CustomerDTO;
import dto.OrderDTO;
import model.OrderModel;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderModelImpl implements OrderModel {
    @Override
    public List<OrderDTO> allOrders() throws SQLException, ClassNotFoundException {
        List<OrderDTO> orderDTOList = new ArrayList<>();
        PreparedStatement preparedStatement = DBConnection.getInstance().getConnection().prepareStatement("SELECT * FROM order_detail");
        ResultSet resultSet = preparedStatement.executeQuery();

        while(resultSet.next()) {
            orderDTOList.add(new OrderDTO(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getInt(3),
                    resultSet.getDouble(4)
            ));
        }

        return orderDTOList;
    }

    @Override
    public OrderDTO searchOrder(OrderDTO orderDTO) throws SQLException, ClassNotFoundException {
        return null;
    }
}
