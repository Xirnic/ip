package Kjaro;

import java.util.Scanner;

import Kjaro.Parser.Parser;
import Kjaro.Storage.Storage;
import Kjaro.Task.TaskList;
import Kjaro.UI.UI;

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
            isRunning = parser.parseInput(message);
        }
        reader.close();
    }

}