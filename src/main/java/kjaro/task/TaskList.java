package kjaro.task;

import java.util.ArrayList;

public class TaskList {

    private ArrayList<Task> tasks;

    /**
     * Constructor for an empty list
     */
    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    /**
     * Constructor for an existing list
     * 
     * @param tasks
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
        return tasks;
    }

    public Task markTaskDone(int taskNumber) {
        return tasks.get(taskNumber - 1).markAsDone();
    }

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

    public TaskList filterList(String keyword) {
        TaskList filtered = new TaskList();
        tasks.stream().filter(x -> x.getName().contains(keyword)).forEach(x -> filtered.addToTasks(x));
        return filtered;
    }
}
