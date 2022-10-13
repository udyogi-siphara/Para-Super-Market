/**
 * @author : Udyogi Siphara
 * Project Name: Super Market
 * Date        : 5/26/2022
 * Time        : 10:09 PM
 */

package lk.para.superMarket.dao.custom.impl;

import lk.para.superMarket.dao.SQLUtil;
import lk.para.superMarket.dao.custom.ItemDAO;
import lk.para.superMarket.entity.Customer;
import lk.para.superMarket.entity.Item;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ItemDAOImpl implements ItemDAO {

    @Override
    public ArrayList<Item> getAll() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.executeQuery("SELECT * FROM Item");
        ArrayList<Item> allItem = new ArrayList<>();
        while (rst.next()) {
            allItem.add(new Item(rst.getString(1),rst.getString(2),rst.getString(3),rst.getBigDecimal(4),rst.getInt(5)));
        }
        return allItem;
    }

    @Override
    public boolean save(Item entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.executeUpdate("INSERT INTO Item (ItemCode,Description,PackSize,UnitPrice,QtyOnHand) VALUES (?,?,?,?,?)",entity.getItemCode(),entity.getDescription(),entity.getPackSize(),entity.getUnitPrice(),entity.getQtyOnHand());
    }

    @Override
    public boolean update(Item entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.executeUpdate("UPDATE Item SET Description=?,PackSize=?,UnitPrice=?,QtyOnHand=? WHERE ItemCode=?",entity.getDescription(),entity.getPackSize(),entity.getUnitPrice(),entity.getQtyOnHand(),entity.getItemCode());
    }

    @Override
    public Item search(String code) throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.executeQuery("SELECT * FROM item WHERE ItemCode=?",code);
        if (rst.next()) {
            return new Item(rst.getString(1),rst.getString(2),rst.getString(3),rst.getBigDecimal(4),rst.getInt(5));
        }
        return null;
    }

    @Override
    public boolean exist(String code) throws SQLException, ClassNotFoundException {
        return SQLUtil.executeQuery("SELECT ItemCode FROM Item WHERE ItemCode=?", code).next();
    }

    @Override
    public boolean delete(String code) throws SQLException, ClassNotFoundException {
        return SQLUtil.executeUpdate("DELETE FROM Item WHERE ItemCode=?",code);
    }

    @Override
    public String generateNewID() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.executeQuery("SELECT ItemCode FROM Item ORDER BY ItemCode DESC LIMIT 1;");
        if (rst.next()) {
            String id = rst.getString("ItemCode");
            int newItemCode = Integer.parseInt(id.replace("I00-", "")) + 1;
            return String.format("I00-%03d", newItemCode);
        } else {
            return "I00-001";
        }
    }

    @Override
    public int count(String s) throws SQLException, ClassNotFoundException {
        return 0;
    }

    @Override
    public ArrayList<Item> searchItems(String enteredText) throws SQLException, ClassNotFoundException {
        ResultSet result = SQLUtil.executeQuery("SELECT * FROM Item where ItemCode LIKE ? OR Description LIKE ? OR PackSize LIKE ? OR UnitPrice LIKE ? OR QtyOnHand LIKE ? ", enteredText, enteredText, enteredText, enteredText, enteredText);
        ArrayList<Item> list = new ArrayList<>();

        while (result.next()) {
            list.add(new Item(result.getString(1),
                    result.getString(2),
                    result.getString(3),
                    result.getBigDecimal(4),
                    result.getInt(5)
            ));
        }
        return list;
    }
}
