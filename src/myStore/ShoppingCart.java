package myStore;

import myStore.Product;
import myStore.ProductStockContainer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * The myStore.ShoppingCart class models a shopping cart and extends the myStore.Inventory class.
 * Each instance keeps track of the type and quantity of each included myStore.Product.
 *
 * @author  Toyin Adams
 * @version 1.0
 */
public class ShoppingCart implements ProductStockContainer {
    private HashMap<Product, Integer> contents;

    /**
     * Constructs an empty myStore.ShoppingCart represented by an empty HashMap.
     */
    public ShoppingCart() {
        this.contents = new HashMap<Product, Integer>();
    }

    @Override
    public int getProductQuantity(Product product) {
        return contents.get(product);
    }

    @Override
    public void addProductQuantity(Product product, int quantity) {
        if (contents.containsKey(product)) {
            contents.replace(product, getProductQuantity(product) + quantity);
        }
        else {
            contents.put(product, quantity);
        }
    }

    @Override
    public void removeProductQuantity(Product product, int quantity) {
        if (product != null) {
            Integer stockDifference = getProductQuantity(product) - quantity;
            if (stockDifference > -1) {
                contents.replace(product, stockDifference);
            }
        }
    }

    @Override
    public Product getProduct(int productID) {
        Product product = null;                         // Returns null unless the myStore.Product exists in myStore.Inventory
        for (Product key : contents.keySet()) {
            if (key.getId() == productID) {
                product = key;
            }
        }
        return product;
    }

    @Override
    public List<Product> getProducts() {
        return new ArrayList<>(contents.keySet());
    }

    @Override
    public int getNumOfProducts() {
        return getProducts().size();
    }

    /**
     * Removes all Products in the myStore.ShoppingCart.
     */
    public void removeAllItems() {
        contents.clear();  // all mapping are cleared from map
    }
}
