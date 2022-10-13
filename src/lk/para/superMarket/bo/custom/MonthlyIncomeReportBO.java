package lk.para.superMarket.bo.custom;

import lk.para.superMarket.bo.SuperBO;
import lk.para.superMarket.dto.CustomDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface MonthlyIncomeReportBO extends SuperBO {
    ArrayList<CustomDTO> getMonthlyIncomeReportDetails(int year) throws SQLException, ClassNotFoundException;

    public double[] getMonthTotal(int year) throws SQLException, ClassNotFoundException;
}
