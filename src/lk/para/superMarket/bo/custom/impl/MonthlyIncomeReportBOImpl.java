/**
 * @author : Hasitha Lakshan
 * Project :SuperMarket
 * Date :6/4/2022
 * Time :6:47 PM
 */

package lk.para.superMarket.bo.custom.impl;



import lk.para.superMarket.bo.custom.MonthlyIncomeReportBO;
import lk.para.superMarket.dao.custom.QueryDAO;
import lk.para.superMarket.dao.custom.impl.QueryDAOImpl;
import lk.para.superMarket.dto.CustomDTO;
import lk.para.superMarket.entity.CustomEntity;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class MonthlyIncomeReportBOImpl implements MonthlyIncomeReportBO {

    @Override
    public ArrayList<CustomDTO> getMonthlyIncomeReportDetails(int year) throws SQLException, ClassNotFoundException {
        QueryDAO queryDAO = new QueryDAOImpl();

        ArrayList<CustomEntity> monthlyIncomeReportDetails = queryDAO.getMonthlyIncomeReportDetails(year);

        ArrayList<CustomDTO> customDTOS = new ArrayList<>();

        for (CustomEntity monthlyDetail : monthlyIncomeReportDetails) {
            double total = (monthlyDetail.getUnitPrice().doubleValue() * monthlyDetail.getOrderQty()) - monthlyDetail.getDiscount();

            customDTOS.add(new CustomDTO(
                    monthlyDetail.getOrderDate(),
                    monthlyDetail.getItemCode(),
                    monthlyDetail.getUnitPrice(),
                    monthlyDetail.getOrderQty(),
                    monthlyDetail.getDiscount(),
                    total

            ));
        }

        return customDTOS;

    }

    @Override
    public double[] getMonthTotal(int year) throws SQLException, ClassNotFoundException {

        ArrayList<CustomDTO> monthlyIncomeReportDetails = getMonthlyIncomeReportDetails(year);

        double[] ar = new double[12];

        double jan = 0;
        double feb = 0;
        double march = 0;
        double april = 0;
        double may = 0;
        double jun = 0;
        double july = 0;
        double aug = 0;
        double sep = 0;
        double oct = 0;
        double nov = 0;
        double dec = 0;


        for (CustomDTO monthlyIncomeReportDetail : monthlyIncomeReportDetails) {
            LocalDate d = monthlyIncomeReportDetail.getOrderDate();

            switch (d.getMonthValue()) {
                case 1:
                    jan = jan+(monthlyIncomeReportDetail.getUnitPrice().doubleValue()*monthlyIncomeReportDetail.getOrderQty()) - (monthlyIncomeReportDetail.getDiscount());
                    break;
                case 2:
                    feb = feb+(monthlyIncomeReportDetail.getUnitPrice().doubleValue()*monthlyIncomeReportDetail.getOrderQty()) - (monthlyIncomeReportDetail.getDiscount());
                    break;
                case 3:
                    march = march + (monthlyIncomeReportDetail.getUnitPrice().doubleValue()*monthlyIncomeReportDetail.getOrderQty()) - (monthlyIncomeReportDetail.getDiscount());
                    break;
                case 4:
                    april = april + (monthlyIncomeReportDetail.getUnitPrice().doubleValue()*monthlyIncomeReportDetail.getOrderQty()) - (monthlyIncomeReportDetail.getDiscount());
                    break;
                case 5:
                    may = may + (monthlyIncomeReportDetail.getUnitPrice().doubleValue()*monthlyIncomeReportDetail.getOrderQty()) - (monthlyIncomeReportDetail.getDiscount());
                    break;
                case 6:
                    jun += (monthlyIncomeReportDetail.getUnitPrice().doubleValue()*monthlyIncomeReportDetail.getOrderQty()) - (monthlyIncomeReportDetail.getDiscount());
                    break;
                case 7:
                    july += (monthlyIncomeReportDetail.getUnitPrice().doubleValue()*monthlyIncomeReportDetail.getOrderQty()) - (monthlyIncomeReportDetail.getDiscount());
                    break;
                case 8:
                    aug += (monthlyIncomeReportDetail.getUnitPrice().doubleValue()*monthlyIncomeReportDetail.getOrderQty()) - (monthlyIncomeReportDetail.getDiscount());
                    break;
                case 9:
                    sep += (monthlyIncomeReportDetail.getUnitPrice().doubleValue()*monthlyIncomeReportDetail.getOrderQty()) - (monthlyIncomeReportDetail.getDiscount());
                    break;
                case 10:
                    oct += (monthlyIncomeReportDetail.getUnitPrice().doubleValue()*monthlyIncomeReportDetail.getOrderQty()) - (monthlyIncomeReportDetail.getDiscount());
                    break;
                case 11:
                    nov += (monthlyIncomeReportDetail.getUnitPrice().doubleValue()*monthlyIncomeReportDetail.getOrderQty()) - (monthlyIncomeReportDetail.getDiscount());
                    break;
                case 12:
                    dec += (monthlyIncomeReportDetail.getUnitPrice().doubleValue()*monthlyIncomeReportDetail.getOrderQty()) - (monthlyIncomeReportDetail.getDiscount());
                    break;
            }

            ar[0] = jan;
            ar[1] = feb;
            ar[2] = march;
            ar[3] = april;
            ar[4] = may;
            ar[5] = jun;
            ar[6] = july;
            ar[7] = aug;
            ar[8] = sep;
            ar[9] = oct;
            ar[10] = nov;
            ar[11] = dec;

        }
        return ar;
    }
}
