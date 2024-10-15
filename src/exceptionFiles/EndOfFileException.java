package exceptionFiles;

public class EndOfFileException extends Exception {
    public EndOfFileException(String expectedVal) {
        super("Syntax error: Expected " + expectedVal + ", instead got End of File");
    }
}
