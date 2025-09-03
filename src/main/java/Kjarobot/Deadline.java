package Kjarobot;

public class Deadline extends Task {
    private String dueDate;

    public Deadline(String taskName, String dueDate) {
        super(taskName);
        this.dueDate = dueDate;
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() 
                + " (by: " + dueDate + ")";
    }

    @Override
    public String toSave() {
        return "D |" + super.toSave() 
                + " | " + dueDate;
    }
    
}
