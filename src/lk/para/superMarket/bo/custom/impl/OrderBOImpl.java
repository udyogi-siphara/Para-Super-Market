/**
 * @author : Udyogi Siphara
 * Project Name: SuperMarketCopy
 * Date        : 6/2/2022
 * Time        : 5:30 PM
 */

package lk.para.superMarket.bo.custom.impl;

import lk.para.superMarket.bo.custom.OrderBO;
import lk.para.superMarket.dao.DAOFactory;
import lk.para.superMarket.dao.custom.OrderDAO;
import lk.para.superMarket.dao.custom.impl.OrderDAOImpl;
import lk.para.superMarket.dto.OrderDTO;
import lk.para.superMarket.entity.Order;


import java.sql.SQLException;
import java.util.ArrayList;

public class OrderBOImpl implements OrderBO {

    private final OrderDAO orderDAO = (OrderDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ORDER);


    @Override
    public ArrayList<OrderDTO> getAllOrder() throws SQLException, ClassNotFoundException {
        ArrayList<Order> all = orderDAO.getAll();
        ArrayList<OrderDTO> allOrder = new ArrayList<>();
        for (Order order : all) {
            allOrder.add(new OrderDTO(order.getOrderId(),order.getOrderDate(), order.getCusId()));
        }
        return allOrder;
    }

    @Override
    public boolean deleteOrder(String id) throws SQLException, ClassNotFoundException {
        return orderDAO.delete(id);
    }
    @Override
    public ArrayList<OrderDTO> searchOrder(String enteredText) throws SQLException, ClassNotFoundException {
        ArrayList<Order> order = orderDAO.searchOrder(enteredText);
        ArrayList<OrderDTO> orderDto=new ArrayList<>();

        for (Order ordersList : order) {

            orderDto.add(new OrderDTO(ordersList.getOrderId(),
                    ordersList.getOrderDate(),
                    ordersList.getCusId()
            ));
        }
        return orderDto;
    }

}
