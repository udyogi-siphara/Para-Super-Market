/**
 * @author : Udyogi Siphara
 * Project Name: Super Market
 * Date        : 5/27/2022
 * Time        : 11:52 AM
 */

package lk.para.superMarket.dao.custom.impl;

import lk.para.superMarket.dao.SQLUtil;
import lk.para.superMarket.dao.custom.QueryDAO;
import lk.para.superMarket.entity.CustomEntity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class QueryDAOImpl implements QueryDAO {

    @Override
    public ArrayList<CustomEntity> MostMovableItem() throws SQLException, ClassNotFoundException {
        ResultSet resultset = SQLUtil.executeQuery("SELECT item.ItemCode,Description,item.PackSize,item.UnitPrice,SUM(OrderQTY) from item inner join orderdetail on item.ItemCode = orderdetail.ItemCode GROUP BY ItemCode ORDER BY SUM(OrderQTY) DESC");

        ArrayList<CustomEntity> cusItem = new ArrayList<>();

        while (resultset.next()) {
            cusItem.add(new CustomEntity(
                    resultset.getString(1),
                    resultset.getString(2),
                    resultset.getString(3),
                    resultset.getBigDecimal(4),
                    resultset.getInt(5)
            ));
        }
        return cusItem;
    }

    @Override
    public ArrayList<CustomEntity> LeastMovableItem() throws SQLException, ClassNotFoundException {
        ResultSet resultset = SQLUtil.executeQuery("SELECT item.ItemCode,Description,item.PackSize,item.UnitPrice,SUM(OrderQTY) from item inner join orderdetail on item.ItemCode = orderdetail.ItemCode GROUP BY ItemCode ORDER BY SUM(OrderQTY) ASC");

        ArrayList<CustomEntity> cusItem = new ArrayList<>();

        while (resultset.next()) {
            cusItem.add(new CustomEntity(
                    resultset.getString(1),
                    resultset.getString(2),
                    resultset.getString(3),
                    resultset.getBigDecimal(4),
                    resultset.getInt(5)
            ));
        }
        return cusItem;
    }

    @Override
    public ArrayList<CustomEntity> getDailyIncomeReportDetails(int year,int month) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.executeQuery("SELECT `orders`.OrderDate, orderdetail.ItemCode,item.Description,orderdetail.UnitPrice,orderdetail.OrderQty,orderdetail.Discount FROM `orders` INNER JOIN orderdetail ON  `orders`.OrderID=orderdetail.OrderId INNER JOIN item ON  item.ItemCode=OrderDetail.ItemCode WHERE YEAR(`orders`.OrderDate)=? AND MONTH(`orders`.OrderDate)=? ", year, month);
        ArrayList<CustomEntity> customEntity=new ArrayList<>();

        while (resultSet.next()){
            double total=0;
            customEntity.add(new CustomEntity(
                            resultSet.getDate(1).toLocalDate(),
                            resultSet.getString(2),
                            resultSet.getString(3),
                            resultSet.getBigDecimal(4),
                            resultSet.getInt(5),
                            resultSet.getDouble(6),
                            total=(resultSet.getDouble(4)*resultSet.getInt(5))-resultSet.getDouble(6)
                    )
            );
        }

        return customEntity;
    }

    @Override
    public ArrayList<CustomEntity> getAnnuallyIncomeReportDetails() throws SQLException, ClassNotFoundException {
        ResultSet result = SQLUtil.executeQuery("SELECT `orders`.OrderDate, orderdetail.ItemCode,orderdetail.UnitPrice,orderdetail.OrderQty,orderdetail.Discount FROM `orders` INNER JOIN orderdetail ON `orders`.OrderID=orderdetail.OrderId");

        ArrayList<CustomEntity> customEnt = new ArrayList<>();

        while (result.next()) {
            double total = (result.getDouble(3) * result.getInt(4)) - result.getDouble(5);

            customEnt.add(new CustomEntity(
                    result.getDate(1).toLocalDate(),
                    result.getString(2),
                    result.getBigDecimal(3),
                    result.getInt(4),
                    result.getDouble(5),
                    total

            ));
        }

        return customEnt;
    }

    @Override
    public ArrayList<CustomEntity> getMonthlyIncomeReportDetails(int year) throws SQLException, ClassNotFoundException {
        ResultSet result = SQLUtil.executeQuery("SELECT `orders`.OrderDate, orderdetail.ItemCode,orderdetail.UnitPrice,orderdetail.OrderQty,orderdetail.Discount FROM `orders` INNER JOIN orderdetail ON `orders`.OrderID=orderdetail.OrderId WHERE year(`orders`.OrderDate)=?", year);

        ArrayList<CustomEntity> customEnt=new ArrayList<>();

        while (result.next()) {
            double total= (result.getDouble(3)* result.getInt(4))- result.getDouble(5);

            customEnt.add(new CustomEntity(
                    result.getDate(1).toLocalDate(),
                    result.getString(2),
                    result.getBigDecimal(3),
                    result.getInt(4),
                    result.getDouble(5),
                    total

            ));
        }

        return customEnt;
    }

    @Override
    public ArrayList<CustomEntity> searchItemsBYItemCodeAndDescription(String enteredText) throws SQLException, ClassNotFoundException {
        ResultSet result = SQLUtil.executeQuery("SELECT customer.CusID,item.ItemCode,item.Description FROM  `orders`  INNER JOIN customer ON `orders`.CusID=customer.`CusID`  INNER JOIN OrderDetail ON `orders`.OrderID=OrderDetail.OrderID INNER JOIN item ON OrderDetail.ItemCode=item.ItemCode WHERE `orders`.CusID=? ", enteredText);

        ArrayList<CustomEntity> customEnt = new ArrayList<>();

        while (result.next()) {
            customEnt.add(new CustomEntity(
                    result.getString(1),
                    result.getString(2),
                    result.getString(3)
            ));
        }

        return customEnt;
    }

    @Override
    public ArrayList<CustomEntity> getSearchItemsBYItemCodeAndDescription(String enteredText) throws SQLException, ClassNotFoundException {
        ResultSet result = SQLUtil.executeQuery("SELECT customer.CusID,item.ItemCode,item.Description FROM  `orders`  INNER JOIN customer ON `orders`.CusID=customer.`CusID`  INNER JOIN OrderDetail ON `orders`.OrderID=OrderDetail.OrderID INNER JOIN item ON OrderDetail.ItemCode=item.ItemCode WHERE `orders`.CusID=? ", enteredText);

        ArrayList<CustomEntity> customEnt = new ArrayList<>();

        while (result.next()) {
            System.out.println(result.getString(1)+""+result.getString(2)+""+result.getString(3));
            customEnt.add(new CustomEntity(
                    result.getString(1),
                    result.getString(2),
                    result.getString(3)
            ));
        }

        return customEnt;
    }

}
