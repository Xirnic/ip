package Kjaro.Task;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Event extends Task {
    private LocalDate fromDate;
    private LocalDate toDate;

    public Event(String taskName, LocalDate fromDate, LocalDate toDate) {
        super(taskName);
        this.fromDate = fromDate;
        this.toDate = toDate;
    }

    @Override
    public String toString() {
        return "[E]" + super.toString()
                + " (from: " + fromDate.format(DateTimeFormatter.ofPattern("dd MMM uuuu"))
                + " to: " + toDate.format(DateTimeFormatter.ofPattern("dd MMM uuuu")) + ")";
    }

    @Override
    public String toSave() {
        return "E/" + super.toSave()
                + "/" + fromDate
                + "/" + toDate;
    }
}
