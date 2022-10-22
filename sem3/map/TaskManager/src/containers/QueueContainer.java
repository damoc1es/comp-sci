package containers;

import model.Task;

public class QueueContainer extends AbstractContainer {

    public QueueContainer() {
        super();
    }

    @Override
    public Task remove() {
        if(!isEmpty()) {
            size--;
            Task task = tasks[0];
            for(int i=0; i<size; i++) {
                tasks[i] = tasks[i+1];
            }
            return task;
        }

        return null;
    }

}
