package online_food_order_system.online_food_order_system.services;

import online_food_order_system.online_food_order_system.Repository.CartRepository;
import online_food_order_system.online_food_order_system.models.Cart;
import online_food_order_system.online_food_order_system.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartService
{

    @Autowired
    private CartRepository cartRepository;

    // Save Cart Item
    public Cart save(Cart cart) {
        return cartRepository.save(cart);
    }

    // Get All Cart Items
    public List<Cart> getAll() {
        return cartRepository.findAll();
    }

    // Get Cart By Id
    public Cart getById(Integer id) {
        return cartRepository.findById(id).orElse(null);
    }

    // Get Cart Items Of Particular User
    public List<Cart> getCartByUser(User user) {
        return cartRepository.findByUser(user);
    }

    // Delete Cart Item
    public void delete(Integer id) {
        cartRepository.deleteById(id);
    }

    // Increase Quantity
    public void increaseQuantity(Integer id) {

        Cart cart = getById(id);

        if (cart != null) {
            cart.setQuantity(cart.getQuantity() + 1);
            cartRepository.save(cart);
        }
    }

    // Decrease Quantity
    public void decreaseQuantity(Integer id) {

        Cart cart = getById(id);

        if (cart != null) {

            if (cart.getQuantity() > 1) {
                cart.setQuantity(cart.getQuantity() - 1);
                cartRepository.save(cart);
            } else {
                cartRepository.delete(cart);
            }
        }
    }

    // Calculate Total Amount
    public Double getTotal(User user) {

        List<Cart> cartList = getCartByUser(user);

        double total = 0;

        for (Cart cart : cartList) {
            total += cart.getPrice() * cart.getQuantity();
        }

        return total;
    }

    // Clear Cart After Order
    public void clearCart(User user) {

        List<Cart> cartList = getCartByUser(user);

        cartRepository.deleteAll(cartList);
    }
}
