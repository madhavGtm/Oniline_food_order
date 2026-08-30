package online_food_order_system.online_food_order_system.models;

import jakarta.persistence.*;

@Entity
@Table(name = "order_userview")
public class Order_User {

    @Id
    @Column(name = "order_id")
    private Integer orderId;

    private String name;
    private String address;
    private String contact;
    private String email;
    private String pincode;

    @Column(name = "food_id")
    private Integer foodId;

    private Integer quantity;

    @Column(name = "total_price")
    private Double totalPrice;

    @Column(name = "payment_method")
    private String paymentMethod;

    @Column(name = "delivery_address")
    private String deliveryAddress;

    @Column(name = "order_status")
    private String orderStatus;

    public Order_User() {
    }

    public Order_User(Integer orderId, String name, String address, String contact, String email, String pincode, Integer foodId, Integer quantity, Double totalPrice, String paymentMethod, String deliveryAddress, String orderStatus) {
        this.orderId = orderId;
        this.name = name;
        this.address = address;
        this.contact = contact;
        this.email = email;
        this.pincode = pincode;
        this.foodId = foodId;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
        this.paymentMethod = paymentMethod;
        this.deliveryAddress = deliveryAddress;
        this.orderStatus = orderStatus;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public Integer getFoodId() {
        return foodId;
    }

    public void setFoodId(Integer foodId) {
        this.foodId = foodId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

// Generate Getters and Setters
}