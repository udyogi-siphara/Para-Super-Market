/**
 * @author : Udyogi Siphara
 * Project Name: Super Market
 * Date        : 5/27/2022
 * Time        : 11:54 AM
 */

package lk.para.superMarket.bo.custom.impl;

import lk.para.superMarket.bo.custom.PurchaseOrderBO;
import lk.para.superMarket.dao.DAOFactory;
import lk.para.superMarket.dao.custom.*;
import lk.para.superMarket.dao.custom.impl.*;
import lk.para.superMarket.db.DBConnection;
import lk.para.superMarket.dto.CustomerDTO;
import lk.para.superMarket.dto.ItemDTO;
import lk.para.superMarket.dto.OrderDTO;
import lk.para.superMarket.dto.OrderDetailDTO;
import lk.para.superMarket.entity.Customer;
import lk.para.superMarket.entity.Item;
import lk.para.superMarket.entity.Order;
import lk.para.superMarket.entity.OrderDetail;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class PurchaseOrderBOImpl implements PurchaseOrderBO {

    private final CustomerDAO customerDAO = (CustomerDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.CUSTOMER);
    private final ItemDAO itemDAO = (ItemDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ITEM);
    private final OrderDAO orderDAO = (OrderDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ORDER);
    private final OrderDetailDAO orderDetailsDAO = (OrderDetailDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ORDERDETAILS);


    @Override
    public boolean purchaseOrder(OrderDTO dto) throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getDbConnection().getConnection();
        /*if order id already exist*/
        if (orderDAO.exist(dto.getOrderId())) {

        }
        connection.setAutoCommit(false);
        boolean save = orderDAO.save(new Order(dto.getOrderId(),dto.getOrderDate(), dto.getCusId()));

        if (!save) {
            connection.rollback();
            connection.setAutoCommit(true);
            return false;
        }

        for (OrderDetailDTO detailDTO : dto.getOrderDetails()) {
            boolean save1 = orderDetailsDAO.save(new OrderDetail(detailDTO.getOrderId(), detailDTO.getItemCode(), detailDTO.getOrderQty(),detailDTO.getDiscount(), BigDecimal.valueOf(detailDTO.getUnitPrice())));
            if (!save1) {
                connection.rollback();
                connection.setAutoCommit(true);
                return false;
            }

            //Search & Update Item
            ItemDTO item = searchItem(detailDTO.getItemCode());
            item.setQtyOnHand(item.getQtyOnHand() - detailDTO.getOrderQty());

            //update item
            boolean update = itemDAO.update(new Item(item.getItemCode(), item.getDescription(),item.getPackSize(),item.getUnitPrice(), item.getQtyOnHand()));

            if (!update) {
                connection.rollback();
                connection.setAutoCommit(true);
                return false;
            }
        }
        connection.commit();
        connection.setAutoCommit(true);
        return true;

    }

    @Override
    public CustomerDTO searchCustomer(String id) throws SQLException, ClassNotFoundException {
        Customer ent = customerDAO.search(id);
        return new CustomerDTO(ent.getCusID(), ent.getCusTitle(), ent.getCusName(), ent.getCusAddress(), ent.getCity(), ent.getProvince(), ent.getPostalCode());
    }

    @Override
    public ItemDTO searchItem(String code) throws SQLException, ClassNotFoundException {
        Item ent = itemDAO.search(code);
        return new ItemDTO(ent.getItemCode(), ent.getDescription(), ent.getPackSize(), ent.getUnitPrice(), ent.getQtyOnHand());
    }

    @Override
    public boolean checkItemIsAvailable(String code) throws SQLException, ClassNotFoundException {
        return itemDAO.exist(code);
    }

    @Override
    public boolean checkCustomerIsAvailable(String id) throws SQLException, ClassNotFoundException {
        return customerDAO.exist(id);
    }

    @Override
    public String generateNewOrderID() throws SQLException, ClassNotFoundException {
        return orderDAO.generateNewID();
    }

    @Override
    public ArrayList<CustomerDTO> getAllCustomers() throws SQLException, ClassNotFoundException {
        ArrayList<Customer> all = customerDAO.getAll();
        ArrayList<CustomerDTO> allCustomer = new ArrayList<>();
        for (Customer ent : all) {
            allCustomer.add(new CustomerDTO(ent.getCusID(), ent.getCusTitle(), ent.getCusName(), ent.getCusAddress(), ent.getCity(), ent.getProvince(), ent.getPostalCode()));
        }
        return allCustomer;
    }

    @Override
    public ArrayList<ItemDTO> getAllItems() throws SQLException, ClassNotFoundException {
        ArrayList<Item> all = itemDAO.getAll();
        ArrayList<ItemDTO> allItem = new ArrayList<>();
        for (Item ent : all) {
            allItem.add(new ItemDTO(ent.getItemCode(), ent.getDescription(), ent.getPackSize(), ent.getUnitPrice(), ent.getQtyOnHand()));
        }
        return allItem;
    }
}
