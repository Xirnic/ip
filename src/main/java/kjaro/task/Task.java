package kjaro.task;

/**
 * Base class for the various types of tasks
 */
public abstract class Task {
    private String taskName;
    private boolean isDone;

    /**
     * The constructor for a task. By default, it is marked as undone.
     * 
     * @param taskName the name of the task.
     */
    public Task(String taskName) {
        assert taskName != null : "Task null";
        this.taskName = taskName;
        this.isDone = false;
    }

    public String getName() {
        return taskName;
    }

    /**
     * Marks a task as done
     * 
     * @return the marked task
     */
    public Task markAsDone() {
        this.isDone = true;
        return this;
    }

    /**
     * Marks a task as undone
     * 
     * @return the marked task
     */
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

    /**
     * Formats the task into the save file format
     * 
     * @return the formatted string to save
     */
    public String toSave() {
        if (this.isDone) {
            return "X/" + this.taskName;
        } else {
            return "O/" + this.taskName;
        }
    }
}
