package Kjarobot;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Kjaro {
    private static ArrayList<Task> tasks = new ArrayList<>();
    public static boolean isRunning = true;
    public static File saveFile = new File("data/KjaroSaveFile.txt");


    public static void main(String[] args) {
        
        System.out.println(KjaroFormat.LINE + "\n" + KjaroFormat.LOGO + KjaroFormat.LINE + "\n" + "Hello! I'm Kjaro\n"
                + "What can I do for you?\n" + KjaroFormat.LINE);
        loadSaveFile();
        Scanner reader = new Scanner(System.in);
        while (isRunning) {
            String message = reader.nextLine().trim();
            if (!message.isEmpty()) {
                parseInput(message);
            } else {
                printError("There's nothing!");
            }
        }
        reader.close();
    }

    // Prints a formatted message, supports multi-line messages
    public static void printMessage(String... messages) {
        System.out.println(KjaroFormat.INDENT + KjaroFormat.LINE);
        for (int i = 0; i < messages.length; i++) {
            System.out.println(KjaroFormat.INDENT + messages[i]);
        }
        System.out.println(KjaroFormat.INDENT + KjaroFormat.LINE);
    }

    // Prints a single-line formatted error message
    public static void printError(String message) {
        System.out.println(KjaroFormat.INDENT + KjaroFormat.ERR_LINE);
        System.out.println(KjaroFormat.INDENT + message);
        System.out.println(KjaroFormat.INDENT + KjaroFormat.ERR_LINE);
    }

    // Adds a new task into the tasks list
    public static void addToTasks(Task task) {
        tasks.add(task);
        printMessage("Task added.", 
            task.toString(), 
            "Now there are " + tasks.size() + " task(s) in your list!");
    }

    // Prints the list of tasks
    public static void printList() {
        System.out.println(KjaroFormat.INDENT + KjaroFormat.LINE);
        System.out.println(KjaroFormat.INDENT + "Here are your task(s)! You have " + tasks.size() + " task(s)!");
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println(KjaroFormat.INDENT + (i + 1) + ": " + tasks.get(i));
        }
        System.out.println(KjaroFormat.INDENT + KjaroFormat.LINE);
    }

    // Handles closing, when "bye" is entered
    public static void exit(String message) {
        printMessage(message);
        isRunning = false;
    }

    // Managing commands containing two or more arguments.
    public static void parseInput(String input) {
        String[] args = input.split(" ");
        switch (args[0]) {
            case ("bye"):
            exportData();
            if (input.equals("bye kjaro!")) {
                exit("Byebye! See you soon! :3");
                break;
            }
            exit("That's all? See you soon!");
            break;
        case ("list"):
            if (args.length != 1) {
                printError("I only have one list!");
                break;
            }
            printList();
            break;
        case ("mark"):
            if (args.length == 2) {
                markTask(args[1]);
            } else {
                printError("Use: mark <task number> to mark tasks!");
            }
            break;

        case ("unmark"):
            if (args.length == 2) {
                unmarkTask(args[1]);
            } else {
                printError("Use: unmark <task number> to unmark tasks!");
            }
            break;

        case ("delete"):
            if (args.length == 2) {
                deleteTask(args[1]);
            } else {
                printError("Use: delete <task number> to delete tasks!");
            }
            break;

        case ("todo"):
            addToDo(args);
            break; 
        
        case ("deadline"):
            addDeadline(args);
            break;

        case ("event"):
            addEvent(args);
            break;

        default:
            printError("Invalid command!");
            break;
        }
    }

    // Handles checks and execution of marking tasks
    public static void markTask(String taskNo) {
        try {
            int taskNumber = Integer.valueOf(taskNo);
            if (taskNumber > tasks.size() || taskNumber <= 0) {
                printError("That's not a markable task!");
                return;
            }
            tasks.get(taskNumber - 1).markAsDone();
            String doneMessage = "Alright! I've marked task " + taskNumber + " as complete!";
            printMessage(doneMessage, tasks.get(taskNumber - 1).toString());
        } 
        catch (NumberFormatException e) {
            printError("Input a number for marking!");
        }
    }
    
    // Handles checks and execution of unmarking tasks
    public static void unmarkTask(String taskNo) {
        try {
            int taskNumber = Integer.valueOf(taskNo);
            if (taskNumber > tasks.size() || taskNumber <= 0) {
                printError("That's not a markable task!");
                return;
            }
            tasks.get(taskNumber - 1).markAsUndone();
            String undoneMessage = "Task " + taskNumber + " has been marked undone, you'll get it next time.";
            printMessage(undoneMessage, tasks.get(taskNumber - 1).toString());
        } 
        catch (NumberFormatException e) {
        printError("Make sure to use a number for marking!");
        }
    }

    public static void deleteTask(String taskNo) {
        try {
            int taskNumber = Integer.valueOf(taskNo);
            if (taskNumber > tasks.size() || taskNumber <= 0) {
                printError("That's not a deletable task!");
                return;
            }
            String deleteMessage = "Alright! I've removed task " + taskNumber + "!";
            printMessage(deleteMessage,
                 tasks.get(taskNumber - 1).toString(),
                 "You now have " + (tasks.size() -  1) + " task(s)");
            tasks.remove(taskNumber - 1);
        } 
        catch (NumberFormatException e) {
            printError("Input a number for deleting!");
        }
    }    

    // Parses input for todos, adds it to tasks if it's valid
    public static void addToDo(String[] args) {
        String toDoName = "";
            for (int i = 1; i < args.length; i++) {
                if (args[i].charAt(0) == '/') {
                    printError("todo doesn't have these features!");
                    return;
                }
                toDoName += args[i] + " ";
            }  
        if (toDoName.isEmpty()) {
            printError("Missing task name!");
        } else {
            addToTasks(new ToDo(toDoName.trim()));
        }
    }

    // Parses input for deadlines, adds it to tasks if valid
    public static void addDeadline(String[] args) {
        String deadlineName = "";
        String deadlineDate = "";
        ArrayList<String> argsList  = new ArrayList<String>(Arrays.asList(args));
        int byIndex = argsList.indexOf("/by");
        argsList.removeIf(x -> x.charAt(0) != '/');
        if (argsList.size() > 1) {
            printError("That's too much info for a deadline!");
            return;
        }

        for (int i = 1; i < byIndex; i++) {
                deadlineName += args[i] + " ";
        }
        for (int i = byIndex + 1; i < args.length; i++) {
            deadlineDate += args[i] + " ";
        }
        
        if (args.length == 1) {
            printError("Missing arguments! Use: deadline <task name> /by <due date");
        } else if (byIndex == - 1) {
            printError("Deadline needs a due date, use /by <date> to set it!");
        } else if (deadlineName.isEmpty()) {
            printError("Missing task name!");
        } else if (deadlineDate.isEmpty()) { 
            printError("Missing due date! (/by)");
        } else {
            try {
                LocalDate ldDeadlineDate = LocalDate.parse(deadlineDate.trim());
                addToTasks(new Deadline(deadlineName.trim(), ldDeadlineDate));
            } catch (DateTimeParseException e) {
                printError("Unrecognised date format! try <yyyy-mm-dd>");
            }
        }
    }
    
    // Parses input for events, adding it to tasks if it's valid
    public static void addEvent(String[] args) {
        String eventName = "";
        String eventFrom = "";
        String eventTo = "";
        ArrayList<String> argsList  = new ArrayList<String>(Arrays.asList(args));
        int fromIndex = argsList.indexOf("/from");
        int toIndex = argsList.indexOf("/to");
        argsList.removeIf(x -> x.charAt(0) != '/');
        if (argsList.size() > 2) {
            printError("That's too much info for an event!");
            return;
        }
        for (int i = 1; i < fromIndex; i++) {
            eventName += args[i] + " ";
        }
        for (int i = fromIndex + 1; i < toIndex; i++) {
            eventFrom += args[i] + " ";
        }
        for (int i = toIndex + 1; i < args.length; i++) {
            eventTo += args[i] + " ";
        }
        if (args.length == 1 || fromIndex == - 1 || toIndex == - 1) {
            printError("Missing arguments! " 
                + "Use: event <task name> /from <start date> /to <end date>");
        } else if (eventName.isEmpty()) {
            printError("Event name is missing!");
        } else if (eventFrom.isEmpty()) {
            printError("Event start date is missing! (/from)");
        } else if (eventTo.isEmpty()) {
            printError("Event end date is missing! (/to)");
        } else {
            try {
                LocalDate ldEventFrom = LocalDate.parse(eventFrom.trim());
                LocalDate ldEventTo = LocalDate.parse(eventTo.trim());
                addToTasks(new Event(eventName.trim(), ldEventFrom, ldEventTo));
                
            } catch (DateTimeParseException e) {
                printError("Unrecognised date format! try <yyyy-mm-dd>");
            }
            
        }
    }

    private static void loadSaveFile() {
        try {
            if (!saveFile.createNewFile()) {
                Scanner saveFileScanner = new Scanner(saveFile);
                while (saveFileScanner.hasNext()) {
                    importData(saveFileScanner.nextLine().split("\\s\\|\\s"));
                }
                saveFileScanner.close();
            }
        } catch (IOException e) {
            
        }
    }

    private static void importData(String[] fileLine) {
        switch (fileLine[0].trim()) {
        case ("T"):
            ToDo toDo = new ToDo(fileLine[2].trim());
            if (fileLine[1].trim().equals("X")) {
                toDo.markAsDone();
            }
            tasks.add(toDo);
            break;
        case ("D"):
            Deadline deadline = new Deadline(fileLine[2].trim(), LocalDate.parse(fileLine[3].trim()));
            if (fileLine[1].trim().equals("X")) {
                deadline.markAsDone();
            }
            tasks.add(deadline);
            break;
        case ("E"):
            Event event = new Event(fileLine[2].trim(),
                     LocalDate.parse(fileLine[3].trim()), 
                     LocalDate.parse(fileLine[4].trim()));
            if (fileLine[1].trim().equals("X")) {
                event.markAsDone();
            }
            tasks.add(event);
            break;
        }
    }

    private static void exportData() {
        try {
            FileWriter fw = new FileWriter(saveFile);
            String saveData = "";
            for (Task t : tasks) {
                saveData += t.toSave() + System.lineSeparator();
            }
            fw.write(saveData);
            fw.close();
        }
        catch (IOException e) {

        } 
    }
}