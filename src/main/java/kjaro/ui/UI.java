package kjaro.ui;


/**
 * Represents the formatting of Kjaro's messages.
 */
public class UI {

    /**
     * Formats multiple messages into one single message.
     * @param messages One or more messages to be printed.
     * @return The formatted message.
     */
    public String formatMessage(String... messages) {
        String finalMessage = "";
        finalMessage += Format.LINE + "\n";
        for (int i = 0; i < messages.length; i++) {
            finalMessage += messages[i] + "\n";
        }
        finalMessage += Format.LINE + "\n";
        return finalMessage;
    }

    /**
     * Formats an error message.
     * @param message A single-line error message.
     * @return the formatted error message.
     */
    public String formatErrorMessage(String message) {
        String finalMessage = "";
        finalMessage += Format.ERR_LINE + "\n";
        finalMessage += message + "\n";
        finalMessage += Format.ERR_LINE + "\n";
        return finalMessage;
    }

    /**
     * Formats Kjaro's default welcome message.
     * @return The formatted welcome message.
     */
    public String formatWelcome() {
        String finalMessage = "";
        finalMessage += Format.LINE + "\n";
        finalMessage += Messages.WELCOME_MESSAGE + "\n";
        finalMessage += Format.LINE + "\n";
        return finalMessage;
    }
}
