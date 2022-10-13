/**
 * @author : Udyogi Siphara
 * Project Name: Super Market
 * Date        : 5/27/2022
 * Time        : 11:51 AM
 */

package lk.para.superMarket.dao.custom;

import lk.para.superMarket.dao.CrudDAO;
import lk.para.superMarket.entity.OrderDetail;

import java.sql.SQLException;
import java.util.ArrayList;

public interface OrderDetailDAO extends CrudDAO<OrderDetail,String> {
    ArrayList<OrderDetail> searchOrderDetail(String enteredText) throws SQLException, ClassNotFoundException;
}
