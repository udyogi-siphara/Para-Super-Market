package lk.para.superMarket.controller;

import com.jfoenix.controls.JFXButton;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;

public class HomeFormController {
    public AnchorPane icnSideAnchorPane;
    public AnchorPane NameSideAnchorPane;
    public AnchorPane MainAnchorePane;
    public Label lblUserType;
    public JFXButton btnCustomer;
    public JFXButton btnItem;
    public JFXButton btnOrder;
    public JFXButton btnReports;
    public Label lblDate;
    public Label lblTime;
    public JFXButton btnAllOrders;
    public JFXButton btnLogOut;

    String userType;

    public void initialize() throws IOException {
        NameSideAnchorPane.setVisible(false);
        setUI("DashBoardForm");
        loadDateAndTime();

    }

    private void loadDateAndTime() {
        lblDate.setText(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e->{
            LocalTime currentTime = LocalTime.now();
            lblTime.setText(currentTime.getHour()+":"+currentTime.getMinute()+":"+currentTime.getSecond());
        }),
                new KeyFrame(Duration.seconds(1))
        );
        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();
    }


    public void iconSideOnAction(MouseEvent mouseEvent) {
        NameSideAnchorPane.setVisible(true);
    }

    public void NameSideOnAction(MouseEvent mouseEvent) {
        NameSideAnchorPane.setVisible(false);
    }

    public void setUI(String URI) throws IOException {
        MainAnchorePane.getChildren().clear();
        MainAnchorePane.getChildren().add(FXMLLoader.load(getClass().getResource("/lk/para/superMarket/view/" + URI + ".fxml")));
    }

    public void btnButtonOnAction(MouseEvent mouseEvent) throws IOException {
        Object button = mouseEvent.getSource();
        if(button instanceof JFXButton){
            JFXButton jfxButton = (JFXButton) button;
            if (jfxButton.getId().equals("CustomerButton")){
                setUI("CustomerForm");
            }else if (jfxButton.getId().equals("ItemButton")){
                setUI("ItemForm");
            }else if (jfxButton.getId().equals("OrderButton")){
                setUI("OrderForm");
            }else if (jfxButton.getId().equals("ReportsButton")){
                setUI("MainReportsForm");
            }else if (jfxButton.getId().equals("AllOrdersButton")){
                setUI("AllOrdersForm");
            }else if(jfxButton.getId().equals("LogOutButton")){
                FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/LoginForm.fxml"));
                Parent parent= loader.load();
                Stage stage =new Stage(StageStyle.DECORATED);
                stage.setTitle("Login");
                stage.setScene(new Scene(parent));
                stage.show();

                Stage stage1 = (Stage) btnCustomer.getScene().getWindow();
                stage1.close();
            }
        }
    }

    public void getAllData(String value) {
        this.userType = value;
        lblUserType.setText(value);

        if(value.equalsIgnoreCase("Admin")){
            btnOrder.setDisable(true);
            btnCustomer.setDisable(true);
        }else if(value.equalsIgnoreCase("Cashier")){
            btnReports.setDisable(true);
            btnItem.setDisable(true);
        }
    }

    public void imgDashBoardOnAction(MouseEvent mouseEvent) throws IOException {
        setUI("DashBoardForm");
    }
}
