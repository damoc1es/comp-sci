package containers;

import model.Task;

public class StackContainer extends AbstractContainer {

    public StackContainer() {
        super();
    }

    @Override
    public Task remove() {
        if(!isEmpty()) {
            size--;
            Task task = tasks[size];
            tasks[size] = null;
            return task;
        }

        return null;
    }
}
