/**
 * @author : Udyogi Siphara
 * Project Name: Super Market
 * Date        : 5/26/2022
 * Time        : 10:09 PM
 */

package lk.para.superMarket.dao.custom;

import lk.para.superMarket.dao.CrudDAO;
import lk.para.superMarket.entity.Customer;
import lk.para.superMarket.entity.Item;

import java.sql.SQLException;
import java.util.ArrayList;

public interface ItemDAO extends CrudDAO<Item,String> {
    ArrayList<Item> searchItems(String enteredText) throws SQLException, ClassNotFoundException;
}
