-- Sample data for the online food ordering system.
-- MySQL/MariaDB compatible and safe to run more than once.

START TRANSACTION;

-- Administrators
INSERT INTO admin_data (email, address, contact, name, password)
VALUES ('admin@foodhub.test', '12 Residency Road, Bengaluru', '9000000001', 'FoodHub Admin', 'Admin@123')
ON DUPLICATE KEY UPDATE
    address = VALUES(address),
    contact = VALUES(contact),
    name = VALUES(name),
    password = VALUES(password);

-- Customers
INSERT INTO user_data (email, address, contact, name, password, pincode)
VALUES
    ('aarav@foodhub.test', '42 Lake View Road, Bengaluru', '9000000011', 'Aarav Sharma', 'User@123', '560001'),
    ('meera@foodhub.test', '18 Green Park, Bengaluru', '9000000012', 'Meera Nair', 'User@123', '560038'),
    ('kabir@foodhub.test', '77 Maple Street, Bengaluru', '9000000013', 'Kabir Singh', 'User@123', '560095')
ON DUPLICATE KEY UPDATE
    address = VALUES(address),
    contact = VALUES(contact),
    name = VALUES(name),
    password = VALUES(password),
    pincode = VALUES(pincode);

-- Restaurant profiles
INSERT INTO restaurants
    (id, address, closing_time, contact, email, image, opening_time, restaurant_name)
VALUES
    (1, '24 Church Street, Bengaluru', '23:00:00', '9000000101', 'spicegarden@foodhub.test', 'restaurant-spice-garden.png', '10:30:00', 'Spice Garden'),
    (2, '81 Indiranagar 100 Feet Road, Bengaluru', '23:30:00', '9000000102', 'forno@foodhub.test', 'restaurant-forno.png', '11:00:00', 'Forno & Flour'),
    (3, '16 Koramangala 5th Block, Bengaluru', '22:30:00', '9000000103', 'urbanbowl@foodhub.test', 'restaurant-urban-bowl.png', '09:30:00', 'Urban Bowl')
ON DUPLICATE KEY UPDATE
    address = VALUES(address),
    closing_time = VALUES(closing_time),
    contact = VALUES(contact),
    email = VALUES(email),
    image = VALUES(image),
    opening_time = VALUES(opening_time),
    restaurant_name = VALUES(restaurant_name);

-- Login accounts. Passwords are plain text because the existing login service uses plain-text comparison.
INSERT INTO login_data (id, email, password, usertype)
VALUES
    (1, 'admin@foodhub.test', 'Admin@123', 'admin'),
    (2, 'aarav@foodhub.test', 'User@123', 'user'),
    (3, 'meera@foodhub.test', 'User@123', 'user'),
    (4, 'kabir@foodhub.test', 'User@123', 'user'),
    (5, 'spicegarden@foodhub.test', 'Restaurant@123', 'restaurant'),
    (6, 'forno@foodhub.test', 'Restaurant@123', 'restaurant'),
    (7, 'urbanbowl@foodhub.test', 'Restaurant@123', 'restaurant')
ON DUPLICATE KEY UPDATE
    email = VALUES(email),
    password = VALUES(password),
    usertype = VALUES(usertype);

-- Menu categories
INSERT INTO category_data (id, category_name, image)
VALUES
    (1, 'Main Course', 'paneer-butter-masala.png'),
    (2, 'Rice & Biryani', 'vegetable-biryani.png'),
    (3, 'South Indian', 'masala-dosa.png'),
    (4, 'Pizza & Italian', 'margherita-pizza.png'),
    (5, 'Burgers & Fast Food', 'veg-burger.png'),
    (6, 'Asian', 'vegetable-hakka-noodles.png'),
    (7, 'Desserts', 'chocolate-brownie.png'),
    (8, 'Sides', 'cheesy-garlic-bread.png')
ON DUPLICATE KEY UPDATE
    category_name = VALUES(category_name),
    image = VALUES(image);

-- Food menu
INSERT INTO food_data
    (id, description, food_name, image, price, category_id, restaurant_id)
VALUES
    (1, 'Soft paneer cubes simmered in a creamy tomato and butter gravy, served with naan.', 'Paneer Butter Masala', 'paneer-butter-masala.png', 289.00, 1, 1),
    (2, 'Fragrant basmati rice slow-cooked with seasonal vegetables, saffron, mint, and fried onions.', 'Vegetable Dum Biryani', 'vegetable-biryani.png', 249.00, 2, 1),
    (3, 'Crisp golden dosa filled with spiced potatoes and served with chutneys and sambar.', 'Masala Dosa', 'masala-dosa.png', 149.00, 3, 1),
    (4, 'Stone-baked pizza topped with tomato sauce, mozzarella, and fresh basil.', 'Margherita Pizza', 'margherita-pizza.png', 329.00, 4, 2),
    (5, 'Penne pasta tossed in a creamy roasted tomato sauce with parmesan and basil.', 'Creamy Tomato Penne', 'creamy-tomato-pasta.png', 299.00, 4, 2),
    (6, 'Oven-baked bread sticks brushed with garlic butter, parsley, and melted cheese.', 'Cheesy Garlic Bread', 'cheesy-garlic-bread.png', 189.00, 8, 2),
    (7, 'Crispy vegetable patty, cheese, lettuce, tomato, and onion in a toasted sesame bun.', 'Classic Veg Burger', 'veg-burger.png', 219.00, 5, 3),
    (8, 'Wok-tossed noodles with bell peppers, cabbage, carrots, spring onion, and sesame.', 'Vegetable Hakka Noodles', 'vegetable-hakka-noodles.png', 239.00, 6, 3),
    (9, 'Warm fudgy chocolate brownie finished with chocolate sauce and vanilla ice cream.', 'Chocolate Brownie', 'chocolate-brownie.png', 179.00, 7, 3)
