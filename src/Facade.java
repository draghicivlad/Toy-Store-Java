import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class Facade {
    public void listcurrencies()
    {
        Store store = Store.getInstance();

        for(Currency currency : store.getCurrencies())
            System.out.println(currency.getName() + " " + currency.getParityToEur());
    }

    public void getstorecurrency()
    {
        Store store = Store.getInstance();

        System.out.println(store.getCurrency().getName());
    }

    public void addcurrency(String currencyName, String currencySymbol,
                                   String currencyParityToEur)
    {
        Store store = Store.getInstance();

        Currency currency = new Currency(currencyName, currencySymbol,
                Double.parseDouble(currencyParityToEur));
        store.getCurrencies().add(currency);
    }

    public void loadcsv(String filename) throws IOException {
        Store store = Store.getInstance();

        store.readCSV(filename);
    }

    public void savecsv(String filename)
    {
        Store store = Store.getInstance();

        store.saveCSV(filename);
    }

    public void setstorecurrency(String currencyName)
    {
        Store store = Store.getInstance();

        Optional<Currency> currencyOptional = store.getCurrencies().stream().
                filter(e -> e.getName().equals(currencyName)).findAny();

        Currency newCurrency = null;
        if(currencyOptional.isPresent())
            newCurrency = currencyOptional.get();

        store.changeCurrency(newCurrency);
    }

    public void updateparity(String currencyName, String currencyParityToEur)
    {
        Store store = Store.getInstance();

        Optional<Currency> currencyOptional = store.getCurrencies().stream()
                .filter(e -> e.getName().equals(currencyName)).findAny();

        Currency toUpdateCurrency = null;

        if(currencyOptional.isPresent())
            toUpdateCurrency = currencyOptional.get();

        if (toUpdateCurrency != null) {
            toUpdateCurrency.updateParity(Double.parseDouble(currencyParityToEur));
        }
    }

    public void listproducts()
    {
        Store store = Store.getInstance();

        for(Product product : store.getProducts())
            System.out.println(product);
    }

    public void showproduct(String productUniqueId)
    {
        Store store = Store.getInstance();

        Optional<Product> productOptional = store.getProducts().stream().filter(e
                -> e.getUniqueId().equals(productUniqueId)).findAny();

        Product product = null;

        if(productOptional.isPresent())
            product = productOptional.get();

        System.out.println(product);
    }

    public void listmanufacturers()
    {
        Store store = Store.getInstance();

        for(Manufacturer manufacturer : store.getManufacturers())
        {
            System.out.println(manufacturer);
        }
    }

    public void listproductsbymanufacturarer(String manufacturerName)
    {
        Store store = Store.getInstance();

        List<Product> products = store.getProducts().stream().filter(e ->
                e.getManufacturer().getName().equals(manufacturerName))
                .collect(Collectors.toList());

        for(Product product : products)
        {
            System.out.println(product);
        }
    }

    public void listdiscounts()
    {
        Store store = Store.getInstance();

        for(Discount discount : store.getDiscounts())
        {
            System.out.println(discount);
        }
    }

    public void addiscount(String discountType, String discountValue,
                                  String discountName)
    {
        Store store = Store.getInstance();

        DiscountType discountTypeNew = DiscountType.PERCENTAGE_DISCOUNT;

        if(discountType.equals("FIXED"))
            discountTypeNew = DiscountType.FIXED_DISCOUNT;

        Discount newDiscount = new Discount(discountName, discountTypeNew,
                Double.parseDouble(discountValue));

        store.getDiscounts().add(newDiscount);
    }

    public void applydiscount(String discountType, String discountValue) throws DiscountNotFoundException
    {
        Store store = Store.getInstance();

        DiscountType discountTypeNew = DiscountType.PERCENTAGE_DISCOUNT;

        if(discountType.equals("FIXED"))
            discountTypeNew = DiscountType.FIXED_DISCOUNT;

        DiscountType finalDiscountTypeNew = discountTypeNew;

        Optional<Discount> discountOptional = store.getDiscounts().stream()
                .filter(e -> e.getDiscountType().equals(finalDiscountTypeNew)
                && e.getValue() == Double.parseDouble(discountValue)).findAny();

        if(discountOptional.isEmpty())
            throw new DiscountNotFoundException();

        Discount discount = discountOptional.get();
        discount.setAsAppliedNow();
        store.applyDiscount(discount);
    }

    public void calculatetotal(ArrayList<String> productsUniqueId)
    {
        Store store = Store.getInstance();

        double total = 0;

        for(String productUniqueId : productsUniqueId)
        {
            Optional<Product> productOptional = store.getProducts().stream()
                    .filter(e -> e.getUniqueId().equals(productUniqueId)).findAny();

            Product product = null;

            if(productOptional.isPresent())
                product = productOptional.get();

            if (product != null) {
                total += product.getPrice();
            }
        }

        System.out.println(store.getCurrency().getSymbol() + total);
    }

    public void savestore()
    {
        Store store = Store.getInstance();

        FileOutputStream fileOutputStream;
        ObjectOutputStream objectOutputStream;
        try {
            fileOutputStream = new FileOutputStream("store.ser");
            objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(store);

            objectOutputStream.close();
            fileOutputStream.close();
        } catch (IOException e) {
            System.out.println(Arrays.toString(e.getStackTrace()));
        }
    }

    public void loadstore()
    {
        Store store = Store.getInstance();
        Store newStore;

        FileInputStream fileInputStream;
        ObjectInputStream objectInputStream;

        try {
            fileInputStream = new FileInputStream("store.ser");
            objectInputStream = new ObjectInputStream(fileInputStream);

            newStore = (Store) objectInputStream.readObject();
            objectInputStream.close();
            fileInputStream.close();

            store.setName(newStore.getName());
            store.setCurrency(newStore.getCurrency());
            store.setCurrencies(newStore.getCurrencies());
            store.setProducts(newStore.getProducts());
            store.setManufacturers(newStore.getManufacturers());
            store.setDiscounts(newStore.getDiscounts());
        } catch (ClassNotFoundException | IOException e) {
            System.out.println(Arrays.toString(e.getStackTrace()));
        }
    }
}
