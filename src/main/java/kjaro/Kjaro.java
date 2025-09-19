package kjaro;

import java.util.Scanner;

import kjaro.parser.Parser;
import kjaro.storage.Storage;
import kjaro.task.TaskList;
import kjaro.ui.Messages;
import kjaro.ui.UI;

public class Kjaro {

    private UI ui;
    private TaskList taskList;
    private Parser parser;
    private Storage storage;

    /**
     * Kjaro, a chatbot that manages a task list, supporting addition, deletion,
     * saving, as well as 3 types of tasks that can be marked as done and
     * undone.
     */
    public Kjaro() {
        ui = new UI();
        storage = new Storage(ui);
        taskList = storage.loadSaveFile();
        parser = new Parser(taskList, ui, storage);
    }

    public static void main(String[] args) {
        Kjaro kjaro = new Kjaro();
        kjaro.run();
    }

    private void run() {
        ui.printWelcome();
        Scanner reader = new Scanner(System.in);
        boolean isRunning = true;
        while (isRunning) {
            String message = reader.nextLine().trim();
            isRunning = parser.parseInput(message) != ui.printMessage(Messages.GOODBYE_MESSAGE);
        }
        reader.close();
    }

    /**
     * Generates a response for the user's chat message.
     */
    public String getResponse(String input) {
        return parser.parseInput(input);
    }

    public String getWelcome() {
        return ui.printWelcome();
    }
}