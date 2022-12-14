package lk.para.superMarket.dao;

import lk.para.superMarket.dao.custom.impl.*;

public class DAOFactory {
    private static DAOFactory daoFactory;

    private DAOFactory() {
    }

    //singleton
    public static DAOFactory getDaoFactory() {
        if (daoFactory == null) {
            daoFactory = new DAOFactory();
        }
        return daoFactory;
    }


    public enum DAOTypes {
        CUSTOMER, ITEM, ORDER, ORDERDETAILS, QUERYDAO
    }


    public SuperDAO getDAO(DAOTypes types) {
        switch (types) {
            case CUSTOMER:
                return new CustomerDAOImpl();
            case ITEM:
                return new ItemDAOImpl();
            case ORDER:
                return  new OrderDAOImpl();
            case ORDERDETAILS:
                return new OrderDetailDAOImpl();
            case QUERYDAO:
                return new QueryDAOImpl();
                default:
                return null;
        }
    }
}
