package errorHandling;

public class JottException extends Exception {
    String message;
    String source;

    public JottException(String message) {
        super(message);
    }
    /**
     * Takes in information on error to throw an error message to the user
     *
     * @param s the object file that the message was thrown from
     * @param m the message to be thrown with the error
     *
     */
    public JottException(String s, String m) {
        super("Error at: " + s + " Message: " + m);
        message = "Error at: " + s + " Message: " + m;
        source = s;
        String mes = message;
    }
    public String getSource() {
        return source;
    }

}
