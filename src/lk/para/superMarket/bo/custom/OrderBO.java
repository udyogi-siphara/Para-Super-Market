/**
 * @author : Udyogi Siphara
 * Project Name: SuperMarketCopy
 * Date        : 6/2/2022
 * Time        : 5:30 PM
 */

package lk.para.superMarket.bo.custom;

import lk.para.superMarket.bo.SuperBO;
import lk.para.superMarket.dto.OrderDTO;


import java.sql.SQLException;
import java.util.ArrayList;

public interface OrderBO extends SuperBO {
    ArrayList<OrderDTO> getAllOrder() throws SQLException, ClassNotFoundException;

    boolean deleteOrder(String oid) throws SQLException, ClassNotFoundException;

    ArrayList<OrderDTO> searchOrder(String enteredText) throws SQLException, ClassNotFoundException;
}
