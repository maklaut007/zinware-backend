package com.example.zinware.seed;

import com.example.zinware.model.*;
import com.example.zinware.model.cart.Cart;
import com.example.zinware.model.cart.CartItem;
import com.example.zinware.model.login.User;
import com.example.zinware.repository.*;
import com.example.zinware.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    CategoryRepository categoryRepository;
    ProductRepository productRepository;
    UserService userService;
    CartItemRepository cartItemRepository;
    CartRepository cartRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    public void setCategoryRepository(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Autowired
    public void setProductRepository(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setCartRepository(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    @Autowired
    public void setCartItemRepository(CartItemRepository cartItemRepository) {
        this.cartItemRepository = cartItemRepository;
    }

    /**
     * Runs on application startup
     *
     * @param args command line arguments
     * @throws Exception if something goes wrong
     */
    @Override
    public void run(String... args) throws Exception {
        loadUserData();
    }

    /**
     * Loads user data into database
     */
    private void loadUserData() {
        try {
            // Seed categories
            Category category1 = new Category("Mice", "PC mice for programmers and gamers", "https://media.discordapp.net/attachments/1112238965743964211/1113833439012081694/Allair_Gaming_mice_black_color_white_lights_dark_blue_backgroud_6584014f-6aff-438e-b1b9-c8fbd2368ff8.png?width=1298&height=865");
            Category category2 = new Category("Keyboards", "PC Keyboards for programmers and gamers", "https://cdn.discordapp.com/attachments/1112238965743964211/1113681140931362938/Allair_Gaming_keyboard_black_color_white_lights_dark_blue_backg_74961f9a-a214-4327-a118-4a30063f06ae.png");
            Category category3 = new Category("Headphones", "PC Headphones for programmers and gamers", "https://media.discordapp.net/attachments/1112238965743964211/1113832002462949386/Allair_Gaming_headphones_black_color_white_lights_dark_blue_bac_8e006651-9ed9-4bbe-9b19-5281af57ed97.png?width=1298&height=865");

            categoryRepository.save(category1);
            categoryRepository.save(category2);
            categoryRepository.save(category3);


            // Seed products
            Product product1 = new Product("Zinmouse 8080", "The Zinmouse 8080 is a flagship gaming mouse made by Zinware. It offers high precision and responsiveness, making it perfect for competitive gaming. With its sleek design and comfortable grip, it enhances your gaming experience", 39.99, "https://media.discordapp.net/attachments/1112238965743964211/1113833580687274054/Allair_single_gaming_mouse_black_color_white_lights_dark_blue_b_e093442e-5e23-479a-b3a3-07f7160d5cc7.png?width=1298&height=865", category1);
            Product product2 = new Product("Sharp Angle Zinboard 4200", "The Sharp Angle Zinboard 4200 is a flagship keyboard made by Zinware specifically for Angular developers. It features a durable construction and customizable backlighting. The keyboard's ergonomic design ensures comfort during long coding sessions. ", 49.99, "https://cdn.discordapp.com/attachments/1112238965743964211/1113681140931362938/Allair_Gaming_keyboard_black_color_white_lights_dark_blue_backg_74961f9a-a214-4327-a118-4a30063f06ae.png", category2);
            Product product3 = new Product("Zinphones 4", "The Zinphones 4 are flagship headphones made by Zinware. They deliver immersive sound quality and come with noise-canceling technology for an enhanced audio experience.", 79.00, "https://media.discordapp.net/attachments/1112238965743964211/1113832002462949386/Allair_Gaming_headphones_black_color_white_lights_dark_blue_bac_8e006651-9ed9-4bbe-9b19-5281af57ed97.png?width=1298&height=865", category3);
            Product product4 = new Product("Zinmouse Gold", "The Zinmouse Gold is a gaming mouse made by Zinware. It features a striking gold color and offers a high DPI of 16,000 for precise cursor movement. With its customizable buttons and ergonomic design, it provides ultra comfort during programming sessions.", 67.99, "https://media.discordapp.net/attachments/1112238965743964211/1113875242163634317/Allair_gaming_mouse_black_color_golden_lights_dark_blue_backgro_99632c35-544f-4725-9289-e104290ee43b.png?width=947&height=631", category1);
            Product product5 = new Product("Reactive Zinboard 3000", "The Reactive Zinboard 3000 is a keyboard designed specifically for React developers. It offers a responsive typing experience and features dedicated shortcut keys for common React functions.", 79.00, "https://media.discordapp.net/attachments/1112238965743964211/1115067347108102175/Allair_gaming_keyboard_black_color_white_lights_dark_blue_backg_6e8ecc8f-37e2-4334-a677-56053bbc2c09.png?width=1298&height=865", category2);
            Product product6 = new Product("Zinphones 5", "The Zinphones 5 are flagship headphones made by Zinware. They provide an immersive audio experience with high-fidelity sound and powerful bass. The headphones feature a comfortable over-ear design and come with detachable cables for convenience. ", 99.00, "https://media.discordapp.net/attachments/1112238965743964211/1115067421691224064/Allair_Gaming_headphones_black_color_white_lights_dark_blue_bac_6a91585a-0cdb-4a60-bd45-12971adbee7a.png?width=1298&height=865", category3);
            Product product7 = new Product("Zinmouse Premium Gold ", "The Zinmouse Premium Gold is a high-end gaming mouse made by Zinware. It boasts a staggering DPI of 25,000, ensuring exceptional precision and accuracy during gameplay.", 97.00, "https://media.discordapp.net/attachments/1112238965743964211/1113875247276498944/Allair_gaming_mouse_black_color_golden_lights_dark_blue_backgro_70f5f116-7308-46cc-b680-fbbba1efffc1.png?width=947&height=631", category1);

            productRepository.save(product1);
            productRepository.save(product2);
            productRepository.save(product3);
            productRepository.save(product4);
            productRepository.save(product5);
            productRepository.save(product6);
            productRepository.save(product7);

            User user1 = new User("Test User", "123456", "test@mail.com");
            userService.registerUser(user1);

            Cart cart1 = new Cart();

            cart1.setUser(user1);
            cartRepository.save(cart1);

            CartItem cartItem1 = new CartItem(cart1, product1, 1);
            CartItem cartItem2 = new CartItem(cart1, product2, 2);

            cartItemRepository.save(cartItem1);
            cartItemRepository.save(cartItem2);
            System.out.println("Data Loaded");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
