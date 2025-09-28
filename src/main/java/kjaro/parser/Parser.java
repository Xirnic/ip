package kjaro.parser;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import kjaro.storage.Storage;
import kjaro.task.*;
import kjaro.ui.Messages;
import kjaro.ui.UI;

/**
 * Represents a parser for Kjaro's supported commands.
 */
public class Parser {

    protected TaskList taskList;
    protected UI ui;
    protected Storage storage;
    private final Pattern TASK_NUMBER_PATTERN = Pattern.compile("(?<taskNumber>^[0-9]+$)");

    /**
     * Constructs a new parser
     * @param taskList the task list that the commands will be carried out on.
     * @param ui the UI to print system messages.
     * @param storage The storage to access saved data.
     */
    public Parser(TaskList taskList, UI ui, Storage storage) {
        this.taskList = taskList;
        this.ui = ui;
        this.storage = storage;
    }

    /**
     * Parses the user input, converting to and executing commands supported by Kjaro.
     * 
     * @param input the user input.
     * @return whether Kjaro should keep running.
     */
    public String parseInput(String input) {
        final Pattern commandPattern = Pattern.compile("(?<commandWord>\\S+)" + "(?<arguments>.*)");
        final Matcher matcher = commandPattern.matcher(input.trim());
        if (!matcher.matches()) {
            ui.printError(Messages.COMMAND_ERROR);
        }
        final String commandWord = matcher.group("commandWord").trim();
        final String arguments = matcher.group("arguments").trim();

        switch (commandWord) {
        case ("list"):
            return displayList(Messages.TASKLIST_MESSAGE.apply(taskList.getCount()), taskList);
        case ("find"):
            return filterList(arguments);
        case ("bye"):
            return exit();
        case ("save"):
            return save();
        case ("todo"):
            return tryToDo(arguments);
        case ("deadline"):
            return tryDeadline(arguments);
        case ("event"):
            return tryEvent(arguments);
        case ("mark"):
            return tryMark(arguments);
        case ("unmark"):
            return tryUnmark(arguments);
        case ("delete"):
            return tryDelete(arguments);
        case ("snooze"):
            return trySnooze(arguments);
        default:
            return ui.printError(Messages.COMMAND_ERROR);
        }
    }

    /**
     * Displays the numbered list of tasks.
     */
    private String displayList(String initialMessage, TaskList taskList) {
        String[] taskListDisplay = taskList.getTasks().stream().map(x -> x.toString()).toArray(String[]::new);
        for (int i = 0; i < taskListDisplay.length; i++) {
            taskListDisplay[i] = (i + 1) + ": " + taskListDisplay[i];
        }
        String[] fullMessage = new String[1 + taskListDisplay.length];
        fullMessage[0] = initialMessage;
        System.arraycopy(taskListDisplay, 0, fullMessage, 1, taskListDisplay.length);
        return ui.printMessage(fullMessage);
    }

    /**
     * Saves data, before exiting
     * @return the exit message
     */
    private String exit() {
        storage.writeSaveData(taskList);
        return ui.printMessage(Messages.GOODBYE_MESSAGE);
    }

    /**
     * Saves the current data
     * @return the save successful message
     */
    private String save() {
        storage.writeSaveData(taskList);
        return ui.printMessage(Messages.SAVE_MESSAGE);
    }

    /**
     * Attempts to instantiate and add a todo to the task list.
     *
     * @param arguments the arguments from the user's input.
     * @return the success / error message.
     */
    private String tryToDo(String arguments) {
        if (arguments.contains("/") || arguments.equals("")) {
            ui.printError(Messages.TODO_ERROR);
        }
        ToDo toDo = new ToDo(arguments);
        taskList.addToTasks(toDo);
        return ui.printMessage(Messages.TASK_ADDED_MESSAGE, toDo.toString());
    }

    /**
     * Attempts to instantiate and add a deadline to the task list.
     *
     * @param arguments the arguments in the user's input.
     * @return the success / error message.
     */
    private String tryDeadline(String arguments) {
        final Pattern deadlinePattern = Pattern.compile("(?<deadlineName>[^/]+)" + "\\/by" + "(?<deadlineBy>[^/]+)");
        final Matcher matcher = deadlinePattern.matcher(arguments.trim());
        if (!matcher.matches()) {
            return ui.printError(Messages.DEADLINE_ERROR);
        }
        String deadlineName = matcher.group("deadlineName").trim();
        String deadlineBy = matcher.group("deadlineBy").trim();
        assert deadlineName != null && deadlineBy != null : "Deadline null";
        try {
            LocalDate ldDeadlineBy = LocalDate.parse(deadlineBy);
            Deadline deadline = new Deadline(deadlineName, ldDeadlineBy);
            taskList.addToTasks(deadline);
            return ui.printMessage(Messages.TASK_ADDED_MESSAGE, deadline.toString());
        } catch (DateTimeParseException e) {
            return ui.printError(Messages.DATE_ERROR);
        }
    }

