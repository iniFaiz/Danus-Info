package model;

import java.util.ArrayList;
import java.util.List;

public class Danus {
    private int id;
    private String namaDanus;
    private String lokasi;
    private List<Product> products; // List of products

    public Danus(String namaDanus, String lokasi) {
        this.namaDanus = namaDanus;
        this.lokasi = lokasi;
        this.products = new ArrayList<>(); // Initialize the products list
    }

    public String getNamaDanus() {
        return namaDanus;
    }

    public String getLokasi() {
        return lokasi;
    }

    public int getId() {
        return this.id;
    }

    public void addProduct(Product product) {
        this.products.add(product);
    }

    // Add this method to get the list of products
    public List<Product> getProducts() {
        return this.products;
    }
}