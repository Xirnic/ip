package kjaro.task;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Represents a task with a start and end date
 */
public class Event extends Task implements Snoozeable {

    private LocalDate fromDate;
    private LocalDate toDate;
    private static final DateTimeFormatter DISPLAY_FORMATTER = DateTimeFormatter.ofPattern("dd MMM uuuu");

    /**
     * Constructs a new event
     * 
     * @param taskName the name of the task.
     * @param fromDate the start date of the task.
     * @param toDate the end date of the task.
     */
    public Event(String taskName, LocalDate fromDate, LocalDate toDate) {
        super(taskName);
        this.fromDate = fromDate;
        this.toDate = toDate;
    }

    @Override
    public void snooze(int days) {
        this.fromDate = this.fromDate.plusDays(days);
        this.toDate = this.toDate.plusDays(days);
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + fromDate.format(DISPLAY_FORMATTER)
                                        + " to: " + toDate.format(DISPLAY_FORMATTER) + ")";
    }

    @Override
    public String toSave() {
        return "E/" + super.toSave() + "/" + fromDate + "/" + toDate;
    }
}
