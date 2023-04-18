package myStore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * The myStore.myStore.Inventory class represents an inventory.
 * Each instance keeps track of the type and quantity of each included myStore.Product.
 * 
 * @author Oluwatoyin Adams
 * @version 1.0
 */
public class Inventory implements ProductStockContainer {
    private HashMap<Product, Integer> productStock;

    /**
     * Constructs an empty myStore.Inventory represented by an empty HashMap.
     */
    public Inventory() {
        this.productStock = new HashMap<Product, Integer>();
    }

    @Override
    public int getProductQuantity(Product product) {
        return productStock.get(product);
    }

    @Override
    public void addProductQuantity(Product product, int quantity) {
        if (productStock.containsKey(product)) {
            productStock.replace(product, getProductQuantity(product) + quantity);
        }
        else {
            productStock.put(product, quantity);
        }
    }

    @Override
    public void removeProductQuantity(Product product, int quantity) {
        if (product != null) {
            Integer stockDifference = getProductQuantity(product) - quantity;
            if (stockDifference > -1) {
                productStock.replace(product, stockDifference);
            }
        }
    }
    @Override
    public Product getProduct(int productID) {
        Product product = null;                         // Returns null unless the myStore.Product exists in myStore.Inventory
        for (Product key : productStock.keySet()) {
            if (key.getId() == productID) {
                product = key;
            }
        }
        return product;
    }

    @Override
    public List<Product> getProducts() {
        return new ArrayList<>(productStock.keySet());
    }

    @Override
    public int getNumOfProducts() {
        return getProducts().size();
    }
}
