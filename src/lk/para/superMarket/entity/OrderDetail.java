/**
 * @author : Udyogi Siphara
 * Project Name: Super Market
 * Date        : 5/27/2022
 * Time        : 11:48 AM
 */

package lk.para.superMarket.entity;

import java.math.BigDecimal;

public class OrderDetail {
    private String orderId;
    private String itemCode;
    private int orderQty;
    private BigDecimal discount;
    private BigDecimal unitPrice;
    private BigDecimal total;

    public OrderDetail() {
    }

    public OrderDetail(String orderId, String itemCode, int orderQty, BigDecimal discount) {
        this.orderId = orderId;
        this.itemCode = itemCode;
        this.orderQty = orderQty;
        this.discount = discount;
    }

    public OrderDetail(String orderId, String itemCode, int orderQty, BigDecimal discount, BigDecimal unitPrice, BigDecimal total) {
        this.orderId = orderId;
        this.itemCode = itemCode;
        this.orderQty = orderQty;
        this.discount = discount;
        this.unitPrice = unitPrice;
        this.total = total;
    }

    public OrderDetail(String orderId, String itemCode, int orderQty, BigDecimal discount, BigDecimal unitPrice) {
        this.orderId = orderId;
        this.itemCode = itemCode;
        this.orderQty = orderQty;
        this.discount = discount;
        this.unitPrice = unitPrice;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public int getOrderQty() {
        return orderQty;
    }

    public void setOrderQty(int orderQty) {
        this.orderQty = orderQty;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "OrderDetail{" +
                "orderId='" + orderId + '\'' +
                ", itemCode='" + itemCode + '\'' +
                ", orderQty=" + orderQty +
                ", discount=" + discount +
                ", unitPrice=" + unitPrice +
                ", total=" + total +
                '}';
    }
}
