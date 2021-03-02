import java.io.Serializable;

public class Product implements Serializable {
    private String uniqueId;
    private String name;
    private Manufacturer manufacturer;
    private double price;
    private int quantity;
    private Discount discount;

    public Product() { }

    public String getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Manufacturer getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(Manufacturer manufacturer) {
        this.manufacturer = manufacturer;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Discount getDiscount() {
        return discount;
    }

    public void setDiscount(Discount discount) {
        this.discount = discount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Product))
            return false;

        Product product = (Product) o;
        return Double.compare(product.getPrice(), getPrice()) == 0 && getQuantity()
                == product.getQuantity() && getUniqueId().equals(product.getUniqueId())
                && getName().equals(product.getName()) && getManufacturer()
                .equals(product.getManufacturer()) && getDiscount().equals(product.getDiscount());
    }

    public String toString()
    {
        Store store = Store.getInstance();

        return this.uniqueId + "," + this.name + "," + this.manufacturer + "," +
                store.getCurrency().getSymbol() + String.format("%.2f", this.price)
                + "," + this.quantity;
    }
}