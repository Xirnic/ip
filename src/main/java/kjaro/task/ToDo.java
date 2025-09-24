package kjaro.task;

/**
 * Represents a task with no due date.
 */

public class ToDo extends Task {

    /**
     * Constructs a new ToDo.
     *
     * @param taskName the name of the task.
     */
    public ToDo(String taskName) {
        super(taskName);
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }

    @Override
    public String toSave() {
        return "T/" + super.toSave();
    }
}
