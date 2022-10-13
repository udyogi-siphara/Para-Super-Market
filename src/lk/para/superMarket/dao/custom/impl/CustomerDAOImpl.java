/**
 * @author : Udyogi Siphara
 * Project Name: Super Market
 * Date        : 5/26/2022
 * Time        : 10:44 AM
 */

package lk.para.superMarket.dao.custom.impl;

import lk.para.superMarket.dao.SQLUtil;
import lk.para.superMarket.dao.custom.CustomerDAO;
import lk.para.superMarket.entity.Customer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerDAOImpl implements CustomerDAO {

    @Override
    public ArrayList<Customer> getAll() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.executeQuery("SELECT * FROM Customer");
        ArrayList<Customer> allCustomer = new ArrayList<>();
        while (rst.next()) {
            allCustomer.add(new Customer(rst.getString(1),rst.getString(2),rst.getString(3),rst.getString(4),rst.getString(5),rst.getString(6), rst.getString(7)));
        }
        return allCustomer;
    }
    @Override
    public boolean save(Customer entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.executeUpdate("INSERT INTO Customer (CusID,CusTitle,CusName,CusAddress,City,Province,PostalCode) VALUES (?,?,?,?,?,?,?)",entity.getCusID(),entity.getCusTitle(),entity.getCusName(),entity.getCusAddress(),entity.getCity(),entity.getProvince(),entity.getPostalCode());
    }
    @Override
    public boolean update(Customer entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.executeUpdate("UPDATE Customer SET CusTitle=?, CusName=? , CusAddress=? , City=? , Province=? , PostalCode=? WHERE CusID=?",entity.getCusTitle(),entity.getCusName(),entity.getCusAddress(),entity.getCity(),entity.getProvince(),entity.getPostalCode(),entity.getCusID());
    }

    @Override
    public Customer search(String id) throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.executeQuery("SELECT * FROM Customer WHERE CusID=?",id);
        if (rst.next()) {
            return new Customer(rst.getString(1),rst.getString(2),rst.getString(3),rst.getString(4),rst.getString(5),rst.getString(6),rst.getString(7));
        }
        return null;
    }

    @Override
    public boolean exist(String CusID) throws SQLException, ClassNotFoundException {
        return SQLUtil.executeQuery("SELECT CusID FROM Customer WHERE CusID=?",CusID).next();
    }
    @Override
    public boolean delete(String CusID) throws SQLException, ClassNotFoundException {
        return SQLUtil.executeUpdate("DELETE FROM Customer WHERE CusID=?",CusID);
    }
    @Override
    public String generateNewID() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.executeQuery("SELECT CusID FROM Customer ORDER BY CusID DESC LIMIT 1;");
        if (rst.next()) {
            String id = rst.getString("CusID");
            int newCustomerId = Integer.parseInt(id.replace("C00-", "")) + 1;
            return String.format("C00-%03d", newCustomerId);
        } else {
            return "C00-001";
        }
    }

    @Override
    public int count(String s) throws SQLException, ClassNotFoundException {
        return 0;
    }

}
