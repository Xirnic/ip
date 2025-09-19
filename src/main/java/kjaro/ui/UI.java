package kjaro.ui;



public class UI {

    public String printMessage(String... messages) {
        String finalMessage = "";
        finalMessage += Format.LINE + "\n";
        for (int i = 0; i < messages.length; i++) {
            finalMessage += messages[i] + "\n";
        }
        finalMessage += Format.LINE + "\n";
        return finalMessage;
    }

    public String printError(String message) {
        String finalMessage = "";
        finalMessage += Format.ERR_LINE + "\n";
        finalMessage += message + "\n";
        finalMessage += Format.ERR_LINE + "\n";
        return finalMessage;
    }

    public String printWelcome() {
        String finalMessage = "";
        finalMessage += Format.LINE + "\n";
        finalMessage += Messages.WELCOME_MESSAGE + "\n";
        finalMessage += Format.LINE + "\n";
        return finalMessage;
    }
}
