/**
 * @author : Udyogi Siphara
 * Project Name: Super Market
 * Date        : 5/27/2022
 * Time        : 11:52 AM
 */

package lk.para.superMarket.dao.custom.impl;

import lk.para.superMarket.dao.SQLUtil;
import lk.para.superMarket.dao.custom.OrderDAO;
import lk.para.superMarket.entity.Order;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class OrderDAOImpl implements OrderDAO {

    @Override
    public ArrayList<Order> getAll() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.executeQuery("SELECT * FROM orders ORDER BY OrderID DESC");
        ArrayList<Order> allOrder = new ArrayList<>();
        while (rst.next()) {
            allOrder.add(new Order(rst.getString(1), rst.getDate(2).toLocalDate(), rst.getString(3)));
        }
        return allOrder;
    }

    @Override
    public boolean save(Order entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.executeUpdate("INSERT INTO `orders` (OrderID,OrderDate,CusID) VALUES (?,?,?)", entity.getOrderId(), entity.getOrderDate(), entity.getCusId());
    }

    @Override
    public boolean update(Order entity) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public Order search(String s) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public boolean exist(String oid) throws SQLException, ClassNotFoundException {
        return SQLUtil.executeQuery("SELECT OrderID FROM `orders` WHERE OrderID=?", oid).next();
    }

    @Override
    public boolean delete(String oid) throws SQLException, ClassNotFoundException {
        return SQLUtil.executeUpdate("DELETE FROM orders WHERE OrderID=?", oid);
    }

    @Override
    public String generateNewID() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.executeQuery("SELECT OrderID FROM `orders` ORDER BY OrderID DESC LIMIT 1;");
        return rst.next() ? String.format("OID-%03d", (Integer.parseInt(rst.getString("OrderID").replace("OID-", "")) + 1)) : "OID-001";
    }

    @Override
    public int count(String s) throws SQLException, ClassNotFoundException {
        return 0;
    }

    @Override
    public ArrayList<Order> searchOrder(String enteredText) throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.executeQuery("SELECT * FROM Orders WHERE OrderID LIKE ? OR  OrderDate LIKE ? OR CusID LIKE ? ORDER BY OrderID DESC", enteredText, enteredText, enteredText);
        ArrayList<Order> list = new ArrayList<>();
        while (rst.next()) {
            list.add(new Order(rst.getString(1), rst.getDate(2).toLocalDate(), rst.getString(3)));
        }
        return list;

    }
}
