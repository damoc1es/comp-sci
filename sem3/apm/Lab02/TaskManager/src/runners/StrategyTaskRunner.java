package runners;

import containers.Container;
import factories.TaskContainerFactory;
import model.Task;
import utils.StrategyType;

public class StrategyTaskRunner implements TaskRunner {
    private Container container;

    public StrategyTaskRunner(StrategyType strategy) {
        container = TaskContainerFactory.getInstance().createContainer(strategy);
    }

    @Override
    public void executeOneTask() {
        if(!container.isEmpty()) {
            container.remove().execute();
        }
    }

    @Override
    public void executeAll() {
        while(!container.isEmpty()) {
            executeOneTask();
        }
    }

    @Override
    public void addTask(Task t) {
        container.add(t);
    }

    @Override
    public boolean hasTask() {
        return !container.isEmpty();
    }
}
