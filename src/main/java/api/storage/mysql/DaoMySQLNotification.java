package api.storage.mysql;

import api.storage.exceptions.DaoException;
import api.storage.interfaces.DaoNotification;
import api.storage.models.Notification;
import api.storage.runner.Mappers;
import api.storage.runner.interfaces.ResultSetHandler;

import java.util.List;

public class DaoMySQLNotification extends DaoMySQL implements DaoNotification {

    private static final String
            SQL_SELECT_ALL_BY_USER_ID = "SELECT * FROM notifications " +
            "JOIN devices on devices.id = notifications.device_id " +
            "JOIN device_companies on device_companies.id = devices.device_company_id " +
            "JOIN device_models on device_models.id = devices.device_model_id " +
            "JOIN device_brands on device_brands.id = device_models.device_brand_id " +
            "JOIN citizens on devices.citizen_id = citizens.id " +
            "WHERE citizens.user_id = ?";

    private static final ResultSetHandler<List<Notification>>
            MANY_NOTIFICATIONS = getHandlerForMany(Mappers.notificationMapper());

    @Override
    public List<Notification> getNotifications(int userId) throws DaoException {
        return runWithResult(
                ex -> ex.query(SQL_SELECT_ALL_BY_USER_ID, MANY_NOTIFICATIONS, userId)
        );
    }

    @Override
    public void insert(Notification notification) throws DaoException {
        runWithoutResult(
                ex -> notification.setId(
                        ex.insert("INSERT INTO notifications (device_id, message, date) VALUES (?, ?, ?)",
                                notification.getDevice().getId(), notification.getMessage(), notification.getDate())
                )
        );
    }

}
