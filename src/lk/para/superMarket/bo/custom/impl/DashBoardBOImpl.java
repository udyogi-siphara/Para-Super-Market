
package lk.para.superMarket.bo.custom.impl;

import lk.para.superMarket.bo.BOFactory;
import lk.para.superMarket.bo.custom.DashBoardBO;
import lk.para.superMarket.bo.custom.OrderBO;
import lk.para.superMarket.dao.DAOFactory;
import lk.para.superMarket.dao.custom.CustomerDAO;
import lk.para.superMarket.dao.custom.ItemDAO;
import lk.para.superMarket.dao.custom.QueryDAO;
import lk.para.superMarket.dao.custom.impl.CustomerDAOImpl;
import lk.para.superMarket.dao.custom.impl.ItemDAOImpl;
import lk.para.superMarket.dao.custom.impl.QueryDAOImpl;
import lk.para.superMarket.dto.CustomDTO;
import lk.para.superMarket.dto.ItemDTO;
import lk.para.superMarket.dto.OrderDTO;
import lk.para.superMarket.entity.CustomEntity;
import lk.para.superMarket.entity.Customer;
import lk.para.superMarket.entity.Item;

import java.sql.SQLException;
import java.util.ArrayList;

public class DashBoardBOImpl implements DashBoardBO {

    private final ItemDAO itemDAO = (ItemDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ITEM);
    private final QueryDAO queryDAO = (QueryDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.QUERYDAO);
    private final OrderBO orderBO = (OrderBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.ORDER);

    @Override
    public ArrayList<ItemDTO> getAllItemName() throws SQLException, ClassNotFoundException {

        ArrayList<Item> all = itemDAO.getAll();

        ArrayList<ItemDTO> allItem = new ArrayList<>();

        for (Item item : all) {

            allItem.add(new ItemDTO(item.getItemCode(),
                    item.getDescription(),
                    item.getPackSize(),
                    item.getUnitPrice(),
                    item.getQtyOnHand(),
                    item.getDiscount()));
        }
        return allItem;
    }

    @Override
    public ArrayList<ItemDTO> getSearchICodeDescription(String enteredText) throws SQLException, ClassNotFoundException {
        ArrayList<Item> items = itemDAO.searchItems(enteredText);
        ArrayList<ItemDTO> itDto=new ArrayList<>();

        for (Item item : items) {
            itDto.add(new ItemDTO(
                    item.getItemCode(),
                    item.getDescription(),
                    item.getPackSize(),
                    item.getUnitPrice(),
                    item.getQtyOnHand(),
                    item.getDiscount()
            ));
        }
        return itDto;
    }

    @Override
    public ArrayList<CustomDTO> getCustomerBuyingItems(String text) throws SQLException, ClassNotFoundException {
        ArrayList<CustomEntity> searchItem = queryDAO.getSearchItemsBYItemCodeAndDescription(text);

        ArrayList<CustomDTO> customDTOS=new ArrayList<>();

        for (CustomEntity customEntity : searchItem) {
            customDTOS.add(new CustomDTO(
                    customEntity.getCusID(),
                    customEntity.getItemCode(),
                    customEntity.getDescription()
            ));
        }
        return customDTOS;

    }

    @Override
    public ArrayList<String> getCmbCustomerIds() throws SQLException, ClassNotFoundException {
        CustomerDAO customerDAO=new CustomerDAOImpl();

        ArrayList<Customer> all = customerDAO.getAll();
        ArrayList<String> allIds=new ArrayList<>();


        for (Customer customer : all) {
            allIds.add(customer.getCusID());
        }

        return allIds;
    }

    @Override
    public int getOrderCount() throws SQLException, ClassNotFoundException {

        ArrayList<OrderDTO> allOrders = orderBO.getAllOrder();

        int total = 0;
        for (OrderDTO allOrder : allOrders) {
            total += 1;
        }
        return total;
    }

    @Override
    public int getItemTypes() throws SQLException, ClassNotFoundException {
        ArrayList<Item> all = itemDAO.getAll();

        int tot = 0;
        for (Item item : all) {
            tot += 1;
        }

        return tot;
    }

    @Override
    public int getTotalCustomer() throws SQLException, ClassNotFoundException {
        CustomerDAO customerDAO=new CustomerDAOImpl();
        int total=0;

        for (Customer customer : customerDAO.getAll()) {
            total+=1;
        }
        return total;
    }

}
