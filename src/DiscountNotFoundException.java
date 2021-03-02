public class DiscountNotFoundException extends RuntimeException{
    public DiscountNotFoundException() {
    }

    public DiscountNotFoundException(String message) {
        super(message);
    }
}
