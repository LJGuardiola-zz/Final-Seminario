package api.storage.interfaces;

import api.storage.exceptions.DaoException;
import api.storage.models.Notification;

import java.util.List;

public interface DaoNotification {

    List<Notification> getNotifications(int userId)
        throws DaoException;

    void insert(Notification notification)
        throws DaoException;

}
