package lk.para.superMarket.bo;

import lk.para.superMarket.bo.custom.impl.*;


public class BOFactory {
    private static BOFactory boFactory;

    private BOFactory() {
    }

    public static BOFactory getBoFactory() {
        if (boFactory == null) {
            boFactory = new BOFactory();
        }
        return boFactory;
    }

    public SuperBO getBO(BOTypes types) {
        switch (types) {
            case CUSTOMER:
                return new CustomerBOImpl();
            case ITEM:
                return new ItemBOImpl();
            case PURCHASE_ORDER:
                return new PurchaseOrderBOImpl();
            case ANNUALLY_INCOME:
                return new AnnuallyIncomeReportBOImpl();
            case DAILY_INCOME:
                return new DailyIncomeReportBOImpl();
            case DASHBOARD:
                return new DashBoardBOImpl();
            case MAIN_REPORT:
                return new MainReportBOImpl();
            case MONTHLY_INCOME:
                return new MonthlyIncomeReportBOImpl();
            case ORDER:
                return new OrderBOImpl();
            case ORDER_DETAIL:
                return new OrderDetailBOImpl();
                default:
                return null;
        }
    }

    public enum BOTypes {
        CUSTOMER, ITEM, PURCHASE_ORDER, ANNUALLY_INCOME, DAILY_INCOME, DASHBOARD, MAIN_REPORT, MONTHLY_INCOME, ORDER, ORDER_DETAIL
    }

}
