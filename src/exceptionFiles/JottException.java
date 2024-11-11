package exceptionFiles;

public class JottException extends Exception {
    private String message;
    private String source;
    public String fileName;

    public static void printError(String s, String m, int lineNum) {
        //REMOVE THIS FOR SUBMISSION
        String errMessage = "Source Node File: " + s + "\n";
        //REMOVE THIS FOR SUBMISSION
        
        errMessage += "Semantic error at: " + s + ": line " + lineNum + ", Message: " + m;

        System.err.println(errMessage);
    }

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
    public JottException(boolean isSyntaxError, String s, String m, int lineNum) {
        super("Error at: " + s + ": line " + lineNum + ", Message: " + m);

        source = s;

        //REMOVE THIS FOR SUBMISSION
        message = "Source Node File: " + s + "\n";
        //REMOVE THIS FOR SUBMISSION

        if (isSyntaxError) {
            message += "Syntax";
        }
        else {
            message += "Semantic";
        }
        message += " error at: " + s + ": line " + lineNum + ", Message: " + m;
    }

    public String getSource() {
        return source;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
