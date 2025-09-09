package Task;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Deadline extends Task {
    private LocalDate dueDate;

    public Deadline(String taskName, LocalDate dueDate) {
        super(taskName);
        this.dueDate = dueDate;
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() 
                + " (by: " + dueDate.format(DateTimeFormatter.ofPattern("dd MMM uuuu")) + ")";
    }

    @Override
    public String toSave() {
        return "D/" + super.toSave() 
                + "/" + dueDate;
    }
    
}
