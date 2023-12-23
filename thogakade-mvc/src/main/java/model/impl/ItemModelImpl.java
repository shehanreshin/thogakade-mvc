package model.impl;

import db.DBConnection;
import dto.ItemDTO;
import model.ItemModel;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ItemModelImpl implements ItemModel {
    @Override
    public boolean saveItem(ItemDTO itemDTO) throws SQLException, ClassNotFoundException {
        PreparedStatement preparedStatement = DBConnection.getInstance().getConnection().prepareStatement("INSERT INTO item VALUES(?,?,?,?)");
        preparedStatement.setString(1,itemDTO.getCode());
        preparedStatement.setString(2,itemDTO.getDesc());
        preparedStatement.setDouble(3,itemDTO.getUnitPrice());
        preparedStatement.setInt(4,itemDTO.getQty());
        return preparedStatement.executeUpdate()>0;
    }

    @Override
    public boolean updateItem(ItemDTO itemDTO) throws SQLException, ClassNotFoundException {
        PreparedStatement preparedStatement = DBConnection.getInstance().getConnection().prepareStatement("UPDATE item SET description=?, unitPrice=?, qtyOnHand=? WHERE code=?");
        preparedStatement.setString(1,itemDTO.getDesc());
        preparedStatement.setDouble(2,itemDTO.getUnitPrice());
        preparedStatement.setInt(3,itemDTO.getQty());
        preparedStatement.setString(4,itemDTO.getCode());
        return preparedStatement.executeUpdate()>0;
    }

    @Override
    public boolean deleteItem(String code) throws SQLException, ClassNotFoundException {
        PreparedStatement preparedStatement = DBConnection.getInstance().getConnection().prepareStatement("DELETE from item WHERE code=?");
        preparedStatement.setString(1,code);
        return preparedStatement.executeUpdate()>0;
    }

    @Override
    public List<ItemDTO> allItems() throws SQLException, ClassNotFoundException {
        List<ItemDTO> itemDTOList = new ArrayList<>();
        PreparedStatement preparedStatement = DBConnection.getInstance().getConnection().prepareStatement("SELECT * FROM item");
        ResultSet resultSet = preparedStatement.executeQuery();

        while(resultSet.next()) {
            itemDTOList.add(new ItemDTO(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getDouble(3),
                    resultSet.getInt(4)
            ));
        }

        return itemDTOList;
    }

    @Override
    public ItemDTO searchItem(ItemDTO itemDTO) {
        return null;
    }
}
