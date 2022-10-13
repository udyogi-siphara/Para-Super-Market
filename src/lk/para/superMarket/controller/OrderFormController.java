package lk.para.superMarket.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import lk.para.superMarket.bo.BOFactory;
import lk.para.superMarket.bo.custom.PurchaseOrderBO;
import lk.para.superMarket.bo.custom.impl.PurchaseOrderBOImpl;
import lk.para.superMarket.db.DBConnection;
import lk.para.superMarket.dto.CustomerDTO;
import lk.para.superMarket.dto.ItemDTO;
import lk.para.superMarket.dto.OrderDTO;
import lk.para.superMarket.dto.OrderDetailDTO;
import lk.para.superMarket.util.ValidationUtil;
import lk.para.superMarket.view.tdm.CustomerTM;
import lk.para.superMarket.view.tdm.OrderDetailTM;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class OrderFormController {


    public TableView<OrderDetailTM> tblOrderTable;
    public TableColumn colCode;
    public TableColumn colDescription;
    public TableColumn colQty;
    public TableColumn colUnitPrice;
    public TableColumn colDiscount;
    public TableColumn colTotal;
    public JFXComboBox<String>cmbCusId;
    public JFXTextField txtCusName;
    public JFXComboBox<String>cmbItemCode;
    public JFXTextField txtDescription;
    public JFXTextField txtQtyOnHand;
    public JFXTextField txtUnitPrice;
    public JFXTextField txtDiscount;
    public JFXTextField txtQty;
    public Label lblOrderId;
    public Label lblTotal;
    public JFXButton btnAdd;
    public JFXButton btnPlaceOrder;

    private final PurchaseOrderBO purchaseOrderBO = (PurchaseOrderBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.PURCHASE_ORDER);
    public Label lblDate;
    private String orderId;

    LinkedHashMap<JFXTextField, Pattern> ord = new LinkedHashMap<>();
    Pattern ItemDiscountPattern = Pattern.compile("^[0-9]{1,2}$");
    Pattern ItemQtyPattern = Pattern.compile("^[0-9]{1,10}$");

    public void initialize(){
        loadDate();
        storeValidations();

        colCode.setCellValueFactory(new PropertyValueFactory("code"));
        colDescription.setCellValueFactory(new PropertyValueFactory("description"));
        colQty.setCellValueFactory(new PropertyValueFactory("qty"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory("unitPrice"));
        colDiscount.setCellValueFactory(new PropertyValueFactory("discount"));
        colTotal.setCellValueFactory(new PropertyValueFactory("total"));


        cmbCusId.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            enableOrDisablePlaceOrderButton();

            if (newValue != null) {
                try {
                    /*Search Customer*/
                    Connection connection = DBConnection.getDbConnection().getConnection();
                    try {
                        if (!existCustomer(newValue + "")) {
//                            "There is no such customer associated with the id " + id
                            new Alert(Alert.AlertType.ERROR, "There is no such customer associated with the id " + newValue + "").show();
                        }

                        CustomerDTO search = purchaseOrderBO.searchCustomer(newValue + "");
                        txtCusName.setText(search.getCusName());

                    } catch (SQLException e) {
                        new Alert(Alert.AlertType.ERROR, "Failed to find the customer " + newValue + "" + e).show();
                    }

                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            } else {
                txtCusName.clear();
            }
        });

        cmbItemCode.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newItemCode) -> {
            txtQty.setEditable(newItemCode != null);
            btnAdd.setDisable(newItemCode == null);

            if (newItemCode != null) {

                /*Find Item*/
                try {
                    //Search Item
                    ItemDTO item = purchaseOrderBO.searchItem(newItemCode + "");
                    txtDescription.setText(item.getDescription());
                    txtUnitPrice.setText(item.getUnitPrice().setScale(2).toString());
                    Optional<OrderDetailTM> optOrderDetail = tblOrderTable.getItems().stream().filter(detail -> detail.getCode().equals(newItemCode)).findFirst();
                    txtQtyOnHand.setText((optOrderDetail.isPresent() ? item.getQtyOnHand() - optOrderDetail.get().getQty() : item.getQtyOnHand()) + "");

                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }

            } else {

                txtDescription.clear();
                txtQty.clear();
                txtDiscount.clear();
                txtQtyOnHand.clear();
                txtUnitPrice.clear();
            }
        });

        loadAllCustomerIds();
        loadAllItemCodes();

    }

    private void loadDate() {
        lblDate.setText(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
    }

    private void storeValidations() {
        btnAdd.setDisable(true);
        ord.put(txtDiscount, ItemDiscountPattern);
        ord.put(txtQty, ItemQtyPattern);
    }

    boolean existCustomer(String id) throws SQLException, ClassNotFoundException {
        return purchaseOrderBO.checkCustomerIsAvailable(id);
    }

    public String generateNewOrderId() {
        try {
            return purchaseOrderBO.generateNewOrderID();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to generate a new order id").show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return "OID-001";
    }

    private void loadAllCustomerIds() {
        try {
            ArrayList<CustomerDTO> all = purchaseOrderBO.getAllCustomers();
            for (CustomerDTO customerDTO : all) {
                cmbCusId.getItems().add(customerDTO.getCusID());
            }

        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to load customer ids").show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void loadAllItemCodes() {
        try {
            /*Get all items*/
            ArrayList<ItemDTO> all = purchaseOrderBO.getAllItems();
            for (ItemDTO dto : all) {
                cmbItemCode.getItems().add(dto.getItemCode());
            }

        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void menuDeleteOnAction(ActionEvent actionEvent) {
        tblOrderTable.getItems().remove(tblOrderTable.getSelectionModel().getSelectedItem());
        tblOrderTable.getSelectionModel().clearSelection();
        new Alert(Alert.AlertType.CONFIRMATION, "Deleted Customer Successfully").show();
        tblOrderTable.refresh();
        cmbCusId.getSelectionModel().clearSelection();
        cmbCusId.requestFocus();

    }

    public void btnAddOnAction(ActionEvent actionEvent) {
        System.out.println("Add");
        if (Integer.parseInt(txtQty.getText()) <= 0 ||
                Integer.parseInt(txtQty.getText()) > Integer.parseInt(txtQtyOnHand.getText())) {
            new Alert(Alert.AlertType.ERROR, "Invalid qty").show();
            txtQty.requestFocus();
            txtQty.selectAll();
            return;
        }

            String itemCode = cmbItemCode.getSelectionModel().getSelectedItem();
            String description = txtDescription.getText();
            BigDecimal unitPrice = new BigDecimal(txtUnitPrice.getText()).setScale(2);
            int qty = Integer.parseInt(txtQty.getText());
            BigDecimal oldTotal = unitPrice.multiply(new BigDecimal(qty)).setScale(2);
            BigDecimal tempDiscount = new BigDecimal(txtDiscount.getText()).setScale(2);
            BigDecimal discount = oldTotal.multiply(tempDiscount).divide(BigDecimal.valueOf(100));
            BigDecimal total = oldTotal.subtract(discount);



            boolean exists = tblOrderTable.getItems().stream().anyMatch(detail -> detail.getCode().equals(itemCode));

            if (exists) {
                OrderDetailTM orderDetailTM = tblOrderTable.getItems().stream().filter(detail -> detail.getCode().equals(itemCode)).findFirst().get();

                if (btnAdd.getText().equalsIgnoreCase("Add")) {
                    orderDetailTM.setQty(orderDetailTM.getQty() + qty);
                    total = new BigDecimal(orderDetailTM.getQty()).multiply(unitPrice).setScale(2);
                    orderDetailTM.setTotal(total);
                    discount = discount.add(orderDetailTM.getDiscount());
                    orderDetailTM.setDiscount(discount);
                    total = orderDetailTM.getTotal().subtract(discount);
                    orderDetailTM.setTotal(total);
                }
                tblOrderTable.refresh();
            } else {
                tblOrderTable.getItems().add(new OrderDetailTM(itemCode, description, qty, unitPrice, discount,total));
                tblOrderTable.refresh();
            }

            cmbItemCode.getSelectionModel().clearSelection();
            cmbItemCode.requestFocus();
            calculateTotal();
            enableOrDisablePlaceOrderButton();

    }

    private void calculateTotal() {
        BigDecimal total = new BigDecimal(0);
        for (OrderDetailTM detail : tblOrderTable.getItems()) {
            total = total.add(detail.getTotal());


        }
        lblTotal.setText(""+total);
    }

    private void enableOrDisablePlaceOrderButton() {
        btnPlaceOrder.setDisable(!(cmbCusId.getSelectionModel().getSelectedItem() != null && !tblOrderTable.getItems().isEmpty()));
    }


    public void btnPlaceOrderOnAction(ActionEvent actionEvent) {
        orderId = generateNewOrderId();
        System.out.println(orderId);
        boolean b = saveOrder(orderId, LocalDate.now(), cmbCusId.getValue(),
                tblOrderTable.getItems().stream().map(tm -> new OrderDetailDTO(orderId, tm.getCode(), tm.getQty(), tm.getUnitPrice().doubleValue(),tm.getDiscount())).collect(Collectors.toList()));
        if (b) {
            new Alert(Alert.AlertType.INFORMATION, "Order has been placed successfully").show();
        } else {
            new Alert(Alert.AlertType.ERROR, "Order has not been placed successfully").show();
        }


        lblOrderId.setText(orderId);
        cmbCusId.getSelectionModel().clearSelection();
        cmbItemCode.getSelectionModel().clearSelection();
        tblOrderTable.getItems().clear();
        txtQty.clear();
        calculateTotal();
    }
    public boolean saveOrder(String orderId, LocalDate orderDate, String customerId, List<OrderDetailDTO> orderDetails) {
        try {
            return purchaseOrderBO.purchaseOrder(new OrderDTO(orderId, orderDate, customerId, orderDetails));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void textFieldValidationOnAction(KeyEvent keyEvent) {
        Object response = ValidationUtil.validateJFXTextField(ord, btnAdd);
        if (keyEvent.getCode() == KeyCode.ENTER) {
            if (response instanceof JFXTextField) {
                JFXTextField errorText = (JFXTextField) response;
                errorText.requestFocus();
            } else if (response instanceof Boolean) {

            }

        }
    }
}
