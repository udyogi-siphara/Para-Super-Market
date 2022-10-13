/**
 * @author : Udyogi Siphara
 * Project Name: SuperMarketCopy
 * Date        : 6/3/2022
 * Time        : 9:29 PM
 */

package lk.para.superMarket.controller;

import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import lk.para.superMarket.bo.BOFactory;
import lk.para.superMarket.bo.custom.AnnuallyIncomeReportBO;
import lk.para.superMarket.bo.custom.impl.AnnuallyIncomeReportBOImpl;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;

public class AnnuallyReportFormController {
    public AnchorPane apnMain;
    public LineChart chartAnnually;
    public TextField txtSearchOne;
    public TextField txtSearchTwo;
    public Pane paneVisibale;

    private final AnnuallyIncomeReportBO annuallyIncomeReportBO = (AnnuallyIncomeReportBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.ANNUALLY_INCOME);


    public void initialize() throws SQLException, ClassNotFoundException {

        paneVisibale.setVisible(false);
        chartAnnually.lookup(".chart-plot-background").setStyle("-fx-background-color: transparent;");


        txtSearchTwo.setText(String.valueOf(getNowYear()));
        txtSearchOne.setText(String.valueOf(getNowYear() - 4));

        setAnnually();

    }

    public void backMouseClick(MouseEvent mouseEvent) throws IOException {
        apnMain.getChildren().clear();
        Parent parent = FXMLLoader.load(getClass().getResource("../view/MainReportsForm.fxml"));
        apnMain.getChildren().add(parent);
    }

    public void setAnnually() throws SQLException, ClassNotFoundException {

        long[][] ar = annuallyIncomeReportBO.getAnnuallyData(Integer.parseInt(txtSearchOne.getText()), Integer.parseInt(txtSearchTwo.getText()));

        //defining a series
        XYChart.Series series = new XYChart.Series();
        series.setName("Annually");
        //populating the series with data


        for (int i = 0; i < ar.length; i++) {
            series.getData().add(new XYChart.Data(String.valueOf(ar[i][0]), ar[i][1]));
        }

        chartAnnually.getData().add(series);
    }


    public void btnSearchOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        paneVisibale.setVisible(true);
        chartAnnually.getData().clear();
        setAnnually();
    }

    private int getNowYear() {
        LocalDate date = LocalDate.now();
        return date.getYear();

    }

    public void paneClickeOnAction(MouseEvent mouseEvent) throws SQLException, ClassNotFoundException {
        chartAnnually.getData().clear();
        setAnnually();
        paneVisibale.setVisible(false);
    }
}
