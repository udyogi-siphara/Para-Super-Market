/**
 * @author : Udyogi Siphara
 * Project Name: Super Market
 * Date        : 5/27/2022
 * Time        : 11:51 AM
 */

package lk.para.superMarket.dao.custom;

import lk.para.superMarket.dao.CrudDAO;
import lk.para.superMarket.entity.Item;
import lk.para.superMarket.entity.Order;

import java.sql.SQLException;
import java.util.ArrayList;

public interface OrderDAO extends CrudDAO<Order,String> {
    ArrayList<Order> searchOrder(String enteredText) throws SQLException, ClassNotFoundException;

}
