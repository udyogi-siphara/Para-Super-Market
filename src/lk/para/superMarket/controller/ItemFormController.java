package lk.para.superMarket.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import lk.para.superMarket.bo.BOFactory;
import lk.para.superMarket.bo.custom.ItemBO;
import lk.para.superMarket.bo.custom.impl.ItemBOImpl;
import lk.para.superMarket.dto.CustomerDTO;
import lk.para.superMarket.dto.ItemDTO;
import lk.para.superMarket.util.ValidationUtil;
import lk.para.superMarket.view.tdm.CustomerTM;
import lk.para.superMarket.view.tdm.ItemTM;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.regex.Pattern;

public class ItemFormController {
    public JFXTextField txtItmCode;
    public JFXTextField txtItmDescription;
    public JFXTextField txtItmPackSize;
    public JFXTextField txtUnitPrice;
    public JFXTextField txtItmQtyOnHand;
    public TableView tblItmTable;
    public TableColumn colItmCode;
    public TableColumn colItmDescription;
    public TableColumn colItmPackSize;
    public TableColumn colItmUnitPrice;
    public TableColumn colItmQtyOnHand;
    public TableColumn colItmDiscount;
    public TextField txtSearchItem;
    public JFXButton btnAddItem;


    private final ItemBO itemBO = (ItemBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.ITEM);
    double editDiscount;

    LinkedHashMap<JFXTextField, Pattern> itm = new LinkedHashMap<>();
    Pattern ItemDescriptionPattern = Pattern.compile("^[A-z ]{4,50}$");
    Pattern ItemPackSizePattern = Pattern.compile("^[A-z0-9 ,/]{1,20}$");
    Pattern ItemPricePattern = Pattern.compile("^\\d+(,\\d{1,2})?$");
    Pattern ItemQtyPattern = Pattern.compile("^[0-9]{1,}$");


    public void initialize(){
        storeValidations();

        colItmCode.setCellValueFactory(new PropertyValueFactory("itemCode"));
        colItmDescription.setCellValueFactory(new PropertyValueFactory("description"));
        colItmPackSize.setCellValueFactory(new PropertyValueFactory("packSize"));
        colItmUnitPrice.setCellValueFactory(new PropertyValueFactory("unitPrice"));
        colItmQtyOnHand.setCellValueFactory(new PropertyValueFactory("qtyOnHand"));

        loadAllItem();
        txtItmCode.setText(generateNewID());
    }

    private void storeValidations() {
        btnAddItem.setDisable(true);
        itm.put(txtItmDescription, ItemDescriptionPattern);
        itm.put(txtItmPackSize, ItemPackSizePattern);
        itm.put(txtUnitPrice, ItemPricePattern);
        itm.put(txtItmQtyOnHand, ItemQtyPattern);
    }


