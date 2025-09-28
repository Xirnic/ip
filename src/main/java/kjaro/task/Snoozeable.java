package kjaro.task;

/**
 * Represents a task that supports the snooze function.
 */
public interface Snoozeable {

    /**
     * Delays a task's date parameters.
     * @param days days to snooze for.
     */
    public void snooze(int days);
}
