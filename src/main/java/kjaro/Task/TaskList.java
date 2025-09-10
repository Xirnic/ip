package Kjaro.Task;

import java.util.ArrayList;

public class TaskList {

    private ArrayList<Task> tasks;

    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    public TaskList(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    // Adds the task to the list, and returns the new total tasks
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
