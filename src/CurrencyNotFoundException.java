public class CurrencyNotFoundException extends RuntimeException{
    public CurrencyNotFoundException() {
    }

    public CurrencyNotFoundException(String message) {
        super(message);
    }
}
