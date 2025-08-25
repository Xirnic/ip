import java.util.Scanner;

public class Kjaro {
    private static String line = "___________________________\n";
    private static String indent = "    ";
    public static void main(String[] args) {
        String logo = " _  __ _                 \r\n" + //
                        "| |/ /(_) __ _ _ __ ___  \r\n" + //
                        "| ' / | |/ _` | '__/ _ \\ \r\n" + //
                        "| . \\ | | (_| | | | (_) |\r\n" + //
                        "|_|\\_\\/ |\\__,_|_|  \\___/ \r\n" + //
                        "    |__/                 \n";
       
        System.out.println(line + logo + line + "Hello! I'm Kjaro\n"
         + "What can I do for you?\n" + line);
        Scanner reader = new Scanner(System.in);
        while (true) {
            String message = reader.nextLine();
            if (message.equals("bye")) {
                reader.close();
                break;
            }
            Echo(message);
        }
        
        System.out.println(indent + line + indent + "That's all? Goodbye!");
    }

    public static void Echo(String message) {
        System.out.println(indent + line + indent + message + "\n" + indent + line);
    }
}