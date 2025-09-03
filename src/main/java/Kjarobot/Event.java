package Kjarobot;

public class Event extends Task {
    private String fromDate;
    private String toDate;

    public Event(String taskName, String fromDate, String toDate) {
        super(taskName);
        this.fromDate = fromDate;
        this.toDate = toDate;
    }

    @Override
    public String toString() {
        return "[E]" + super.toString()
                + " (from: " + fromDate
                + " to: " + toDate + ")";
    }

    @Override
    public String toSave() {
        return "E |" + super.toSave()
                + " | " + fromDate
                + " | " + toDate;
    }
}
