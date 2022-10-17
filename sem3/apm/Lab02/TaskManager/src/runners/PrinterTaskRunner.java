package runners;

import java.time.LocalDateTime;

import static utils.Constants.DATE_TIME_FORMATTER;

public class PrinterTaskRunner extends AbstractTaskRunner {
    public PrinterTaskRunner(TaskRunner runner) {
        super(runner);
    }

    @Override
    public void executeOneTask() {
        runner.executeOneTask();
        decorateExecuteOneTask();
    }

    public void decorateExecuteOneTask() {
        System.out.println("Task executed at: " + LocalDateTime.now().format(DATE_TIME_FORMATTER));
    }
}
