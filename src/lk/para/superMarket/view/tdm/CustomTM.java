/**
 * @author : Udyogi Siphara
 * Project Name: SuperMarketCopy
 * Date        : 6/2/2022
 * Time        : 11:37 PM
 */

package lk.para.superMarket.view.tdm;


import java.math.BigDecimal;
import java.time.LocalDate;

public class CustomTM {
    private String CusID;
    private String CusTitle;
    private String CusName;
    private String CusAddress;
    private String City;
    private String Province;
    private String PostalCode;

    private String itemCode;
    private String description;
    private String packSize;
    private BigDecimal unitPrice;
    private int qtyOnHand;

    private String orderId;
    private LocalDate orderDate;
    private String cusId;

    private int orderQty;
    private double discount;
    private double total;

    public CustomTM() {
    }

    public CustomTM( String orderId, String itemCode, int orderQty ,BigDecimal unitPrice, double discount,double total) {
        this.itemCode = itemCode;
        this.unitPrice = unitPrice;
        this.orderId = orderId;
        this.orderQty = orderQty;
        this.discount = discount;
        this.total = total;
    }

    public CustomTM(String itemCode, String description, String packSize, BigDecimal unitPrice, int orderQty) {
        this.itemCode = itemCode;
        this.description = description;
        this.packSize = packSize;
        this.unitPrice = unitPrice;
        this.orderQty = orderQty;
    }

    public CustomTM(LocalDate orderDate, String itemCode, String description, BigDecimal unitPrice, int orderQty, double discount, double total) {
        this.itemCode = itemCode;
        this.description = description;
        this.unitPrice = unitPrice;
        this.orderQty = orderQty;
        this.discount=discount;
        this.total = total;
        this.orderDate = orderDate;
    }

    public CustomTM( String CusID,String itemCode, String description) {
        this.CusID = CusID;
        this.itemCode = itemCode;
        this.description = description;
    }

    public String getCusID() {
        return CusID;
    }

    public void setCusID(String cusID) {
        CusID = cusID;
    }

    public String getCusTitle() {
        return CusTitle;
    }

    public void setCusTitle(String cusTitle) {
        CusTitle = cusTitle;
    }

    public String getCusName() {
        return CusName;
    }

    public void setCusName(String cusName) {
        CusName = cusName;
    }

    public String getCusAddress() {
        return CusAddress;
    }

    public void setCusAddress(String cusAddress) {
        CusAddress = cusAddress;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public String getProvince() {
        return Province;
    }

    public void setProvince(String province) {
        Province = province;
    }

    public String getPostalCode() {
        return PostalCode;
    }

    public void setPostalCode(String postalCode) {
        PostalCode = postalCode;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPackSize() {
        return packSize;
    }

    public void setPackSize(String packSize) {
        this.packSize = packSize;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public int getQtyOnHand() {
        return qtyOnHand;
    }

    public void setQtyOnHand(int qtyOnHand) {
        this.qtyOnHand = qtyOnHand;
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

    public int getOrderQty() {
        return orderQty;
    }

    public void setOrderQty(int orderQty) {
        this.orderQty = orderQty;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "CustomTM{" +
                "CusID='" + CusID + '\'' +
                ", CusTitle='" + CusTitle + '\'' +
                ", CusName='" + CusName + '\'' +
                ", CusAddress='" + CusAddress + '\'' +
                ", City='" + City + '\'' +
                ", Province='" + Province + '\'' +
                ", PostalCode='" + PostalCode + '\'' +
                ", itemCode='" + itemCode + '\'' +
                ", description='" + description + '\'' +
                ", packSize='" + packSize + '\'' +
                ", unitPrice=" + unitPrice +
                ", qtyOnHand=" + qtyOnHand +
                ", orderId='" + orderId + '\'' +
                ", orderDate=" + orderDate +
                ", cusId='" + cusId + '\'' +
                ", orderQty=" + orderQty +
                ", discount=" + discount +
                ", total=" + total +
                '}';
    }
}
