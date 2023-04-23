package no.ntnu.idatt1002.data;

import java.util.Date;

public final class Notification {

    private final String title;
    private final String content;
    private final Date date;
    private final Runnable action;

    public Notification(String title, String content, Date date, Runnable action) {
        this.title = title;
        this.content = content;
        this.date = date;
        this.action = action;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public Date getDate() {
        return date;
    }

    public void runAction() {
        action.run();
    }
}
