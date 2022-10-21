import model.MessageTask;
import model.SortingTask;
import runners.DelayTaskRunner;
import runners.PrinterTaskRunner;
import runners.StrategyTaskRunner;
import utils.SortType;
import utils.StrategyType;

import java.time.LocalDateTime;
import java.util.Scanner;

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

        StrategyType strat;
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter strategy {LIFO, FIFO}: ");
        switch (scanner.nextLine().toUpperCase()) {
            case "LIFO" -> strat = StrategyType.LIFO;
            case "FIFO" -> strat = StrategyType.FIFO;
            default -> {
                System.out.println("Invalid, so it was defaulted to LIFO.");
                strat = StrategyType.LIFO;
            }
        }

        PrinterTaskRunner printerTaskRunner = new PrinterTaskRunner(new StrategyTaskRunner(strat));
        for(int i=0; i<3; i++)
            printerTaskRunner.addTask(tasks[i]);

        printerTaskRunner.executeAll();
    }

    public static void runLaboratory() {
        SortingTask task = new SortingTask("57", "A sorting task", SortType.BUBBLE_SORT, new int[]{8, 5, 1, 5, 3, 64, 23});
        SortingTask task2 = new SortingTask("52", "A sorting task", SortType.MERGE_SORT, new int[]{8, 5, 1, 5, 3, 64, 23});
        task.execute();
        task2.execute();

        MessageTask[] tasks = getMessages();

        StrategyType strat;
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter strategy {LIFO, FIFO}: ");
        switch (scanner.nextLine().toUpperCase()) {
            case "LIFO" -> strat = StrategyType.LIFO;
            case "FIFO" -> strat = StrategyType.FIFO;
            default -> {
                System.out.println("Invalid, so it was defaulted to LIFO.");
                strat = StrategyType.LIFO;
            }
        }

        PrinterTaskRunner printerTaskRunner = new PrinterTaskRunner(new DelayTaskRunner(new StrategyTaskRunner(strat)));
        for(int i=0; i<3; i++)
            printerTaskRunner.addTask(tasks[i]);

        printerTaskRunner.executeAll();
    }
}
