package model.impl;

import db.DBConnection;
import dto.CustomerDTO;
import dto.ItemDTO;
import model.CustomerModel;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerModelImpl implements CustomerModel {
    @Override
    public boolean saveCustomer(CustomerDTO customerDTO) throws SQLException, ClassNotFoundException {
        PreparedStatement preparedStatement = DBConnection.getInstance().getConnection().prepareStatement("INSERT INTO customer VALUES(?,?,?,?)");
        preparedStatement.setString(1,customerDTO.getId());
        preparedStatement.setString(2,customerDTO.getName());
        preparedStatement.setString(3,customerDTO.getAddress());
        preparedStatement.setDouble(4,customerDTO.getSalary());
        return preparedStatement.executeUpdate()>0;
    }

    @Override
    public boolean updateCustomer(CustomerDTO customerDTO) throws SQLException, ClassNotFoundException {
        PreparedStatement preparedStatement = DBConnection.getInstance().getConnection().prepareStatement("UPDATE customer SET name=?, address=?, salary=? WHERE id=?");
        preparedStatement.setString(1,customerDTO.getName());
        preparedStatement.setString(2,customerDTO.getAddress());
        preparedStatement.setDouble(3,customerDTO.getSalary());
        preparedStatement.setString(4,customerDTO.getId());
        return preparedStatement.executeUpdate()>0;
    }

    @Override
    public boolean deleteCustomer(String id) throws SQLException, ClassNotFoundException {
        PreparedStatement preparedStatement = DBConnection.getInstance().getConnection().prepareStatement("DELETE from customer WHERE id=?");
        preparedStatement.setString(1,id);
        return preparedStatement.executeUpdate()>0;
    }

    @Override
    public List<CustomerDTO> allCustomers() throws SQLException, ClassNotFoundException {
        List<CustomerDTO> customerDTOList = new ArrayList<>();
        PreparedStatement preparedStatement = DBConnection.getInstance().getConnection().prepareStatement("SELECT * FROM customer");
        ResultSet resultSet = preparedStatement.executeQuery();

        while(resultSet.next()) {
            customerDTOList.add(new CustomerDTO(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getDouble(4)
            ));
        }

        return customerDTOList;
    }

    @Override
    public CustomerDTO searchCustomer(CustomerDTO customerDTO) throws SQLException, ClassNotFoundException {
        return null;
    }
}
