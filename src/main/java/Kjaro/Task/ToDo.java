package kjaro.task;

/**
 * A todo is the simplest task, with no additional features
 */

public class ToDo extends Task {

    /**
     * Constructor for a ToDo
     *
     * @param taskName the name of the ToDo
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
