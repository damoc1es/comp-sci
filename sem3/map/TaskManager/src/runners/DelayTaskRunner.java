package runners;

public class DelayTaskRunner extends AbstractTaskRunner {

    public DelayTaskRunner(TaskRunner runner) {
        super(runner);
    }

    @Override
    public void executeOneTask() {
        try {
            Thread.sleep(900);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        runner.executeOneTask();
    }
}
