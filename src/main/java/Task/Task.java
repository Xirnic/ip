package Task;

public abstract class Task {
    private String taskName;
    private boolean isDone;

    public Task(String taskName) {
        this.taskName = taskName;
        this.isDone = false;
    }

    public Task markAsDone() {
        this.isDone = true;
        return this;
    }

    public Task markAsUndone() {
        this.isDone = false;
        return this;
    }

    @Override
    public String toString() {
        if (this.isDone) {
            return "[X] " + this.taskName;
        } else {
            return "[ ] " + this.taskName;
        }
    }

    public String toSave() {
        if (this.isDone) {
            return "X/" + this.taskName;
        } else {
            return "O/" + this.taskName;
        }
    }

    public static String getErrMessage() {
        return "Unimplemented!";
    }
}