    /**
     * Attempts to instantiate and add an event to the task list.
     *
     * @param arguments the arguments in the user's input.
     * @return the success / error message.
     */
    private String tryEvent(String arguments) {
        final Pattern eventPattern = Pattern.compile("(?<eventName>[^/]+)" + "\\/from" + "(?<eventFrom>[^/]+)" + "\\/to"
                                        + "(?<eventTo>[^/]+)");
        final Matcher matcher = eventPattern.matcher(arguments.trim());
        if (!matcher.matches()) {
            return ui.printError(Messages.EVENT_ERROR);
        }
        String eventName = matcher.group("eventName").trim();
        String eventFrom = matcher.group("eventFrom").trim();
        String eventTo = matcher.group("eventTo").trim();
        assert eventName != null && eventFrom != null && eventTo != null : "Event null";
        try {
            LocalDate ldEventFrom = LocalDate.parse(eventFrom);
            LocalDate ldEventTo = LocalDate.parse(eventTo);
            Event event = new Event(eventName, ldEventFrom, ldEventTo);
            taskList.addToTasks(event);
            return ui.printMessage(Messages.TASK_ADDED_MESSAGE, event.toString());

        } catch (DateTimeParseException e) {
            return ui.printError(Messages.DATE_ERROR);
        }
    }

    /**
     * Attempts to mark a task as done in the task list by task number.
     *
     * @param arguments the arguments in the user's input.
     * @return the success / error message.
     */
    private String tryMark(String arguments) {
        final Matcher matcher = TASK_NUMBER_PATTERN.matcher(arguments);
        if (!matcher.matches()) {
            return ui.printError(Messages.MARK_ERROR);
        }
        int taskNumber = Integer.valueOf(matcher.group("taskNumber"));
        try {
            Task task = taskList.markTaskDone(taskNumber);
            return ui.printMessage(Messages.MARK_MESSAGE.apply(taskNumber), task.toString());
        } catch (IndexOutOfBoundsException e) {
            return ui.printError(Messages.TASK_OOB_ERROR);
        }
    }

    /**
     * Attempts to mark a task as undone in the task list by task number.
     *
     * @param arguments the arguments in the user's input.
     * @return the success / error message.
     */
    private String tryUnmark(String arguments) {
        final Matcher matcher = TASK_NUMBER_PATTERN.matcher(arguments);
        if (!matcher.matches()) {
            return ui.printError(Messages.UNMARK_ERROR);
        }
        int taskNumber = Integer.valueOf(matcher.group("taskNumber"));
        try {
            Task task = taskList.markTaskUndone(taskNumber);
            return ui.printMessage(Messages.UNMARK_MESSAGE.apply(taskNumber), task.toString());
        } catch (IndexOutOfBoundsException e) {
            return ui.printError(Messages.TASK_OOB_ERROR);
        }
    }

    /**
     * Attempts to delete a task from the task list by task number.
     *
     * @param arguments the arguments in the user's input.
     * @return the success / error message.
     */
    private String tryDelete(String arguments) {
        final Matcher matcher = TASK_NUMBER_PATTERN.matcher(arguments);
        if (!matcher.matches()) {
            return ui.printError(Messages.DELETE_ERROR);
        }
        int taskNumber = Integer.valueOf(matcher.group("taskNumber"));
        try {
            Task task = taskList.deleteTask(taskNumber);
            return ui.printMessage(Messages.DELETE_MESSAGE.apply(taskNumber), task.toString());
        } catch (IndexOutOfBoundsException e) {
            return ui.printError(Messages.TASK_OOB_ERROR);
        }
    }

    /**
     * Filters the task list by keyword / keyphrase.
     * @param arguments the arguments in the user's input.
     * @return the filtered, numbered task list.
     */
    private String filterList(String arguments) {
        return displayList(Messages.FILTERED_LIST_MESSAGE,taskList.filterList(arguments));
    }

    /**
     * Attempts to snooze a task in the task list by task number.
     * Snooze duration can be specified, with a default value of 1 day.
     *
     * @param arguments the arguments in the user's input.
     * @return the success / error message.
     */
    private String trySnooze(String arguments) {
        final Pattern snoozePattern = Pattern.compile("(?<taskNumber>\\d+)" + "(?:\\s*(?<for>/for)\\s*(?<days>\\d+))?");
        final Matcher matcher = snoozePattern.matcher(arguments);
        if (!matcher.matches()) {
            return ui.printError(Messages.SNOOZE_ERROR);
        }
        int taskNumber = Integer.valueOf(matcher.group("taskNumber"));
        Optional<String> snoozeString = Optional.ofNullable((matcher.group("days")));
        int snoozeDays = Integer.valueOf(snoozeString.orElse("1"));

        Task task;
        try {
            task = taskList.getTask(taskNumber);
        } catch (IndexOutOfBoundsException e) {
            return ui.printError(Messages.TASK_OOB_ERROR);
        }
        if (!(task instanceof Snoozeable)) {
            return ui.printError(Messages.UNSNOOZEABLE_ERROR);
        }

        Snoozeable snoozeTask = (Snoozeable) task;
        snoozeTask.snooze(snoozeDays);
        return ui.printMessage(Messages.SNOOZE_MESSAGE, snoozeTask.toString());
    }
}
