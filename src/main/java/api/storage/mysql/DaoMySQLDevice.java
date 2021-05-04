package api.storage.mysql;

import api.storage.exceptions.DaoException;
import api.storage.interfaces.DaoDevice;
import api.storage.models.Device;
import api.storage.models.DeviceBrand;
import api.storage.models.DeviceCompany;
import api.storage.models.DeviceModel;
import api.storage.runner.Mappers;
import api.storage.runner.interfaces.ResultSetHandler;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class DaoMySQLDevice extends DaoMySQL implements DaoDevice {

    private static final String
            SQL_SELECT_ALL_BY_USER_ID = "SELECT * FROM devices " +
                                        "JOIN device_models on device_models.id = devices.device_model_id " +
                                        "JOIN device_brands on device_brands.id = device_models.device_brand_id " +
                                        "JOIN device_companies on device_companies.id = devices.device_company_id " +
                                        "JOIN citizens on citizens.id = devices.citizen_id " +
                                        "WHERE citizens.user_id = ?",

            SQL_SELECT_CURRENT_BY_USER_ID = "SELECT * FROM devices " +
                                        "JOIN device_models on device_models.id = devices.device_model_id " +
                                        "JOIN device_brands on device_brands.id = device_models.device_brand_id " +
                                        "JOIN device_companies on device_companies.id = devices.device_company_id " +
                                        "JOIN citizens on citizens.id = devices.citizen_id " +
                                        "WHERE citizens.user_id = ? and devices.end_date is null";

    private static final ResultSetHandler<Device> ONE_DEVICE = getHandlerForOne(Mappers.deviceMapper());
    private static final ResultSetHandler<List<Device>> MANY_DEVICES = getHandlerForMany(Mappers.deviceMapper());

    private static final ResultSetHandler<DeviceCompany> ONE_COMPANY = getHandlerForOne(Mappers.deviceCompanyMapper());
    private static final ResultSetHandler<List<DeviceCompany>> MANY_COMPANIES = getHandlerForMany(Mappers.deviceCompanyMapper());

    private static final ResultSetHandler<DeviceBrand> ONE_BRAND = getHandlerForOne(Mappers.deviceBrandMapper());
    private static final ResultSetHandler<List<DeviceBrand>> MANY_BRANDS = getHandlerForMany(Mappers.deviceBrandMapper());

    private static final ResultSetHandler<DeviceModel> ONE_MODEL = getHandlerForOne(Mappers.deviceModelMapper());
    private static final ResultSetHandler<List<DeviceModel>> MANY_MODELS = getHandlerForMany(Mappers.deviceModelMapper());

    @Override
    public Optional<Device> getCurrent(int userId) throws DaoException {
        return Optional.ofNullable(
                runWithResult(ex -> ex.query(SQL_SELECT_CURRENT_BY_USER_ID, ONE_DEVICE, userId))
        );
    }

    @Override
    public List<Device> getAll(int userId) throws DaoException {
        return runWithResult(ex -> ex.query(SQL_SELECT_ALL_BY_USER_ID, MANY_DEVICES, userId));
    }

    @Override
    public Optional<DeviceCompany> getCompany(int id) throws DaoException {
        return Optional.ofNullable(
                runWithResult(ex -> ex.query("SELECT * FROM device_companies WHERE id = ?", ONE_COMPANY, id))
        );
    }

    @Override
    public List<DeviceCompany> getCompanies() throws DaoException {
        return runWithResult(ex -> ex.query("SELECT * FROM device_companies", MANY_COMPANIES));
    }

    @Override
    public Optional<DeviceBrand> getBrand(int id) throws DaoException {
        return Optional.ofNullable(
                runWithResult(ex -> ex.query("SELECT * FROM device_brands WHERE id = ?", ONE_BRAND, id))
        );
    }

    @Override
    public List<DeviceBrand> getBrands() throws DaoException {
        return runWithResult(ex -> ex.query("SELECT * FROM device_brands", MANY_BRANDS));
    }

    @Override
    public Optional<DeviceModel> getModel(int id) throws DaoException {
        return Optional.ofNullable(
                runWithResult(ex -> ex.query("SELECT * FROM device_models JOIN device_brands on device_brands.id = device_models.device_brand_id WHERE device_models.id = ?", ONE_MODEL, id))
        );
    }

    @Override
    public List<DeviceModel> getModels() throws DaoException {
        return runWithResult(ex -> ex.query("SELECT * FROM device_models JOIN device_brands on device_brands.id = device_models.device_brand_id", MANY_MODELS));
    }

    @Override
    public void insert(int userId, Device device) throws DaoException {
        runInTransaction(ex -> {
            Integer citizenId = ex.query("SELECT id FROM citizens WHERE user_id = ?", rs -> {
                rs.next();
                return rs.getInt("citizens.id");
            }, userId);
            ex.update(
                    "INSERT INTO devices (number, citizen_id, device_company_id, device_model_id) VALUES (?, ? ,?, ?)",
                    device.getNumber(), citizenId, device.getCompany().getId(), device.getModel().getId()
            );
        });
    }

    @Override
    public void delete(Device device) throws DaoException {
        runWithoutResult(ex -> ex.update("UPDATE devices SET end_date = ? WHERE id = ?", Timestamp.valueOf(LocalDateTime.now()), device.getId()));
    }

}
