

package lk.para.superMarket.bo.custom.impl;



import lk.para.superMarket.bo.custom.AnnuallyIncomeReportBO;
import lk.para.superMarket.dao.custom.QueryDAO;
import lk.para.superMarket.dao.custom.impl.QueryDAOImpl;
import lk.para.superMarket.dto.CustomDTO;
import lk.para.superMarket.entity.CustomEntity;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class AnnuallyIncomeReportBOImpl implements AnnuallyIncomeReportBO {

    @Override
    public ArrayList<CustomDTO> getAnnuallyIncomeReportDetails() throws SQLException, ClassNotFoundException {
        QueryDAO queryDAO = new QueryDAOImpl();

        ArrayList<CustomEntity> monthlyIncomeReportDetails = queryDAO.getAnnuallyIncomeReportDetails();

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
    public long[][] getAnnuallyData(int txtSearchYearOne,int txtSearchYearTwo) throws SQLException, ClassNotFoundException {

        ArrayList<CustomDTO> anDetail = getAnnuallyIncomeReportDetails();

        //use +1 to get earnings this entered year to other year.
        int count = txtSearchYearTwo - txtSearchYearOne + 1;

        long[][] ar = new long[count][2];  //5

        int tempYear = txtSearchYearTwo;

        for (int i = count - 1; i >= 0; i--) {
            ar[i][0] = tempYear;
            tempYear--;
        }

        for (long[] x : ar
        ) {

        }

        /*while (result.next()) {
            //getLocalDate
            LocalDate d = result.getDate(1).toLocalDate();

            for (int i = 0; i < count; i++) {

                if (d.getYear() == ar[i][0]) {

//                    ar[i][1]+=result.getDouble(2);
                    ar[i][1] += (result.getDouble(3) * result.getInt(4)) - result.getDouble(5);
                }
            }

        }*/

        for (CustomDTO customDTO : anDetail) {
            //getLocalDate
            LocalDate d = customDTO.getOrderDate();

            for (int i = 0; i < count; i++) {

                if (d.getYear() == ar[i][0]) {

//                    ar[i][1]+=result.getDouble(2);
                    ar[i][1] += (customDTO.getUnitPrice().doubleValue() * customDTO.getOrderQty()) -customDTO.getDiscount();
                }
            }
        }


        return ar;
    }


}
