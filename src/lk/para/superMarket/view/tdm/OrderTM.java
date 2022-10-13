/**
 * @author : Udyogi Siphara
 * Project Name: Super Market
 * Date        : 5/27/2022
 * Time        : 11:47 AM
 */

package lk.para.superMarket.view.tdm;

import javax.xml.crypto.Data;
import java.time.LocalDate;

public class OrderTM {
    private String orderId;
    private LocalDate orderDate;
    private String cusId;

    public OrderTM() {
    }

    public OrderTM(String orderId, LocalDate orderDate, String cusId) {
        this.orderId = orderId;
        this.orderDate = orderDate;
        this.cusId = cusId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }

    public String getCusId() {
        return cusId;
    }

    public void setCusId(String cusId) {
        this.cusId = cusId;
    }

    @Override
    public String toString() {
        return "OrderTM{" +
                "orderId='" + orderId + '\'' +
                ", orderDate=" + orderDate +
                ", cusId='" + cusId + '\'' +
                '}';
    }
}
