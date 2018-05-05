package notification;

import java.util.Date;

public class Notification {

	private Date date;
	public enum NotificationType {
		ADD, REMOVE, MODIFY
	}
	private NotificationType notificationType;
	private int department_id, item_id;
	public Notification(int department_id, int item_id, NotificationType x) {
		date = new Date();
		this.department_id = department_id;
		this.item_id = item_id;
		notificationType = x;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public NotificationType getNotificationType() {
		return notificationType;
	}
	public void setNotificationType(NotificationType notificationType) {
		this.notificationType = notificationType;
	}
	public int getDepartment_id() {
		return department_id;
	}
	public void setDepartment_id(int department_id) {
		this.department_id = department_id;
	}
	public int getItem_id() {
		return item_id;
	}
	public void setItem_id(int item_id) {
		this.item_id = item_id;
	}
}
