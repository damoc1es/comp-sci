import model.MessageTask;
import runners.PrinterTaskRunner;
import runners.StrategyTaskRunner;
import utils.StrategyType;

import java.time.LocalDateTime;

public class TestRunner {
    private static MessageTask[] getMessages() {
        MessageTask m1 = new MessageTask("1", "Test task1", "Message 1", "me", "you", LocalDateTime.now());
        MessageTask m2 = new MessageTask("2", "Test task2", "Message 2", "another me", "another you", LocalDateTime.now());
        MessageTask m3 = new MessageTask("3", "Test task3", "Message 3", "Gabriel", "everyone", LocalDateTime.now());
        MessageTask m4 = new MessageTask("4", "Test task4", "Message 4", "X", "Y", LocalDateTime.now());
        MessageTask m5 = new MessageTask("5", "Test task5", "Message 5", "Y", "Z", LocalDateTime.now());

        return new MessageTask[]{m1, m2, m3, m4, m5};
    }

    public static void run() {
        MessageTask[] tasks = getMessages();
//        for(MessageTask message : tasks) {
//            System.out.println(message);
//        }

        PrinterTaskRunner printerTaskRunner = new PrinterTaskRunner(new StrategyTaskRunner(StrategyType.LIFO));
        printerTaskRunner.addTask(tasks[0]);
        printerTaskRunner.addTask(tasks[1]);
        printerTaskRunner.addTask(tasks[2]);
        printerTaskRunner.executeAll();
    }
}
