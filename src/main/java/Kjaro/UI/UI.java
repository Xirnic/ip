package kjaro.ui;

/**
 * Provides methods for printing messages, following a predetermined indented
 * format
 */
public class UI {

    /**
     * Print an ordinary message, accepts multiple arguments
     *
     * @param messages messages to be printed
     */
    public void printMessage(String... messages) {
        System.out.println(Format.INDENT + Format.LINE);
        for (int i = 0; i < messages.length; i++) {
            System.out.println(Format.INDENT + messages[i]);
        }
        System.out.println(Format.INDENT + Format.LINE);
    }

    /**
     * Prints a single line error message
     *
     * @param message error message
     */
    public void printError(String message) {
        System.out.println(Format.INDENT + Format.ERR_LINE);
        System.out.println(Format.INDENT + message);
        System.out.println(Format.INDENT + Format.ERR_LINE);
    }

    /**
     * Prints Kjaro's welcome message
     */
    public void printWelcome() {
        System.out.println(Format.LINE);
        System.out.println(Messages.WELCOME_MESSAGE);
        System.out.println(Format.LINE);
    }
}
