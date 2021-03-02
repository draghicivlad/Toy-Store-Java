import java.io.Serializable;

public class Manufacturer implements Serializable {
    private String name;
    private int countProducts;

    public Manufacturer() { }

    public Manufacturer(String name, int countProducts) {
        this.name = name;
        this.countProducts = countProducts;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCountProducts() {
        return countProducts;
    }

    public void setCountProducts(int countProducts) {
        this.countProducts = countProducts;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Manufacturer)) return false;
        Manufacturer that = (Manufacturer) o;
        return getCountProducts() == that.getCountProducts() && getName().equals(that.getName());
    }

    @Override
    public String toString() {
        return name;
    }
}
