import java.util.*;

public class Main {
    public static void main(String[] args) {
        Facade facade = new Facade();
        Scanner scanner = new Scanner(System.in);
        String[] currentArgs;

        while(true)
        {
            try
            {
                currentArgs = scanner.nextLine().split(" ");
                switch(currentArgs[0])
                {
                    case "listcurrencies":
                        facade.listcurrencies();
                        break;

                    case "getstorecurrency":
                        facade.getstorecurrency();
                        break;

                    case "addcurrency":
                        String currencyName = currentArgs[1];
                        String currencySymbol = currentArgs[2];
                        String currencyParityToEur = currentArgs[3];
                        facade.addcurrency(currencyName, currencySymbol, currencyParityToEur);
                        break;

                    case "loadcsv":
                        facade.loadcsv(currentArgs[1]);
                        break;

                    case "savecsv":
                        facade.savecsv(currentArgs[1]);
                        break;

                    case "setstorecurrency":
                        currencyName = currentArgs[1];
                        facade.setstorecurrency(currencyName);
                        break;

                    case "updateparity":
                        currencyName = currentArgs[1];
                        currencyParityToEur = currentArgs[2];
                        facade.updateparity(currencyName, currencyParityToEur);
                        break;

                    case "listproducts":
                        facade.listproducts();
                        break;

                    case "showproduct":
                        String productUniqueId = currentArgs[1];
                        facade.showproduct(productUniqueId);
                        break;

                    case "listmanufacturers":
                        facade.listmanufacturers();
                        break;

                    case "listproductsbymanufacturarer":
                        String manufacturerName = currentArgs[1];
                        facade.listproductsbymanufacturarer(manufacturerName);
                        break;

                    case "listdiscounts":
                        facade.listdiscounts();
                        break;

                    case "addiscount":
                        String discountType = currentArgs[1];
                        String discountValue = currentArgs[2];

                        StringBuilder builder = new StringBuilder();
                        for(int i = 3; i < currentArgs.length; i++) {
                            builder.append(currentArgs[i]);
                            builder.append(" ");
                        }
                        builder.deleteCharAt(builder.length() - 1);

                        String discountName = builder.toString();
                        facade.addiscount(discountType, discountValue, discountName);
                        break;

                    case "applydiscount":
                        discountType = currentArgs[1];
                        discountValue = currentArgs[2];
                        facade.applydiscount(discountType, discountValue);
                        break;

                    case "calculatetotal":
                        ArrayList<String> productsUniqueId = new ArrayList<>(Arrays.
                                asList(currentArgs).subList(2, currentArgs.length));
                        facade.calculatetotal(productsUniqueId);
                        break;

                    case "savestore":
                        facade.savestore();
                        break;

                    case  "loadstore":
                        facade.loadstore();
                        break;

                    case "exit":
                    case "quit":
                        return;
                }
            }
            catch (Exception e)
            {
                System.out.println(Arrays.toString(e.getStackTrace()));
            }
        }
    }
}