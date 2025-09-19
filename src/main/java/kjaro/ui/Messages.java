package kjaro.ui;

import java.util.function.Function;

public class Messages {
    public static final String COMMAND_ERROR = "I don't understand that command @@";
    public static final String DATE_ERROR = "Unrecognised date format! Use <yyyy-mm-dd>";
    public static final String TODO_ERROR = "Incorrect input! Use todo <task name>";
    public static final String DEADLINE_ERROR = "Incorrect input! Use deadline <task name> /by <due date>";
    public static final String EVENT_ERROR = "Incorrect input! Use event <task name> /from <start date> /to <end date>";
    public static final String MARK_ERROR = "Incorrect input! Use mark <task number> to mark your tasks!";
    public static final String UNMARK_ERROR = "Incorrect input! Use unmark <task number> to unmark your tasks!";
    public static final String TASK_OOB_ERROR = "That task number doesn't exist!";
    public static final String DELETE_ERROR = "Incorrect input! Use delete <task number> to delete your tasks!";
    public static final String FILE_ERROR = "Unable to access save file!";
    public static final String SNOOZE_ERROR = "Incorrect input! Use snooze <task number> /for <days> to snooze!";
    public static final String UNSNOOZEABLE_ERROR = "That task cannot be snoozed!";
    
    public static final String WELCOME_MESSAGE = Format.LOGO + Format.LINE + "\n" + "Hello! I'm Kjaro\n" + "What can I do for you?";
    public static final Function<Integer, String> TASKLIST_MESSAGE = x -> "Here is your task list! You currently have " + x + " task(s)!";
    public static final String TASK_ADDED_MESSAGE = "Task added sucessfully!";
    public static final Function<Integer, String> MARK_MESSAGE = x -> "Well done! I've marked task " + x + " as done!";
    public static final Function<Integer, String> UNMARK_MESSAGE = x -> "I've marked task " + x + "as undone, you'll get it next time.";
    public static final Function<Integer, String> DELETE_MESSAGE = x -> "Alright, I've deleted task " + x + ".";
    public static final String GOODBYE_MESSAGE = "That's all? See you soon!";
    public static final String FILTERED_LIST_MESSAGE = "Here are the matching tasks in your list:";
    public static final String SAVE_MESSAGE = "Data saved successfully!";
    public static final String SNOOZE_MESSAGE = "Task snoozed! You'll get it done, right?";
}
