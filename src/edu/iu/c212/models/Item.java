package edu.iu.c212.models;

public class Item {

    /**
     * initialize instance variables
     */
    private String name;
    private double price;
    private int quantity;
    private int aisleNum;

    /**
     * this is the constructor that builds items
     * @param name String
     * @param price double
     * @param quantity int
     * @param aisleNum int
     */
    public Item(String name, double price, int quantity, int aisleNum) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.aisleNum = aisleNum;
    }

    /**
     * gets name
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * gets price
     * @return price
     */
    public double getPrice() {
        return price;
    }

    /**
     * gets quantity
     * @return quantity
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * gets aisleNUm
     * @return aisleNum
     */
    public int getAisleNum() {
        return aisleNum;
    }

    /**
     * sets a new name
     * @param name String
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * sets a new price
     * @param price double
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * sets a new quantity
     * @param quantity int
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /**
     * sets a new aisleNum
     * @param aisleNum int
     */
    public void setAisleNum(int aisleNum) {
        this.aisleNum = aisleNum;
    }
}
