package no.ntnu.idatt1002.data;

import java.util.Date;

public final class Notification {

    private final String title;
    private final String text;
    private final Date date;

    public Notification(String title, String text, Date date) {
        this.title = title;
        this.text = text;
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public String getText() {
        return text;
    }

    public Date getDate() {
        return date;
    }
}
