package model;

import utils.Constants;

import java.time.LocalDateTime;

public class MessageTask extends Task {
    private String message, from, to;
    private LocalDateTime date;

    public MessageTask(String taskID, String description, String message, String from, String to, LocalDateTime date) {
        super(taskID, description);
        this.message = message;
        this.from = from;
        this.to = to;
        this.date = date;
    }

    @Override
    public void execute() {
        System.out.println(message);
    }

    @Override
    public String toString() {
        return String.format("id=%s|description=%s|message=%s|from=%s|to=%s|date=%s", getTaskID(), getDescription(), message, from, to, date.format(Constants.DATE_TIME_FORMATTER));
    }
}
