import java.io.Serializable;
import java.time.LocalDateTime;

public class Discount implements Serializable {
    private String name;
    private DiscountType discountType;
    private double value;
    private LocalDateTime lastDateApplied = null;

    public Discount() { }

    public Discount(String name, DiscountType discountType, double value) {
        this.name = name;
        this.discountType = discountType;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public DiscountType getDiscountType() {
        return discountType;
    }

    public void setDiscountType(DiscountType discountType) {
        this.discountType = discountType;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public LocalDateTime getLastDateApplied() {
        return lastDateApplied;
    }

    public void setLastDateApplied(LocalDateTime lastDateApplied) {
        this.lastDateApplied = lastDateApplied;
    }

    void setAsAppliedNow()
    {
        lastDateApplied = LocalDateTime.now();
    }

    public String toString()
    {
        return this.discountType + " " + this.value + " " + this.name + " " +
                this.lastDateApplied;
    }
}
