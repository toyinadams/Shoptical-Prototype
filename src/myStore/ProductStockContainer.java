package myStore;

import myStore.Product;

import java.util.List;

/**
 * The myStore.ProductStockContainer interface outlines the behaviours of
 * a myStore.ProductStockContainer.
 * A myStore.ProductStockContainer must implement methods to get the quantity of a myStore.Product in the myStore.ProductStockContainer,
 * add to the quantity of a myStore.Product, remove from the quantity of a myStore.Product, and additional behaviours outlined
 * in the documentation of the respective methods.
 *
 * @author  Toyin Adams
 * @version 1.0
 */
public interface ProductStockContainer {

    /**
     * Implementations should return the quantity of the specified myStore.Product in the myStore.ProductStockContainer.
     *
     * @param product   myStore.Product, the product to get the stock of
     * @return          the stock of the specified myStore.Product
     */
    int getProductQuantity(Product product);

    /**
     * Implementations should add the specified quantity of the specified product to
     * the specified myStore.ProductStockContainer.
     *
     * @param product   myStore.Product, specified product to add
     * @param quantity  int, quantity of product to be added
     */
    void addProductQuantity(Product product, int quantity);

    /**
     * Implementations should remove the specified quantity of the specified product from
     * the specified myStore.ProductStockContainer.
     *
     * @param product   myStore.Product, specified product to remove
     * @param quantity  int, quantity of product to be removed
     */
    void removeProductQuantity(Product product, int quantity);

    /**
     * Implementations should return the myStore.Product corresponding to the specified product id in myStore.Inventory.
     * If the product does not exist in myStore.Inventory, it should return null.
     *
     * @param productID the product id of the product
     * @return the product corresponding to the specified product id if it exists in myStore.Inventory, otherwise null
     */
    Product getProduct(int productID);

    /**
     * Implementations should return the Products in the myStore.ProductStockContainer as a List.
     *
     * @return List<myStore.Product>, an iterable list of the Products in the myStore.ProductStockContainer
     */
    List<Product> getProducts();

    /**
     * Implementations should return the number of Products in the myStore.ProductStockContainer.
     *
     * @return int, the number of Products in the myStore.ProductStockContainer
     */
    int getNumOfProducts();
}
