package myStore;

/**
 * The myStore.myStore.Product class represents a product.
 * Each instance has an immutable name, id, and price.
 * 
 * @author Oluwatoyin Adams
 * @version 1.0
 */
public class Product {
    private String name;
    private int id;
    private double price;

    /**
     * Constructs a myStore.Product object with the specified name, id, and price.
     *
     * @param name the name of the product
     * @param id the product id number
     * @param price the price of the product
     */
    public Product(String name, int id, double price) {
        this.name = name;
        this.id = id;
        this.price = price;
    }

    /**
     * Returns the name of the product.
     *
     * @return the name of the product
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the id of the product.
     *
     * @return the id of the product
     */
    public int getId() {
        return id;
    }

    /**
     * Returns the price of the object.
     *
     * @return the price of the object
     */
    public double getPrice() {
        return price;
    }
}
