package exceptionFiles;

public class JottException extends Exception {
    String message;
    String source;

    public JottException(String message) {
        super(message);
    }
    /**
     * Takes in information on error to throw an error message to the user
     *
     * @param s the file name of the file that the message was thrown from
     * @param m the message to be thrown with the error
     *
     */
    public JottException(String s, String m, int lineNum) {
        super("Syntax error at: " + s + ": line " + lineNum + ", Message: " + m);
        message = "Syntax error at: " + s + ": line " + lineNum + ", Message: " + m;
        source = s;
        String mes = message;
    }
    public String getSource() {
        return source;
    }

}
