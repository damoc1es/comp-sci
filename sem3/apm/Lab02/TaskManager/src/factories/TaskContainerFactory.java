package factories;

import containers.Container;
import containers.StackContainer;
import utils.StrategyType;

public class TaskContainerFactory implements Factory{
    private static volatile TaskContainerFactory instance = null;

    private TaskContainerFactory() {}

    public static TaskContainerFactory getInstance() {
        if(instance == null) {
            synchronized (TaskContainerFactory.class) {
                if(instance == null) {
                    instance = new TaskContainerFactory();
                }
            }
        }
        return instance;
    }

    @Override
    public Container createContainer(StrategyType strategy) {
        return switch (strategy) {
            case LIFO -> new StackContainer();
            case FIFO -> null;
        };
    }
}
