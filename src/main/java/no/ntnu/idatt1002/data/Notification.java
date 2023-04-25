package no.ntnu.idatt1002.data;

import java.util.Date;
import java.util.Objects;

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

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Notification that = (Notification) o;
    return Objects.equals(title, that.title) && Objects.equals(content, that.content)
            && Objects.equals(date, that.date) && Objects.equals(action, that.action);
  }

  @Override
  public int hashCode() {
    return Objects.hash(title, content, date, action);
  }
}
