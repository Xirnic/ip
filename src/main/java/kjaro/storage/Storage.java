package kjaro.storage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import kjaro.Kjaro;
import kjaro.task.Deadline;
import kjaro.task.Event;
import kjaro.task.Task;
import kjaro.task.TaskList;
import kjaro.task.ToDo;
import kjaro.ui.Messages;
import kjaro.ui.UI;

/**
 * Represents the storage component of Kjaro, to save and read data.
 */
public class Storage {

    protected final UI ui;
    protected final File saveFile;
    protected final Task ERR_TASK = new ToDo("Save Error!");

    /**
     * The constructor for a Storage.
     * 
     * @param ui for error printing.
     */
    public Storage(UI ui) {
        this.ui = ui;
        this.saveFile = new File(getPath().getParent(), "SaveFile.txt");
    }

    private static File getPath() {
        try {
            return new File(
                    Kjaro.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Reads the associated save file, and creates a new file if one isn't found.
     * 
     * @return TaskList with loaded tasks.
     */
    public TaskList loadSaveFile() {
        TaskList taskList = new TaskList();
        try {
            if (!saveFile.createNewFile()) {
                Scanner saveFileScanner = new Scanner(saveFile);
                readFile(saveFileScanner, taskList);
                saveFileScanner.close();
            }
        } catch (IOException e) {
            ui.formatErrorMessage(Messages.FILE_ERROR);
        }
        return taskList;
    }

    private void readSaveData(String line, TaskList taskList) {
        final Pattern savePattern = Pattern.compile("(?<taskType>[A-Z])" + "\\/" + "(?<isDone>[XO])" + "\\/"
                                        + "(?<arguments>.*)");
        final Matcher matcher = savePattern.matcher(line);
        if (!matcher.matches()) {
            return;
        }
        String taskType = matcher.group("taskType");
        boolean isDone = matcher.group("isDone").equals("X");
        String arguments = matcher.group("arguments");
        Task addedTask;
        switch (taskType) {
        case ("T"):
            addedTask = loadToDo(arguments);
            break;
        case ("D"):
            addedTask = loadDeadline(arguments);
            break;
        case ("E"):
            addedTask = loadEvent(arguments);
            break;
        default:
            assert false : taskType;
            return;
        }
        if (ERR_TASK == addedTask) {
            return;
        }
        if (isDone) {
            addedTask.markAsDone();
        }
        taskList.addToTasks(addedTask);
    }

    /**
     * Converts a task list into its save format and writes it in the save file.
     * 
     * @param taskList the task list to be saved.
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
            ui.formatErrorMessage(Messages.FILE_ERROR);
        }
    }

    private void readFile(Scanner scanner, TaskList taskList) {
        while (scanner.hasNext()) {
            readSaveData(scanner.nextLine(), taskList);
        }
    }

    private Task loadToDo(String arguments) {
        if (arguments.contains("/") || arguments.equals("")) {
            return ERR_TASK;
        }
        ToDo toDo = new ToDo(arguments);
        return toDo;
    }

    private Task loadDeadline(String arguments) {
        final Pattern deadlinePattern = Pattern.compile("(?<deadlineName>[^/]+)" + "\\/" + "(?<deadlineBy>[^/]+)");
        final Matcher matcher = deadlinePattern.matcher(arguments.trim());
        if (!matcher.matches()) {
            return ERR_TASK;
        }
        String deadlineName = matcher.group("deadlineName").trim();
        String deadlineBy = matcher.group("deadlineBy").trim();
        try {
            LocalDate ldDeadlineBy = LocalDate.parse(deadlineBy);
            Deadline deadline = new Deadline(deadlineName, ldDeadlineBy);
            return deadline;
        } catch (DateTimeParseException e) {
            return ERR_TASK;
        }
    }

    private Task loadEvent(String arguments) {
        final Pattern eventPattern = Pattern.compile("(?<eventName>[^/]+)" + "\\/" + "(?<eventFrom>[^/]+)" + "\\/"
                + "(?<eventTo>[^/]+)");
        final Matcher matcher = eventPattern.matcher(arguments.trim());
        if (!matcher.matches()) {
            return ERR_TASK;
        }
        String eventName = matcher.group("eventName").trim();
        String eventFrom = matcher.group("eventFrom").trim();
        String eventTo = matcher.group("eventTo").trim();
        try {
            LocalDate ldEventFrom = LocalDate.parse(eventFrom);
            LocalDate ldEventTo = LocalDate.parse(eventTo);
            Event event = new Event(eventName, ldEventFrom, ldEventTo);
            return event;
        } catch (DateTimeParseException e) {
            return ERR_TASK;
        }
    }
}
