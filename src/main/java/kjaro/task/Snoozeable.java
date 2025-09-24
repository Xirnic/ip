package kjaro.task;

public interface Snoozeable {
    /**
     * Delays a task's date parameters
     * @param days days to snooze for
     */
    public void snooze(int days);
}
