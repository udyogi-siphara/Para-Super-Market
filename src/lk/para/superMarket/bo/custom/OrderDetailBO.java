/**
 * @author : Udyogi Siphara
 * Project Name: SuperMarketCopy
 * Date        : 6/2/2022
 * Time        : 9:35 PM
 */

package lk.para.superMarket.bo.custom;

import lk.para.superMarket.bo.SuperBO;
import lk.para.superMarket.dto.OrderDetailDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface OrderDetailBO extends SuperBO {
    ArrayList<OrderDetailDTO> searchOrderDetail(String enteredText) throws SQLException, ClassNotFoundException;
}
