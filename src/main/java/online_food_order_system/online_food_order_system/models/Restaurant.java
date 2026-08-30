package online_food_order_system.online_food_order_system.models;


import jakarta.persistence.*;

import java.time.LocalTime;

@Entity
@Table(name = "restaurants")
public class Restaurant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String restaurantName;

    private String address;

    private String contact;

    private String email;

    // Restaurant Image
    private String image;

    // This field will NOT be stored in restaurants table
    @Transient
    private String password;

    private LocalTime openingTime;

    private LocalTime closingTime;

    public Restaurant() {
    }

    public Restaurant(Integer id, String restaurantName, String address,
                      String contact, String email, String image,
                      String password, LocalTime openingTime,
                      LocalTime closingTime) {
        this.id = id;
        this.restaurantName = restaurantName;
        this.address = address;
        this.contact = contact;
        this.email = email;
        this.image = image;
        this.password = password;
        this.openingTime = openingTime;
        this.closingTime = closingTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
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

    // Image Getter & Setter
    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalTime getOpeningTime() {
        return openingTime;
    }

    public void setOpeningTime(LocalTime openingTime) {
        this.openingTime = openingTime;
    }

    public LocalTime getClosingTime() {
        return closingTime;
    }

    public void setClosingTime(LocalTime closingTime) {
        this.closingTime = closingTime;
    }
}