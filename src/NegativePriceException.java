public class NegativePriceException extends RuntimeException{
    public NegativePriceException() {
    }

    public NegativePriceException(String message) {
        super(message);
    }
}