ON DUPLICATE KEY UPDATE
    description = VALUES(description),
    food_name = VALUES(food_name),
    image = VALUES(image),
    price = VALUES(price),
    category_id = VALUES(category_id),
    restaurant_id = VALUES(restaurant_id);

-- Active carts
INSERT INTO cart_data (id, price, quantity, food_id, user_email)
VALUES
    (1, 299.00, 1, 5, 'aarav@foodhub.test'),
    (2, 219.00, 2, 7, 'meera@foodhub.test'),
    (3, 149.00, 1, 3, 'kabir@foodhub.test')
ON DUPLICATE KEY UPDATE
    price = VALUES(price),
    quantity = VALUES(quantity),
    food_id = VALUES(food_id),
    user_email = VALUES(user_email);

-- Orders
INSERT INTO order_data
    (id, delivery_address, order_status, payment_method, quantity, total_price, food_id, user_id)
VALUES
    (1, '18 Green Park, Bengaluru - 560038', 'Delivered', 'UPI', 2, 578.00, 1, 'meera@foodhub.test'),
    (2, '42 Lake View Road, Bengaluru - 560001', 'Accepted', 'Cash on Delivery', 1, 329.00, 4, 'aarav@foodhub.test'),
    (3, '77 Maple Street, Bengaluru - 560095', 'Preparing', 'Card', 2, 478.00, 8, 'kabir@foodhub.test'),
    (4, '18 Green Park, Bengaluru - 560038', 'Delivered', 'Card', 2, 358.00, 9, 'meera@foodhub.test')
ON DUPLICATE KEY UPDATE
    delivery_address = VALUES(delivery_address),
    order_status = VALUES(order_status),
    payment_method = VALUES(payment_method),
    quantity = VALUES(quantity),
    total_price = VALUES(total_price),
    food_id = VALUES(food_id),
    user_id = VALUES(user_id);

-- Order line items
INSERT INTO order_item (id, price, quantity, total_price, food_id, order_id)
VALUES
    (1, 289.00, 2, 578.00, 1, 1),
    (2, 329.00, 1, 329.00, 4, 2),
    (3, 239.00, 2, 478.00, 8, 3),
    (4, 179.00, 2, 358.00, 9, 4)
ON DUPLICATE KEY UPDATE
    price = VALUES(price),
    quantity = VALUES(quantity),
    total_price = VALUES(total_price),
    food_id = VALUES(food_id),
    order_id = VALUES(order_id);

-- Denormalized order table mapped by Order_User.java
INSERT INTO order_user_view
    (order_id, address, contact, delivery_address, email, food_id, name, order_status, payment_method, pincode, quantity, total_price)
VALUES
    (1, '18 Green Park, Bengaluru', '9000000012', '18 Green Park, Bengaluru - 560038', 'meera@foodhub.test', 1, 'Meera Nair', 'Delivered', 'UPI', '560038', 2, 578.00),
    (2, '42 Lake View Road, Bengaluru', '9000000011', '42 Lake View Road, Bengaluru - 560001', 'aarav@foodhub.test', 4, 'Aarav Sharma', 'Accepted', 'Cash on Delivery', '560001', 1, 329.00),
    (3, '77 Maple Street, Bengaluru', '9000000013', '77 Maple Street, Bengaluru - 560095', 'kabir@foodhub.test', 8, 'Kabir Singh', 'Preparing', 'Card', '560095', 2, 478.00),
    (4, '18 Green Park, Bengaluru', '9000000012', '18 Green Park, Bengaluru - 560038', 'meera@foodhub.test', 9, 'Meera Nair', 'Delivered', 'Card', '560038', 2, 358.00)
ON DUPLICATE KEY UPDATE
    address = VALUES(address),
    contact = VALUES(contact),
    delivery_address = VALUES(delivery_address),
    email = VALUES(email),
    food_id = VALUES(food_id),
    name = VALUES(name),
    order_status = VALUES(order_status),
    payment_method = VALUES(payment_method),
    pincode = VALUES(pincode),
    quantity = VALUES(quantity),
    total_price = VALUES(total_price);

COMMIT;
