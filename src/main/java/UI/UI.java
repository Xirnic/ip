package UI;



public class UI {

    public void printMessage(String... messages) {
        System.out.println(Format.INDENT + Format.LINE);
        for (int i = 0; i < messages.length; i++) {
            System.out.println(Format.INDENT + messages[i]);
        }
        System.out.println(Format.INDENT + Format.LINE);
    }

    public void printError(String message) {
        System.out.println(Format.INDENT + Format.ERR_LINE);
        System.out.println(Format.INDENT + message);
        System.out.println(Format.INDENT + Format.ERR_LINE);
    }

    public void printWelcome() {
        System.out.println(Format.LINE);
        System.out.println(Messages.WELCOME_MESSAGE);
        System.out.println(Format.LINE);
    }
}
