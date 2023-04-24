package no.ntnu.idatt1002.data;

import java.util.Date;

/**
 * This class represents a notification visible in the recent
 * activity tab on the home page.
 */
public final class Notification {

  private final String title;
  private final String content;
  private final Date date;
  private final Runnable action;

  /**
   * Constructs a notification.
   *
   * @param   title the title
   * @param   content the content
   * @param   date the date
   * @param   action the action
   */
  public Notification(String title, String content, Date date, Runnable action) {
    this.title = title;
    this.content = content;
    this.date = date;
    this.action = action;
  }

  /**
   * Returns the notification title.
   *
   * @return  the notification title
   */
  public String getTitle() {
    return title;
  }

  /**
   * Returns the notification content.
   *
   * @return  the notification content
   */
  public String getContent() {
    return content;
  }

  /**
   * Returns the notification date.
   *
   * @return  the notification date
   */
  public Date getDate() {
    return date;
  }

  /**
   * Invoke the runnable for this notification.
   */
  public void runAction() {
    action.run();
  }
}