    public void loadAllItem(){
        tblItmTable.getItems().clear();

        try {
            ArrayList<ItemDTO> allItem = itemBO.getAllItems();
            for (ItemDTO item : allItem) {
                tblItmTable.getItems().add(new ItemTM(item.getItemCode(),item.getDescription(),item.getPackSize(),item.getUnitPrice(),item.getQtyOnHand()));
            }

        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }
    public void btnAddItem(ActionEvent actionEvent) {
        String code = txtItmCode.getText();
        String description = txtItmDescription.getText();
        String packSize = txtItmPackSize.getText();
        BigDecimal unitPrice = new BigDecimal(txtUnitPrice.getText()).setScale(2);
        int qtyOnHand =  Integer.parseInt(txtItmQtyOnHand.getText());



        if (btnAddItem.getText().equalsIgnoreCase("Add Item")) {

            try {
                if (existItem(code)) {
                    new Alert(Alert.AlertType.ERROR, code + " already exists").show();
                }
                itemBO.saveItem(new ItemDTO(code, description, packSize, unitPrice, qtyOnHand));
                tblItmTable.getItems().add(new ItemTM(code, description, packSize, unitPrice, qtyOnHand));
                new Alert(Alert.AlertType.CONFIRMATION, "Added Item Successfully").show();
                clearAllTexts();

            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, "Failed to save the item " + e.getMessage()).show();
                e.printStackTrace();

            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

        }else{
            try {
                if (!existItem(code)) {
                    new Alert(Alert.AlertType.ERROR, "There is no such item associated with the id " + code).show();
                }

                itemBO.updateItem(new ItemDTO(code, description, packSize, unitPrice, qtyOnHand));
                loadAllItem();
                new Alert(Alert.AlertType.CONFIRMATION, "Updated Item Successfully").show();
                btnAddItem.setText("Add Customer");
                clearAllTexts();

            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, "Failed to update the item " + code + e.getMessage()).show();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    boolean existItem(String code) throws SQLException, ClassNotFoundException {
        return itemBO.itemExist(code);
    }

    public void menuEditOnAction(ActionEvent actionEvent) {
        btnAddItem.setText("Update");
        ItemTM selectItem = (ItemTM) tblItmTable.getSelectionModel().getSelectedItem();
        txtItmCode.setText(selectItem.getItemCode());
        txtItmDescription.setText(selectItem.getDescription());
        txtItmPackSize.setText(selectItem.getPackSize());
        txtUnitPrice.setText(String.valueOf(selectItem.getUnitPrice()));
        txtItmQtyOnHand.setText(String.valueOf(selectItem.getQtyOnHand()));
        editDiscount = selectItem.getDiscount();
    }

    public void menuDeleteOnAction(ActionEvent actionEvent) {
        ItemTM selectItem = (ItemTM) tblItmTable.getSelectionModel().getSelectedItem();
        String code = selectItem.getItemCode();
        try {
            if (!existItem(code)) {
                new Alert(Alert.AlertType.ERROR, "There is no such item associated with the code " + code).show();
            }
            itemBO.deleteItem(code);
            tblItmTable.getItems().remove(tblItmTable.getSelectionModel().getSelectedItem());
            tblItmTable.getSelectionModel().clearSelection();
            new Alert(Alert.AlertType.CONFIRMATION, "Deleted Item Successfully").show();
            txtItmCode.setText(generateNewID());


        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to delete the item " + code).show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private String generateNewID(){
        try {
            return itemBO.generateNewItemCode();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to generate a new id " + e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        if (tblItmTable.getItems().isEmpty()) {
            return "I00-001";
        } else {
            String id = getLastItemCode();
            int newCustomerId = Integer.parseInt(id.replace("C", "")) + 1;
            return String.format("I00-%03d", newCustomerId);
        }
    }

    private String getLastItemCode() {
        List<ItemTM> tempCustomersList = new ArrayList<>(tblItmTable.getItems());
        Collections.sort(tempCustomersList);
        return tempCustomersList.get(tempCustomersList.size() - 1).getItemCode();
    }


    public void btnItemClear(ActionEvent actionEvent) {
        txtItmDescription.clear();
        txtItmPackSize.clear();
        txtUnitPrice.clear();
        txtItmQtyOnHand.clear();
    }

    public void clearAllTexts(){
        txtItmCode.clear();
        txtItmDescription.clear();
        txtItmPackSize.clear();
        txtUnitPrice.clear();
        txtItmQtyOnHand.clear();
    }

    public void textFieldValidationOnAction(KeyEvent keyEvent) {
        Object response = ValidationUtil.validateJFXTextField(itm, btnAddItem);
        if (keyEvent.getCode() == KeyCode.ENTER) {
            if (response instanceof JFXTextField) {
                JFXTextField errorText = (JFXTextField) response;
                errorText.requestFocus();
            } else if (response instanceof Boolean) {

            }
        }
    }

    public void searchDetailsOnAction(KeyEvent keyEvent) throws SQLException, ClassNotFoundException {
        String search = "%" + txtSearchItem.getText() + "%";

        if (keyEvent.getCode().equals(KeyCode.ENTER)) {
            ArrayList<ItemDTO> itemDTOS = itemBO.searchItems(search);
            ObservableList<ItemTM> oBItemTMS = FXCollections.observableArrayList();

            for (ItemDTO itemDto : itemDTOS) {
                oBItemTMS.add(new ItemTM(itemDto.getItemCode(),
                        itemDto.getDescription(),
                        itemDto.getPackSize(),
                        itemDto.getUnitPrice(),
                        itemDto.getQtyOnHand()));
            }
            tblItmTable.getItems().clear();
            tblItmTable.getItems().addAll(oBItemTMS);
            tblItmTable.refresh();
        }
    }
}
