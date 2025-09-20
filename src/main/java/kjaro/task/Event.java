package kjaro.task;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * An event is a task with from and to dates.
 */
public class Event extends Task implements Snoozeable {
    private LocalDate fromDate;
    private LocalDate toDate;
    private static final DateTimeFormatter DISPLAY_FORMATTER = DateTimeFormatter.ofPattern("dd MMM uuuu");
    /**
     * The constructor for an Event.
     * 
     * @param taskName the name of the event.
     * @param fromDate the start date (from) of the event.
     * @param toDate the end date (to) of the event.
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
