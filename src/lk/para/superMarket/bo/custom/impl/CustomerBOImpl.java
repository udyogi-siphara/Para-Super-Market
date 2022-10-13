/**
 * @author : Udyogi Siphara
 * Project Name: Super Market
 * Date        : 5/26/2022
 * Time        : 10:14 AM
 */

package lk.para.superMarket.bo.custom.impl;

import lk.para.superMarket.bo.custom.CustomerBO;
import lk.para.superMarket.dao.DAOFactory;
import lk.para.superMarket.dao.custom.CustomerDAO;
import lk.para.superMarket.dao.custom.impl.CustomerDAOImpl;
import lk.para.superMarket.dto.CustomerDTO;
import lk.para.superMarket.entity.Customer;

import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerBOImpl implements CustomerBO {
    private final CustomerDAO customerDAO = (CustomerDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.CUSTOMER);

    @Override
    public ArrayList<CustomerDTO> getAllCustomers() throws SQLException, ClassNotFoundException {
        ArrayList<Customer> all = customerDAO.getAll();
        ArrayList<CustomerDTO> allCustomer =  new ArrayList<>();
        for (Customer customer : all) {
            allCustomer.add(new CustomerDTO(customer.getCusID(),customer.getCusTitle(),customer.getCusName(),customer.getCusAddress(),customer.getCity(),customer.getProvince(),customer.getPostalCode()));
        }
        return allCustomer;
    }

    @Override
    public boolean saveCustomer(CustomerDTO dto) throws SQLException, ClassNotFoundException {
        return customerDAO.save(new Customer(dto.getCusID(),dto.getCusTitle(),dto.getCusName(),dto.getCusAddress(),dto.getCity(),dto.getProvince(),dto.getPostalCode()));
    }

    @Override
    public boolean updateCustomer(CustomerDTO dto) throws SQLException, ClassNotFoundException {
        return customerDAO.update(new Customer(dto.getCusID(),dto.getCusTitle(),dto.getCusName(),dto.getCusAddress(),dto.getCity(),dto.getProvince(),dto.getPostalCode()));
    }

    @Override
    public boolean customerExist(String CusID) throws SQLException, ClassNotFoundException {
        return customerDAO.exist(CusID);
    }

    @Override
    public boolean deleteCustomer(String CusID) throws SQLException, ClassNotFoundException {
        return customerDAO.delete(CusID);
    }

    @Override
    public String generateNewCustomerID() throws SQLException, ClassNotFoundException {
        return customerDAO.generateNewID();
    }
}
