package model;

public class Product {
    private String name;
    private double price;
    private int quantity;
    private String category;
    private int NumberSales;

    public Product(String name, double price, int quantity, String category, int numbersales) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.category = category;
        this.NumberSales = numbersales;
    }

    public Product(String productName, double price, int quantity, String productCategory) {
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getCategory() {
        return category;
    }

    public int getNumberSales() {
        return NumberSales;
    }
}