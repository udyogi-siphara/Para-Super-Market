/**
 * @author : Udyogi Siphara
 * Project Name: SuperMarketCopy
 * Date        : 6/2/2022
 * Time        : 12:13 PM
 */

package lk.para.superMarket.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import lk.para.superMarket.bo.BOFactory;
import lk.para.superMarket.bo.custom.OrderBO;
import lk.para.superMarket.bo.custom.OrderDetailBO;
import lk.para.superMarket.bo.custom.impl.OrderBOImpl;
import lk.para.superMarket.bo.custom.impl.OrderDetailBOImpl;
import lk.para.superMarket.dto.CustomDTO;
import lk.para.superMarket.dto.OrderDTO;
import lk.para.superMarket.dto.OrderDetailDTO;
import lk.para.superMarket.view.tdm.CustomTM;
import lk.para.superMarket.view.tdm.OrderDetailTM;
import lk.para.superMarket.view.tdm.OrderTM;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;

public class AllOrdersFormController {

    public TableView<OrderDetailTM>tblOrderDetails;
    public TableView<OrderTM> tblOrder;
    public Label lblOrderDetailsTotal;
    public TextField txtSearchOrder;
    public TextField txtSearchOrderDetails;

    private final OrderBO orderBO = (OrderBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.ORDER);
    private final OrderDetailBO orderDetailBO = (OrderDetailBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.ORDER_DETAIL);

    public void initialize() {
        tblOrder.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("orderId"));
        tblOrder.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("orderDate"));
        tblOrder.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("cusId"));

        tblOrderDetails.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("code"));
        tblOrderDetails.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("description"));
        tblOrderDetails.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("qty"));
        tblOrderDetails.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("discount"));
        tblOrderDetails.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        tblOrderDetails.getColumns().get(5).setCellValueFactory(new PropertyValueFactory<>("total"));



        loadAllOrders();

        tblOrder.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {

            if (newValue != null) {


                    searchOrderDetails(newValue.getOrderId());


            }

        });


    }

    public void loadAllOrders() {
        tblOrder.getItems().clear();

        try {
            ArrayList<OrderDTO> allOrders = orderBO.getAllOrder();
            for (OrderDTO order : allOrders) {
                tblOrder.getItems().add(new OrderTM(order.getOrderId(), order.getOrderDate(), order.getCusId()));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void menuItemDeleteOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        OrderTM selectedItem = tblOrder.getSelectionModel().getSelectedItem();
        boolean b = orderBO.deleteOrder(selectedItem.getOrderId());

        if (b) {
            new Alert(Alert.AlertType.INFORMATION, "Deleted SussesFull").show();
            tblOrderDetails.getItems().clear();
            tblOrder.getItems().clear();
            initialize();
        } else {

            new Alert(Alert.AlertType.INFORMATION, "Something Went Wrong..").show();
        }
    }

    private void searchOrderDetails(String newValue) {
        //Search by ID
        String value = "%" + newValue + "%";

        ArrayList<OrderDetailDTO> oDetailDto = null;

        try {

            oDetailDto = orderDetailBO.searchOrderDetail(value);


        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


        ObservableList<OrderDetailTM> orderDetailsTMS = FXCollections.observableArrayList();

        double allTotal = 0;

        for (OrderDetailDTO od : oDetailDto) {
            orderDetailsTMS.add(new OrderDetailTM(od.getOrderId(),
                    od.getItemCode(),
                    od.getOrderQty(),
                    BigDecimal.valueOf(od.getUnitPrice()),
                    od.getDiscount(),
                    od.getTotal()));

           allTotal += od.getTotal().doubleValue();
        }

        tblOrderDetails.getItems().clear();
        tblOrderDetails.getItems().addAll(orderDetailsTMS);
        tblOrderDetails.refresh();
        lblOrderDetailsTotal.setText(String.valueOf(allTotal));


    }


    public void searchOrderOnAction(KeyEvent keyEvent) throws SQLException, ClassNotFoundException {
        if (keyEvent.getCode().equals(KeyCode.ENTER)) {
            String value = "%" + txtSearchOrder.getText() + "%";

            ArrayList<OrderDTO> orderDto = orderBO.searchOrder(value);


            ObservableList<OrderTM> orderTMS = FXCollections.observableArrayList();


            for (OrderDTO od : orderDto) {
                orderTMS.add(new OrderTM(
                        od.getOrderId(),
                        od.getOrderDate(),
                        od.getCusId()
                ));

            }

            tblOrder.getItems().clear();
            tblOrder.getItems().addAll(orderTMS);
            tblOrder.refresh();
        }
    }

    public void searchOrderDetailOnAction(KeyEvent keyEvent) {
        if (keyEvent.getCode().equals(KeyCode.ENTER)) {
            tblOrderDetails.getItems().clear();
            searchOrderDetails(txtSearchOrderDetails.getText());
        }
    }
}
