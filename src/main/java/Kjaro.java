import java.util.Scanner;

public class Kjaro {
    private static String line = "________________________________\n";
    private static String errLine = "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n";
    private static String indent = "    ";
    private static Task[] listOfItems = new Task[100];
    private static int itemsSoFar = 0;
    public static boolean isRunning = true;
    public static Scanner reader = new Scanner(System.in);
    public static void main(String[] args) {
        String logo = " _  __ _                 \r\n" + //
                        "| |/ /(_) __ _ _ __ ___  \r\n" + //
                        "| ' / | |/ _` | '__/ _ \\ \r\n" + //
                        "| . \\ | | (_| | | | (_) |\r\n" + //
                        "|_|\\_\\/ |\\__,_|_|  \\___/ \r\n" + //
                        "    |__/                 \n";
       
        System.out.println(line + logo + line + "Hello! I'm Kjaro\n"
         + "What can I do for you?\n" + line);
        
        while (isRunning) {
            String message = reader.nextLine();
            String[] splitMessage = message.split(" ");
            if (splitMessage.length == 2 && TwoArgCommand(splitMessage)) continue;

            // Managing single word commands / non-commands
            switch (message) {
                case ("bye"):
                    Farewell();
                    break;
                case ("list"):
                    PrintList();
                    break;
                default:
                    PrintMessage("added: " + message);
                    AddToList(message);
            }  
        }
    }

    public static void PrintMessage(String... messages) {
        System.out.println(indent + line);
        for (int i = 0; i < messages.length; i++) {
            System.out.println(indent + messages[i]);
        }
        System.out.println(indent + line);
    }

    public static void PrintError(String message) {
        System.err.println(indent + errLine + indent + message + "\n" + indent + errLine);
    }

    public static void AddToList(String message) {
        listOfItems[itemsSoFar] = new Task(message);
        itemsSoFar++;
    }

    public static void PrintList() {
        System.out.println(indent + line);
        for (int i = 0; i < itemsSoFar; i++) {
            System.out.println(indent + (i + 1) + ": " + listOfItems[i]);
        }
        System.out.println(indent + line);
    }

    public static void Farewell() {
        PrintMessage("That's all? Goodbye!");
        reader.close();
        isRunning = false;
    }
    // Managing commands containing two arguments.
    public static boolean TwoArgCommand(String[] splitMessage) {
        switch (splitMessage[0]) {
                case ("mark"):
                    try {
                        int taskNumber = Integer.valueOf(splitMessage[1]);
                        if (taskNumber > itemsSoFar || taskNumber <= 0) {
                            PrintError("You don't have a task " + taskNumber);
                            return true;
                        }
                        listOfItems[taskNumber - 1].MarkAsDone();
                        String doneMessage = "Alright! I've marked task " + taskNumber + " as complete!" + "\n";
                        PrintMessage(doneMessage, listOfItems[taskNumber - 1].toString());
                    }
                    catch (NumberFormatException e) {
                        PrintError("Input a number for marking!");
                    }
                    return true;

                case ("unmark"):
                    try {
                        int taskNumber = Integer.valueOf(splitMessage[1]);
                        if (taskNumber > itemsSoFar || taskNumber <= 0) {
                            PrintError("You don't have a task " + taskNumber);
                            return true;
                        }
                        listOfItems[taskNumber - 1].MarkAsUndone();
                        String undoneMessage = "Task " + taskNumber + " has been marked undone, you'll get it next time." + "\n";
                        PrintMessage(undoneMessage, listOfItems[taskNumber - 1].toString());
                    }
                    catch (NumberFormatException e) {
                        PrintError("Input a number for marking!");
                    }
                    return true;
        }
        return false;
    }
}