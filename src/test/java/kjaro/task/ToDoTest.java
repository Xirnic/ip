package kjaro.task;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ToDoTest {
    @Test
    public void toStringTest() {
        Task test = new ToDo("Test task");
        String expected = "[T][ ] Test task";
        assertEquals(test.toString(), expected);
    }

    @Test
    public void toSaveTest() {
        Task test = new ToDo("Test task 2");
        test.markAsDone();
        String expected = "T/X/Test task 2";
        assertEquals(test.toSave(), expected);
    }
}