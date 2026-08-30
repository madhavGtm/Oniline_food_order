package online_food_order_system.online_food_order_system.models;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name= " admin_data")
public class Admin {

    private  String name;
    private  String address;
    private  String contact;
    @Id
    private  String email;
    private String password;

    public Admin() {
    }

    public Admin(String name, String address, String contact, String email) {
        this.name = name;
        this.address = address;
        this.contact = contact;
        this.email = email;
        this.password = password;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

