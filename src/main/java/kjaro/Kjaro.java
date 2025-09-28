package kjaro;

import java.util.Scanner;

import kjaro.parser.Parser;
import kjaro.storage.Storage;
import kjaro.task.TaskList;
import kjaro.ui.Messages;
import kjaro.ui.UI;

/**
 * Represents a chatbot that can manage 3 types of tasks, with varying features involving due
 * dates, marking as done, and snoozing.
 */
public class Kjaro {

    private UI ui;
    private TaskList taskList;
    private Parser parser;
    private Storage storage;

    /**
     * Constructs a new Kjaro instance, and automatically loads data from the save file.
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
        ui.formatWelcome();
        Scanner reader = new Scanner(System.in);
        boolean isRunning = true;
        while (isRunning) {
            String message = reader.nextLine().trim();
            isRunning = parser.parseInput(message) != ui.formatMessage(Messages.GOODBYE_MESSAGE);
        }
        reader.close();
    }

    /**
     * Generates a response for the user's chat message.
     */
    public String getResponse(String input) {
        return parser.parseInput(input) + "\n";
    }

    public String getWelcome() {
        return ui.formatWelcome() + "\n";
    }
}