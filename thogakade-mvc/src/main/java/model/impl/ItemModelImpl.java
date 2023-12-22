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
    public boolean saveItem(ItemDTO itemDTO) {
        return false;
    }

    @Override
    public boolean updateItem(ItemDTO itemDTO) {
        return false;
    }

    @Override
    public boolean deleteItem(String code) {
        return false;
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
