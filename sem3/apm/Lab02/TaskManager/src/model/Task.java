package model;

import java.util.Objects;

public abstract class Task {
    private String taskID, description;

    public Task(String taskID, String description) {
        this.taskID = taskID;
        this.description = description;
    }

    public String getTaskID() {
        return taskID;
    }

    public void setTaskID(String taskID) {
        this.taskID = taskID;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public abstract void execute();

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;

        if(!(o instanceof Task task))
            return false;

        return Objects.equals(getTaskID(), task.getTaskID()) && Objects.equals(getDescription(), task.getDescription());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTaskID(), getDescription());
    }

    @Override
    public String toString() {
        return getTaskID() + " " + getDescription();
    }
}
