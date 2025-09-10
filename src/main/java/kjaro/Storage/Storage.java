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

public class Storage {

    public static final String SUCCESSFUL_STRING = "Loaded successfully";
    protected final UI ui;
    protected final File saveFile;
    protected ArrayList<String> errors = new ArrayList<>();
    protected boolean isFullyLoaded;

    public Storage(UI ui) {
        this.ui = ui;
        this.saveFile = new File("data/SaveFile.txt");
    }

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

    private String readSaveData(String line, TaskList taskList) {
        final Pattern savePattern = 
                Pattern.compile("(?<taskType>[TDE])" + "\\/"
                    + "(?<isDone>[XO])" + "\\/"
                    + "(?<taskName>[^/]+)" + "(?:\\/)?"
                    + "(?<firstDate>[^/]+)?" + "(?:\\/)?"
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

    private String logSaveError(String line) {
        /*
        isFullyLoaded = false;
        final Pattern savePatternLenient = 
                Pattern.compile("(?<taskType>.)?" + "(?:\\/)?"
                    + "(?<isDone>.)?" + "(?:\\/)?"
                    + "(?<taskName>[^/]+)?" + "(?:\\/)?"
                    + "(?<firstDate>[^/]+)?"  + "(?:\\/)?"
                    + "(?<secondDate>[^/]+)?");
        final Matcher matcher = savePatternLenient.matcher(line);
        ui.printError(line);
        */
        return line;
    }

    public ArrayList<String> getErrors() {
        return errors;
    }

    public void writeSaveData(TaskList taskList) {
        try {
            FileWriter fw = new FileWriter(saveFile);
            String saveData = "";
            for (Task t : taskList.getTasks()) {
                saveData += t.toSave() + System.lineSeparator();
            }
            fw.write(saveData);
            fw.close();
        }
        catch (IOException e) {
            ui.printError(Messages.FILE_ERROR);
        } 
    }
}
