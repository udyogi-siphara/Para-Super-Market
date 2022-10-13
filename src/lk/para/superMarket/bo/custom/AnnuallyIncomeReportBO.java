
package lk.para.superMarket.bo.custom;


import lk.para.superMarket.bo.SuperBO;
import lk.para.superMarket.dto.CustomDTO;

import java.sql.SQLException;
import java.util.ArrayList;


public interface AnnuallyIncomeReportBO extends SuperBO {
    ArrayList<CustomDTO> getAnnuallyIncomeReportDetails() throws SQLException, ClassNotFoundException;

    public long[][] getAnnuallyData(int yearOne,int YearTwo) throws SQLException, ClassNotFoundException;
}
