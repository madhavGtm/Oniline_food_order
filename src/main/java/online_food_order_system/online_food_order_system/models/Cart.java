package online_food_order_system.online_food_order_system.models;


import jakarta.persistence.*;

@Entity
@Table(name = "cart_data")
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_email")
    private User user;

    @ManyToOne
    @JoinColumn(name = "food_id")
    private Food food;

    private Integer quantity;

    private Double price;

    public Cart() {
    }

    public Cart(Integer id, User user, Food food, Integer quantity, Double price) {
        this.id = id;
        this.user = user;
        this.food = food;
        this.quantity = quantity;
        this.price = price;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Food getFood() {
        return food;
    }

    public void setFood(Food food) {
        this.food = food;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getTotalPrice() {
        return price * quantity;
    }
}
