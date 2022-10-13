/**
 * @author : Udyogi Siphara
 * Project Name: Super Market
 * Date        : 5/27/2022
 * Time        : 11:52 AM
 */

package lk.para.superMarket.dao.custom;

import lk.para.superMarket.dao.SuperDAO;
import lk.para.superMarket.entity.CustomEntity;

import java.sql.SQLException;
import java.util.ArrayList;


public interface QueryDAO extends SuperDAO {
    ArrayList<CustomEntity> MostMovableItem() throws SQLException, ClassNotFoundException;
    ArrayList<CustomEntity> LeastMovableItem() throws SQLException, ClassNotFoundException;

    ArrayList<CustomEntity> getDailyIncomeReportDetails(int year,int Month) throws SQLException, ClassNotFoundException;
    ArrayList<CustomEntity> getAnnuallyIncomeReportDetails() throws SQLException, ClassNotFoundException;
    ArrayList<CustomEntity> getMonthlyIncomeReportDetails(int Year) throws SQLException, ClassNotFoundException;
    ArrayList<CustomEntity> searchItemsBYItemCodeAndDescription(String enteredText) throws SQLException, ClassNotFoundException;
    ArrayList<CustomEntity> getSearchItemsBYItemCodeAndDescription(String enteredText) throws SQLException, ClassNotFoundException;

}
