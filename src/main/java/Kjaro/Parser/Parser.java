package Kjaro.Parser;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import Kjaro.Storage.Storage;
import Kjaro.Task.Deadline;
import Kjaro.Task.Event;
import Kjaro.Task.Task;
import Kjaro.Task.TaskList;
import Kjaro.Task.ToDo;
import Kjaro.UI.Messages;
import Kjaro.UI.UI;

/**
 * A simple parser for the commands supported by Kjaro.
 */
public class Parser {

    protected TaskList taskList;
    protected UI ui;
    protected Storage storage;

    public Parser(TaskList taskList, UI ui, Storage storage) {
        this.taskList = taskList;
        this.ui = ui;
        this.storage = storage;
    }

    /**
     * Parses the user input, converting it to functions supported by Kjaro.
     * 
     * @param input the user input.
     * @return boolea, whether Kjaro should keep running.
     */
    public boolean parseInput(String input) {
        final Pattern commandPattern = Pattern.compile("(?<commandWord>\\S+)" + "(?<arguments>.*)");
        final Matcher matcher = commandPattern.matcher(input.trim());
        if (!matcher.matches()) {
            ui.printError(Messages.COMMAND_ERROR);
        }
        final String commandWord = matcher.group("commandWord").trim();
        final String arguments = matcher.group("arguments").trim();

        switch (commandWord) {
        case ("list"):
            displayList(Messages.TASKLIST_MESSAGE.apply(taskList.getCount()), taskList);
            break;
        case ("find"):
            filterList(arguments);
            break;
        case ("bye"):
            exit();
            return false;
        case ("todo"):
            tryToDo(arguments);
            break;
        case ("deadline"):
            tryDeadline(arguments);
            break;
        case ("event"):
            tryEvent(arguments);
            break;
        case ("mark"):
            tryMark(arguments);
            break;
        case ("unmark"):
            tryUnmark(arguments);
            break;
        case ("delete"):
            tryDelete(arguments);
            break;
        default:
            ui.printError(Messages.COMMAND_ERROR);
        }
        return true;
    }

    /**
     * Displays the full list of tasks using the UI class.
     */
    private void displayList(String initialMessage, TaskList taskList) {
        String[] taskListDisplay = taskList.getTasks().stream().map(x -> x.toString()).toArray(String[]::new);
        String[] fullMessage = new String[1 + taskListDisplay.length];
        fullMessage[0] = initialMessage;
        System.arraycopy(taskListDisplay, 0, fullMessage, 1, taskListDisplay.length);
        ui.printMessage(fullMessage);
    }

    /**
     * Prints the goodbye message, before saving the current data.
     */
    private void exit() {
        ui.printMessage(Messages.GOODBYE_MESSAGE);
        storage.writeSaveData(taskList);
    }

    /**
     * Attempt to add a todo to the tasklist, printing an error if there are
     * unexpected arguments.
     * 
     * @param arguments the arguments in the user's input.
     */
    private void tryToDo(String arguments) {
        if (arguments.contains("/")) {
            ui.printError(Messages.TODO_ERROR);
        }
        ToDo toDo = new ToDo(arguments);
        taskList.addToTasks(toDo);
        ui.printMessage(Messages.TASK_ADDED_MESSAGE, toDo.toString());
    }

    /**
     * Attempts to add a deadline to the tasklist, printing an error if there
     * are unexpected arguments.
     * 
     * @param arguments the arguments in the user's input.
     */
    private void tryDeadline(String arguments) {
        final Pattern deadlinePattern = Pattern.compile("(?<deadlineName>[^/]+)" + "\\/by" + "(?<deadlineBy>[^/]+)");
        final Matcher matcher = deadlinePattern.matcher(arguments.trim());
        if (!matcher.matches()) {
            ui.printError(Messages.DEADLINE_ERROR);
            return;
        }
        String deadlineName = matcher.group("deadlineName").trim();
        String deadlineBy = matcher.group("deadlineBy").trim();
        try {
            LocalDate ldDeadlineBy = LocalDate.parse(deadlineBy);
            Deadline deadline = new Deadline(deadlineName, ldDeadlineBy);
            taskList.addToTasks(deadline);
            ui.printMessage(Messages.TASK_ADDED_MESSAGE, deadline.toString());
        } catch (DateTimeParseException e) {
            ui.printError(Messages.DATE_ERROR);
        }
    }

