/**
 * @author : Udyogi Siphara
 * Project Name: SuperMarketCopy
 * Date        : 6/3/2022
 * Time        : 11:18 PM
 */

package lk.para.superMarket.bo.custom;

import lk.para.superMarket.bo.SuperBO;
import lk.para.superMarket.dto.CustomDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface MainReportBO extends SuperBO {
    ArrayList<CustomDTO> MostMovableItem() throws SQLException, ClassNotFoundException;

    ArrayList<CustomDTO> LeastMovableItem() throws SQLException, ClassNotFoundException;
}
