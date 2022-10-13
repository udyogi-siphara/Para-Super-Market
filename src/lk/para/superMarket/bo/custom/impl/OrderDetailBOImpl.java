/**
 * @author : Udyogi Siphara
 * Project Name: SuperMarketCopy
 * Date        : 6/2/2022
 * Time        : 9:35 PM
 */

package lk.para.superMarket.bo.custom.impl;

import lk.para.superMarket.bo.custom.OrderDetailBO;
import lk.para.superMarket.dao.DAOFactory;
import lk.para.superMarket.dao.custom.ItemDAO;
import lk.para.superMarket.dao.custom.OrderDetailDAO;
import lk.para.superMarket.dao.custom.impl.ItemDAOImpl;
import lk.para.superMarket.dao.custom.impl.OrderDetailDAOImpl;
import lk.para.superMarket.dto.CustomDTO;
import lk.para.superMarket.dto.ItemDTO;
import lk.para.superMarket.dto.OrderDetailDTO;
import lk.para.superMarket.entity.Item;
import lk.para.superMarket.entity.OrderDetail;
import lk.para.superMarket.view.tdm.CustomTM;

import java.sql.SQLException;
import java.util.ArrayList;

public class OrderDetailBOImpl implements OrderDetailBO {

    private final OrderDetailDAO orderDetailDAO = (OrderDetailDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ORDERDETAILS);

    @Override
    public ArrayList<OrderDetailDTO> searchOrderDetail(String enteredText) throws SQLException, ClassNotFoundException {
        ArrayList<OrderDetail> orderDetails = orderDetailDAO.searchOrderDetail(enteredText);
        ArrayList<OrderDetailDTO> orDetailDto=new ArrayList<>();

        for (OrderDetail ordersList : orderDetails) {

            orDetailDto.add(new OrderDetailDTO(ordersList.getOrderId(),
                    ordersList.getItemCode(),
                    ordersList.getOrderQty(),
                    ordersList.getUnitPrice().doubleValue(),
                    ordersList.getDiscount(),
                    ordersList.getTotal()
            ));
        }
        return orDetailDto;
    }
}
