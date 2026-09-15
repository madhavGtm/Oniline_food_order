package online_food_order_system.online_food_order_system.models;


import jakarta.persistence.*;

@Entity
@Table(name = "category_data")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String categoryName;

    private String image;

    public Category() {
    }

    public Category(Integer id, String categoryName, String image) {
        this.id = id;
        this.categoryName = categoryName;
        this.image = image;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}