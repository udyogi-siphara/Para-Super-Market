
package lk.para.superMarket.bo.custom;

import lk.para.superMarket.bo.SuperBO;
import lk.para.superMarket.dto.CustomDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface DailyIncomeReportBO extends SuperBO {
    ArrayList<CustomDTO> getDailyIncomeReportDetails(int year, int month) throws SQLException, ClassNotFoundException;

    int getMonth(String month);
}
