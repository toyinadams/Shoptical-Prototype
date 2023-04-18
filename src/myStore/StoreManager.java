package myStore;

import myStore.Inventory;
import myStore.Product;
import myStore.ShoppingCart;

import java.util.HashMap;
import java.util.List;

/**
 * The myStore.StoreManager class manages an myStore.Inventory and ShoppingCarts.
 * Each instance has an myStore.Inventory, assigns each user a unique cart ID,
 * and manages user ShoppingCarts.
 *
 * @author  Toyin Adams
 * @version 1.0
 */
public class StoreManager {
    private Inventory inventory;
    private HashMap<Integer, ShoppingCart> shoppingCarts;   // maps the cart id to the shopping cart
    private static Integer cartIDCount;

    /**
     * Constructs a myStore.StoreManager with an empty myStore.Inventory and empty storage of ShoppingCarts
     * modelled by an empty HashMap, mapping cart IDs to their respective myStore.ShoppingCart.
     * The field that keeps track of cart IDs is initialized.
     */
    public StoreManager() {
        this.inventory = new Inventory();
        this.shoppingCarts = new HashMap<Integer, ShoppingCart>();
        cartIDCount = 0;
    }

    /**
     * Returns the stock of a specified item in the myStore.StoreManager's inventory.
     *
     * @param product myStore.Product, the specified product
     * @return the stock of a specified item in the myStore.StoreManager's inventory
     */
    public Integer getStock(Product product) {
        return inventory.getProductQuantity(product);
    }

    /**
     * Returns the stock of the myStore.Product corresponding to the specified product id in
     * the myStore.StoreManager's inventory.
     *
     * @param productID int, the specified product id
     * @return the stock of a specified item in the myStore.StoreManager's inventory
     */
    public Integer getStock(int productID) {
        Product product = inventory.getProduct(productID);
        return inventory.getProductQuantity(product);
    }

    /**
     * Returns the stock of the myStore.Product corresponding to the specified product id in
     * the shopping cart corresponding to the specified cart id.
     *
     * @param cartID Integer, the specified cartID
     * @param productID int, the specified product id
     * @return the stock of a specified item in the myStore.StoreManager's inventory
     */
    public Integer getStock(Integer cartID, int productID) {
        Product product = shoppingCarts.get(cartID).getProduct(productID);
        return shoppingCarts.get(cartID).getProductQuantity(product);
    }

    /**
     * Returns the myStore.Product corresponding to the specified product id in myStore.Inventory.
     * If the product does not exist in myStore.Inventory, it returns null.
     *
     * @param productID the product id of the product
     * @return the product corresponding to the specified product id if it exists in myStore.Inventory, otherwise null
     */
    public Product getProduct(int productID) {
        Product product = null;              // Returns null unless the myStore.myStore.Product exists in myStore.myStore.Inventory
        for (Product key : inventory.getProducts()) {
            if (key.getId() == productID) {
                product = key;
            }
        }
        return product;
    }

    /**
     * Adds a new cart to the shopping carts field of myStore.StoreManager
     * and returns the unique corresponding cart ID.
     *
     * @return Integer, cart ID
     */
    public Integer newCartId() {
        ShoppingCart shoppingCart = new ShoppingCart();
        Integer cartID = cartIDCount;
        shoppingCarts.put(cartID, shoppingCart);
        cartIDCount++;
        return cartID;
    }

    /**
     * Adds to the myStore.Inventory the specified stock of the specified myStore.Product.
     *
     * @param product myStore.Product, the product to be added
     * @param addStock the amount of stock to be added
     */
    public void initInventory(Product product, Integer addStock) {
        inventory.addProductQuantity(product, addStock);
    }

    /**
     * Adds the specified quantity of the specified item to the myStore.ShoppingCart corresponding
     * to the specified cart ID.
     *
     * @param cartID    Integer, the id of the shopping cart
     * @param productID int, the id of the product
     * @param quantity  the quantity of the product
     */
    public void addToCart(Integer cartID, int productID, Integer quantity) {
        Product product = inventory.getProduct(productID);
            if (quantity <= inventory.getProductQuantity(product)) {
                shoppingCarts.get(cartID).addProductQuantity(product, quantity);
                inventory.removeProductQuantity(product, quantity);
            }
    }

    /**
     * Removes the specified quantity of the specified item to the myStore.ShoppingCart corresponding
     * to the specified cart ID.
     *
     * @param cartID    Integer, the id of the shopping cart
     * @param productID int, the id of the product
     * @param quantity  the quantity of the product
     */
    public void removeFromCart(Integer cartID, int productID, Integer quantity) {
        if (!shoppingCarts.isEmpty()) {
            Product product = inventory.getProduct(productID);
            if (quantity <= shoppingCarts.get(cartID).getProductQuantity(product)) {
                shoppingCarts.get(cartID).removeProductQuantity(product, quantity);
                inventory.addProductQuantity(product, quantity);
            }
        }
    }

    /**
     * Returns a HashMap with each myStore.Product mapped to its quantity in the myStore.ShoppingCart
     * corresponding to the specified cartID.
     *
     * @return HashMap<myStore.myStore.Product, Integer>, the contents of the myStore.ShoppingCart
     */
    public List<Product> getProductsCart(int cartID) {
        return shoppingCarts.get(cartID).getProducts();
    }

    /**
     * Empties the contents of the myStore.ShoppingCart corresponding to the specified cart ID, back into myStore.Inventory.
     *
     * @param cartID Integer, cart ID corresponding to the cart to be emptied
     */
    public void initCart(Integer cartID) {
        for (Product p : inventory.getProducts()) {
            shoppingCarts.get(cartID).addProductQuantity(p, 0);
        }
    }

    /**
     * Calculates and returns the total amount of each item in the specified
     * cart and displays transaction details.
     *
     * @param cartID Integer, the cart ID
     * @return double, total amount of all item in specified cart
     */
    public double processTransaction(Integer cartID) {
        double total = 0;
        String s = "|-----------------------------------------------------CHECKOUT" +
                "------------------------------------------------------|\n" +
                "\n|myStore.Product Name                |myStore.Product ID                  " +
                "|Unit Price                  |Quantity                    |\n";
        for (Product p : shoppingCarts.get(cartID).getProducts()) {
            int quantity = shoppingCarts.get(cartID).getProductQuantity(p);
            s += String.format("|%28s|%28d|%22s $%.2f|%28d|\n", p.getName(), p.getId(), "", p.getPrice(), quantity);
            total += p.getPrice() * quantity;
        }
        s += String.format("|TOTAL: %100s $%.2f|\n", "", total);
        s += "\n|-----------------------------------------------------------------" +
                "--------------------------------------------------|\n";
        System.out.println(s);
        shoppingCarts.get(cartID).removeAllItems();
        return total;
    }

}

