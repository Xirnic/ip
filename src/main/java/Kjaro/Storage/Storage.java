package Kjaro.Storage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import Kjaro.Task.Deadline;
import Kjaro.Task.Event;
import Kjaro.Task.Task;
import Kjaro.Task.TaskList;
import Kjaro.Task.ToDo;
import Kjaro.UI.Messages;
import Kjaro.UI.UI;

/**
 * The storage class, managing writing and reading from a predetermined save
 * file format.
 */
public class Storage {

    public static final String SUCCESSFUL_STRING = "Loaded successfully";
    public static final String SAVE_PATHNAME = "data/SaveFile.txt";
    protected final UI ui;
    protected final File saveFile;
    protected ArrayList<String> errors = new ArrayList<>();
    protected boolean isFullyLoaded;

    /**
     * The constructor for Storage.
     * 
     * @param ui for error printing.
     */
    public Storage(UI ui) {
        this.ui = ui;
        this.saveFile = new File(SAVE_PATHNAME);
    }

    /**
     * Reads the associated save file, and creates a new file if one isn't found
     * 
     * @return TaskList with loaded tasks.
     */
    public TaskList loadSaveFile() {
        TaskList taskList = new TaskList();
        try {
            if (!saveFile.createNewFile()) {
                Scanner saveFileScanner = new Scanner(saveFile);
                while (saveFileScanner.hasNext()) {
                    String saveResult = readSaveData(saveFileScanner.nextLine(), taskList);
                    if (saveResult != SUCCESSFUL_STRING) {
                        errors.add(saveResult);
                    }
                }
                saveFileScanner.close();
            }
        } catch (IOException e) {
            ui.printError(Messages.FILE_ERROR);
        }
        return taskList;
    }

    /**
     * Reads a single line of the save file, adding the task to the list if
     * successful.
     * 
     * @param line the line of the save file to be read.
     * @param taskList the TaskList for the task to be added to.
     * @return returns a successful string if successful, the erroneous line if
     * unsuccessful.
     */
    private String readSaveData(String line, TaskList taskList) {
        final Pattern savePattern = Pattern.compile("(?<taskType>[TDE])" + "\\/" + "(?<isDone>[XO])" + "\\/"
                                        + "(?<taskName>[^/]+)" + "(?:\\/)?" + "(?<firstDate>[^/]+)?" + "(?:\\/)?"
                                        + "(?<secondDate>[^/]+)?");
        final Matcher matcher = savePattern.matcher(line);
        if (!matcher.matches()) {
            return logSaveError(line);
        }
        String taskType = matcher.group("taskType");
        boolean isDone = matcher.group("isDone").equals("X");
        String taskName = matcher.group("taskName");
        String firstDate = matcher.group("firstDate");
        String secondDate = matcher.group("secondDate");
        Task addedTask;
        try {
            switch (taskType) {
            case ("T"):
                addedTask = new ToDo(taskName);
                break;
            case ("D"):
                if (firstDate != null) {
                    LocalDate deadlineBy = LocalDate.parse(firstDate);
                    addedTask = new Deadline(taskName, deadlineBy);
                    break;
                } else {
                    return logSaveError(line);
                }
            case ("E"):
                if (firstDate != null && secondDate != null) {
                    LocalDate eventFrom = LocalDate.parse(firstDate);
                    LocalDate eventTo = LocalDate.parse(secondDate);
                    addedTask = new Event(taskName, eventFrom, eventTo);
                    break;
                } else {
                    return logSaveError(line);
                }
            default:
                return logSaveError(line);
            }
            if (isDone) {
                addedTask.markAsDone();
            }
            taskList.addToTasks(addedTask);
            return SUCCESSFUL_STRING;
        } catch (DateTimeParseException e) {
            return logSaveError(line);
        }
    }

    /**
     * Currently returns the erroneous line without formatting.
     * 
     * @param line the erroneous line in the save file.
     * @return the erroneous line in the save file.
     */
    private String logSaveError(String line) {
        /*
         * isFullyLoaded = false; final Pattern savePatternLenient =
         * Pattern.compile("(?<taskType>.)?" + "(?:\\/)?" + "(?<isDone>.)?" +
         * "(?:\\/)?" + "(?<taskName>[^/]+)?" + "(?:\\/)?" +
         * "(?<firstDate>[^/]+)?" + "(?:\\/)?" + "(?<secondDate>[^/]+)?"); final
         * Matcher matcher = savePatternLenient.matcher(line);
         * ui.printError(line);
         */
        return line;
    }

    public ArrayList<String> getErrors() {
        return errors;
    }

    /**
     * Converts a tasklist into its save format and writes it in the save file.
     * 
     * @param taskList the tasklist to be saved
     */
    public void writeSaveData(TaskList taskList) {
        try {
            FileWriter fw = new FileWriter(saveFile);
            String saveData = "";
            for (Task t : taskList.getTasks()) {
                saveData += t.toSave() + System.lineSeparator();
            }
            fw.write(saveData);
            fw.close();
        } catch (IOException e) {
            ui.printError(Messages.FILE_ERROR);
        }
    }
}
