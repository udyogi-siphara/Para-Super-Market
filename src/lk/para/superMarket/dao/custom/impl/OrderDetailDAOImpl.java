/**
 * @author : Udyogi Siphara
 * Project Name: Super Market
 * Date        : 5/27/2022
 * Time        : 11:52 AM
 */

package lk.para.superMarket.dao.custom.impl;

import lk.para.superMarket.dao.SQLUtil;
import lk.para.superMarket.dao.custom.OrderDetailDAO;
import lk.para.superMarket.entity.OrderDetail;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class OrderDetailDAOImpl implements OrderDetailDAO {

    @Override
    public ArrayList<OrderDetail> getAll() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public boolean save(OrderDetail entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.executeUpdate("INSERT INTO `orderDetail`  VALUES (?,?,?,?,?) ",entity.getOrderId(),entity.getItemCode(),entity.getOrderQty(),entity.getUnitPrice(),entity.getDiscount());
    }

    @Override
    public boolean update(OrderDetail entity) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public OrderDetail search(String s) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public boolean exist(String s) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean delete(String s) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public String generateNewID() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public int count(String s) throws SQLException, ClassNotFoundException {
        return 0;
    }


    @Override
    public ArrayList<OrderDetail> searchOrderDetail(String enteredText) throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.executeQuery("SELECT * FROM OrderDetail WHERE OrderID LIKE ? OR  ItemCode LIKE ? OR OrderQty LIKE ? OR UnitPrice LIKE ? OR Discount LIKE ?",enteredText,enteredText,enteredText,enteredText,enteredText);
        ArrayList<OrderDetail> list = new ArrayList<>();
        while (rst.next()) {
            double total=(rst.getInt(3)*rst.getDouble(4)-rst.getDouble(5));
            list.add(new OrderDetail(rst.getString(1),rst.getString(2),rst.getInt(3), BigDecimal.valueOf(rst.getDouble(4)),rst.getBigDecimal(5),BigDecimal.valueOf(total)));
        }
        return list;
    }
}
