package errorHandling;

public class EndOfFileException extends Exception {
    public EndOfFileException(String expectedVal) {
        super("Expected " + expectedVal + ", instead got End of File");
    }
}
