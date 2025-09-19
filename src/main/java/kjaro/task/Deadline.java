package kjaro.task;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * A Deadline is a task with a due date
 */
public class Deadline extends Task implements Snoozeable {

    private LocalDate dueDate;

    /**
     * The constructor for a deadline.
     * 
     * @param taskName the name of the deadline
     * @param dueDate the date at which the deadline is due
     */
    public Deadline(String taskName, LocalDate dueDate) {
        super(taskName);
        this.dueDate = dueDate;
    }

    @Override
    public void snooze(int days) {
        this.dueDate = this.dueDate.plusDays(days);
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + dueDate.format(DateTimeFormatter.ofPattern("dd MMM uuuu")) + ")";
    }

    @Override
    public String toSave() {
        return "D/" + super.toSave() + "/" + dueDate;
    }

}
