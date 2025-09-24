package kjaro.task;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Represents a list of tasks.
 */
public class TaskList {

    private ArrayList<Task> tasks;

    /**
     * Constructor for an empty task list
     *
     */
    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    /**
     * Constructor for an existing task list.
     * 
     * @param tasks the task list.
     */
    public TaskList(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    /**
     * Adds a task to the list.
     * 
     * @param task the task to be added.
     */
    public void addToTasks(Task task) {
        tasks.add(task);
    }

    public int getCount() {
        return tasks.size();
    }

    public ArrayList<Task> getTasks() {
        return new ArrayList<Task>(tasks);
    }

    /**
     * Marks a task in the list as done.
     * @param taskNumber the number associated with the task.
     * @return the marked task
     */
    public Task markTaskDone(int taskNumber) {
        return tasks.get(taskNumber - 1).markAsDone();
    }

    /**
     * Marks a task in the list as undone.
     * @param taskNumber the number associated with the task.
     * @return the marked task
     */
    public Task markTaskUndone(int taskNumber) {
        return tasks.get(taskNumber - 1).markAsUndone();
    }

    public Task deleteTask(int taskNumber) {
        Task removedTask = tasks.get(taskNumber - 1);
        tasks.remove(taskNumber - 1);
        return removedTask;
    }

    public Task getTask(int taskNumber) {
        return tasks.get(taskNumber - 1);
    }

    /**
     * Creates a new task list, filtered by the keyword.
     * @param keyword to filter by
     * @return the filtered task list.
     */
    public TaskList filterList(String keyword) {
        TaskList filtered = new TaskList();
        tasks.stream()
                .filter(x -> x.getName().contains(keyword))
                .forEach(x -> filtered.addToTasks(x));
        return filtered;
    }
}
