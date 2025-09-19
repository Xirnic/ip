package kjaro.task;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TaskListTest {
    @Test
    public void markTaskDoneTest() {
        Task test1 = new ToDo("Zero");
        Task test2 = new ToDo("One");
        Task test3 = new ToDo("Two");
        TaskList taskList = new TaskList();
        taskList.addToTasks(test1);
        taskList.addToTasks(test2);
        taskList.addToTasks(test3);
        taskList.markTaskDone(1);
        String expected = "[T][X] One";
        assertEquals(test2.toString(), expected);
    }

    @Test
    public void deleteTaskTest() {
        Task test1 = new ToDo("Zero");
        Task test2 = new ToDo("One");
        Task test3 = new ToDo("Two");
        TaskList taskList = new TaskList();
        taskList.addToTasks(test1);
        taskList.addToTasks(test2);
        taskList.addToTasks(test3);
        taskList.deleteTask(1);
        assertEquals(taskList.getTasks().get(1), test3);
    }
}
