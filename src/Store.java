import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

public class Store implements Serializable {
    private static Store instance;

    private String name;
    private Currency currency;
    private ArrayList<Currency> currencies;
    private ArrayList<Product> products;
    private ArrayList<Manufacturer> manufacturers;
    private ArrayList<Discount> discounts;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public ArrayList<Product> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<Product> products) {
        this.products = products;
    }

    public ArrayList<Manufacturer> getManufacturers() {
        return manufacturers;
    }

    public void setManufacturers(ArrayList<Manufacturer> manufacturers) {
        this.manufacturers = manufacturers;
    }

    public ArrayList<Currency> getCurrencies() {
        return currencies;
    }

    public void setCurrencies(ArrayList<Currency> currencies) {
        this.currencies = currencies;
    }

    public ArrayList<Discount> getDiscounts() {
        return discounts;
    }

    public void setDiscounts(ArrayList<Discount> discounts) {
        this.discounts = discounts;
    }

    private Store() {
        products = new ArrayList<>();
        manufacturers = new ArrayList<>();
        currencies = new ArrayList<>();
        discounts = new ArrayList<>();

        currency = new Currency("EUR", "EUR", 1);
        currencies.add(currency);
    }

    public static Store getInstance() {
        if (instance == null)
            instance = new Store();
        return instance;
    }

    public void readCSV(String filename) throws CurrencyNotFoundException {
        Store store = Store.getInstance();

        try {
            Iterable<CSVRecord> records = CSVFormat.DEFAULT
                    .withFirstRecordAsHeader()
                    .parse(new FileReader(filename));

            for (CSVRecord record : records) {

                String productUniqueId = record.get(0);
                String productName = record.get(1);
                String manufacturerName = record.get(2);
                String productPrice = record.get(3);
                String productQuantity = record.get(4);

                if (productPrice.equals(""))
                    continue;

                Currency productCurrency = null;

                for (Currency currency : store.getCurrencies()) {
                    if (productPrice.contains(currency.getSymbol()))
                        productCurrency = currency;
                }

                if (productCurrency == null)
                    throw new CurrencyNotFoundException();

                if (manufacturerName.equals(""))
                    manufacturerName = "Not Available";

                Manufacturer currentManufacturer;
                String finalManufacturerName = manufacturerName;
                Optional<Manufacturer> manufacturerOptional = store.getManufacturers()
                        .stream().filter(e -> e.getName().equals(finalManufacturerName)).findAny();

                if (manufacturerOptional.isEmpty()) {
                    currentManufacturer = new Manufacturer(manufacturerName, 1);
                    store.addManufacturer(currentManufacturer);
                } else {
                    currentManufacturer = manufacturerOptional.get();
                }

                double doubleProductPrice = Double.parseDouble(productPrice
                        .split(" ")[0].replaceAll("[^0-9.]", "")
                        .replaceAll(",", ""));

                doubleProductPrice *= productCurrency.getParityToEur() / store
                        .getCurrency().getParityToEur();

                String formattedProductQuantity;
                if (!productQuantity.equals(""))
                    formattedProductQuantity = productQuantity.split(Character
                            .toString((char) 160))[0];
                else
                    formattedProductQuantity = "0";

                Product newProduct = new ProductBuilder()
                        .withUniqueId(productUniqueId)
                        .withName(productName)
                        .withManufacturer(currentManufacturer)
                        .withPrice(doubleProductPrice)
                        .withQuantity(Integer.parseInt(formattedProductQuantity))
                        .build();

                store.addProduct(newProduct);
            }
        } catch (Exception e) {
            System.out.println(Arrays.toString(e.getStackTrace()));
        }
    }

    public void saveCSV(String filename) {
        Store store = Store.getInstance();

        BufferedWriter writer;
        try {
            writer = new BufferedWriter(new FileWriter(filename));
            CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.EXCEL
                    .withHeader("uniq_id", "product_name", "manufacturer", "price",
                            "number_available_in_stock"));

            for (Product product : store.getProducts()) {
                String[] fields = {
                        product.getUniqueId(),
                        product.getName(),
                        product.getManufacturer().getName(),
                        store.getCurrency().getSymbol() + product.getPrice(),
                        product.getQuantity() + " NEW"
                };

                csvPrinter.printRecord(fields);
            }
            csvPrinter.flush();
        } catch (IOException e) {
            System.out.println(Arrays.toString(e.getStackTrace()));
        }
    }

    void addProduct(Product product) throws DuplicateProductException {
        Store store = Store.getInstance();

        for (Product auxProduct : store.getProducts())
            if (product.equals(auxProduct))
                throw new DuplicateProductException();

        store.getProducts().add(product);
    }

    void addManufacturer(Manufacturer manufacturer) throws DuplicateManufacturerException {
        Store store = Store.getInstance();

        for (Manufacturer auxManufacturer : store.getManufacturers())
            if (manufacturer.equals(auxManufacturer))
                throw new DuplicateManufacturerException();

        store.getManufacturers().add(manufacturer);
    }

    void changeCurrency(Currency currency) throws CurrencyNotFoundException {
        if (currency == null)
            throw new CurrencyNotFoundException();

        Store store = Store.getInstance();

        for (Product product : store.getProducts())
            product.setPrice(product.getPrice() * store.getCurrency().getParityToEur() /
                    currency.getParityToEur());

        store.setCurrency(currency);
    }

    void applyDiscount(Discount discount) throws DiscountNotFoundException, NegativePriceException {
        if (discount == null)
            throw new DiscountNotFoundException();

        Store store = Store.getInstance();

        for (Product product : store.getProducts()) {
            if (discount.getDiscountType() == DiscountType.FIXED_DISCOUNT) {
                if (product.getPrice() < discount.getValue())
                    throw new NegativePriceException();

                product.setPrice(product.getPrice() - discount.getValue());
            } else {
                product.setPrice(product.getPrice() * (1 - discount.getValue() / 100D));
            }
        }
        discount.setAsAppliedNow();
    }
}