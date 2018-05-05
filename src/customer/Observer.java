package customer;

import notification.Notification;

public interface Observer {
	public void update (Notification notification);
}
