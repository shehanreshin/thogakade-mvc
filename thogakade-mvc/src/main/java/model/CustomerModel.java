package model;

import dto.CustomerDTO;

import java.sql.SQLException;
import java.util.List;

public interface CustomerModel {
        boolean saveCustomer(CustomerDTO customerDTO) throws SQLException, ClassNotFoundException;
        boolean updateCustomer(CustomerDTO customerDTO) throws SQLException, ClassNotFoundException;
        boolean deleteCustomer(String id) throws SQLException, ClassNotFoundException;
        List<CustomerDTO> allCustomers() throws SQLException, ClassNotFoundException;
        CustomerDTO searchCustomer(CustomerDTO customerDTO) throws SQLException, ClassNotFoundException;
}
