package Kjaro.Task;

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
        return tasks.get(taskNumber).markAsDone();
    }

    public Task markTaskUndone(int taskNumber) {
        return tasks.get(taskNumber).markAsUndone();
    }

    public Task deleteTask(int taskNumber) {
        Task removedTask = tasks.get(taskNumber);
        tasks.remove(taskNumber);
        return removedTask;
    }
}
