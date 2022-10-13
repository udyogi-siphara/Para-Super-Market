/**
 * @author : Udyogi Siphara
 * Project Name: SuperMarketCopy
 * Date        : 6/3/2022
 * Time        : 8:40 PM
 */

package lk.para.superMarket.controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import lk.para.superMarket.bo.BOFactory;
import lk.para.superMarket.bo.custom.MainReportBO;
import lk.para.superMarket.bo.custom.impl.MainReportBOImpl;
import lk.para.superMarket.dto.CustomDTO;
import lk.para.superMarket.view.tdm.CustomTM;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class MainReportsFormController {

    public AnchorPane apnMain;
    public TableView<CustomTM>tblMostItem;
    public TableView<CustomTM>tblLeastItem;
    public JFXButton btnAnually;

    private final MainReportBO mainReportBO = (MainReportBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.MAIN_REPORT);

    public void initialize() throws SQLException, ClassNotFoundException {
        tblMostItem.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("itemCode"));
        tblMostItem.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("description"));
        tblMostItem.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("packSize"));
        tblMostItem.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        tblMostItem.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("orderQty"));


        tblLeastItem.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("itemCode"));
        tblLeastItem.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("description"));
        tblLeastItem.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("packSize"));
        tblLeastItem.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        tblLeastItem.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("orderQty"));

        setMostItem();
        setLeastItem();
    }

    private void setMostItem() throws SQLException, ClassNotFoundException {
        ArrayList<CustomDTO> item = mainReportBO.MostMovableItem();
        for (CustomDTO customDTO : item) {
            tblMostItem.getItems().add(new CustomTM(customDTO.getItemCode(), customDTO.getDescription(), customDTO.getPackSize(), customDTO.getUnitPrice(), customDTO.getOrderQty()));
        }

    }

    private void setLeastItem() throws SQLException, ClassNotFoundException {
        ArrayList<CustomDTO> item = mainReportBO.LeastMovableItem();
        for (CustomDTO customDTO : item) {
            tblLeastItem.getItems().add(new CustomTM(customDTO.getItemCode(), customDTO.getDescription(), customDTO.getPackSize(), customDTO.getUnitPrice(), customDTO.getOrderQty()));
        }
    }

    public void btnDailyReportOnAction(ActionEvent actionEvent) throws IOException {
        //new SlideInUp(apnMain).play();
        apnMain.getChildren().clear();
        Parent parent = FXMLLoader.load(getClass().getResource("../view/DailyReportForm.fxml"));
        apnMain.getChildren().add(parent);
    }

    public void btnMonthlyReportOnAction(ActionEvent actionEvent) throws IOException {
//        new SlideInUp(apnMain).play();
        apnMain.getChildren().clear();
        Parent parent = FXMLLoader.load(getClass().getResource("../view/MonthlyReportForm.fxml"));
        apnMain.getChildren().add(parent);
    }

    public void btnAnuallyReportOnAction(ActionEvent actionEvent) throws IOException {
//        new SlideInUp(apnMain).play();
        apnMain.getChildren().clear();
        Parent parent = FXMLLoader.load(getClass().getResource("../view/AnnuallyReportForm.fxml"));
        apnMain.getChildren().add(parent);
    }
}