    /**
     * Attempts to add a event to the tasklist, printing an error if there are
     * unexpected arguments.
     * 
     * @param arguments the arguments in the user's input.
     */
    private void tryEvent(String arguments) {
        final Pattern eventPattern = Pattern.compile("(?<eventName>[^/]+)" + "\\/from" + "(?<eventFrom>[^/]+)" + "\\/to"
                                        + "(?<eventTo>[^/]+)");
        final Matcher matcher = eventPattern.matcher(arguments.trim());
        if (!matcher.matches()) {
            ui.printError(Messages.EVENT_ERROR);
            return;
        }
        String eventName = matcher.group("eventName").trim();
        String eventFrom = matcher.group("eventFrom").trim();
        String eventTo = matcher.group("eventTo").trim();
        try {
            LocalDate ldEventFrom = LocalDate.parse(eventFrom);
            LocalDate ldEventTo = LocalDate.parse(eventTo);
            Event event = new Event(eventName, ldEventFrom, ldEventTo);
            taskList.addToTasks(event);
            ui.printMessage(Messages.TASK_ADDED_MESSAGE, event.toString());

        } catch (DateTimeParseException e) {
            ui.printError(Messages.DATE_ERROR);
        }
    }

    /**
     * Attempts to mark a task as done, printing an error if the argument isn't
     * a number or is out of bounds.
     * 
     * @param arguments the arguments in the user's inputs.
     */
    private void tryMark(String arguments) {
        final Pattern markPattern = Pattern.compile("(?<taskNumber>^[0-9]+$)");
        final Matcher matcher = markPattern.matcher(arguments);
        if (!matcher.matches()) {
            ui.printError(Messages.MARK_ERROR);
            return;
        }
        int taskNumber = Integer.valueOf(matcher.group("taskNumber"));
        try {
            Task task = taskList.markTaskDone(taskNumber - 1);
            ui.printMessage(Messages.MARK_MESSAGE.apply(taskNumber), task.toString());
        } catch (IndexOutOfBoundsException e) {
            ui.printError(Messages.TASK_OOB_ERROR);
        }
    }

    /**
     * Attempts to mark a task as undone, printing an error if the argument
     * isn't a number or is out of bounds.
     * 
     * @param arguments the arguments in the user's inputs.
     */
    private void tryUnmark(String arguments) {
        final Pattern unmarkPattern = Pattern.compile("(?<taskNumber>^[0-9]+$)");
        final Matcher matcher = unmarkPattern.matcher(arguments);
        if (!matcher.matches()) {
            ui.printError(Messages.UNMARK_ERROR);
            return;
        }
        int taskNumber = Integer.valueOf(matcher.group("taskNumber"));
        try {
            Task task = taskList.markTaskUndone(taskNumber - 1);
            ui.printMessage(Messages.UNMARK_MESSAGE.apply(taskNumber), task.toString());
        } catch (IndexOutOfBoundsException e) {
            ui.printError(Messages.TASK_OOB_ERROR);
        }
    }

    /**
     * Attempts to delete a task, printing an error if the argument isn't a
     * number or is out of bounds.
     * 
     * @param arguments the arguments in the user's inputs.
     */
    private void tryDelete(String arguments) {
        final Pattern deletePattern = Pattern.compile("(?<taskNumber>^[0-9]+$)");
        final Matcher matcher = deletePattern.matcher(arguments);
        if (!matcher.matches()) {
            ui.printError(Messages.DELETE_ERROR);
            return;
        }
        int taskNumber = Integer.valueOf(matcher.group("taskNumber"));
        try {
            Task task = taskList.deleteTask(taskNumber - 1);
            ui.printMessage(Messages.DELETE_MESSAGE.apply(taskNumber), task.toString());
        } catch (IndexOutOfBoundsException e) {
            ui.printError(Messages.TASK_OOB_ERROR);
        }
    }

    private void filterList(String arguments) {
        displayList(Messages.FILTERED_LIST_MESSAGE,taskList.filterList(arguments));
    }
}