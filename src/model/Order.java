// Order.java
package model;

import javafx.beans.property.*;

import java.time.LocalDate;

public class Order {
    private StringProperty productName;
    private DoubleProperty pricePaid;
    private ObjectProperty<LocalDate> orderDate;
    private StringProperty orderStatus;
    private StringProperty namaDanus;

    public Order(String productName, double pricePaid, LocalDate orderDate, String orderStatus, String namaDanus) {
        this.productName = new SimpleStringProperty(productName);
        this.pricePaid = new SimpleDoubleProperty(pricePaid);
        this.orderDate = new SimpleObjectProperty<>(orderDate);
        this.orderStatus = new SimpleStringProperty(orderStatus);
        this.namaDanus = new SimpleStringProperty(namaDanus);
    }

    // Getters and setters
    // ...

    public StringProperty productNameProperty() {
        return productName;
    }

    public DoubleProperty pricePaidProperty() {
        return pricePaid;
    }

    public ObjectProperty<LocalDate> orderDateProperty() {
        return orderDate;
    }

    public StringProperty orderStatusProperty() {
        return orderStatus;
    }

    public StringProperty namaDanusProperty() {
        return namaDanus;
    }
}