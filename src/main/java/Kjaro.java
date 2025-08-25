import java.util.Scanner;

public class Kjaro {
    private static String line = "___________________________\n";
    private static String indent = "    ";
    private static String[] listOfItems = new String[100];
    private static int itemsSoFar = 0;
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
            if (message.equals("list")) {
                PrintList();
                continue;
            }
            Echo("added: " + message);
            AddToList(message);
        }
        
        System.out.println(indent + line + indent + "That's all? Goodbye!");
    }

    public static void Echo(String message) {
        System.out.println(indent + line + indent + message + "\n" + indent + line);
    }

    public static void AddToList(String message) {
        listOfItems[itemsSoFar] = message;
        itemsSoFar++;
    }

    public static void PrintList() {
        System.out.println(indent + line);
        for (int i = 0; i < itemsSoFar; i++) {
            System.out.println(indent + (i + 1) + ": " + listOfItems[i]);
        }
        System.out.println(indent + line);
    }
}