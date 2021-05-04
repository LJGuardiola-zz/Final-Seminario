package api.storage.interfaces;

import api.storage.exceptions.DaoException;
import api.storage.models.Device;
import api.storage.models.DeviceBrand;
import api.storage.models.DeviceCompany;
import api.storage.models.DeviceModel;

import java.util.List;
import java.util.Optional;

public interface DaoDevice {

    Optional<Device> getCurrent(int id)
        throws DaoException;

    List<Device> getAll(int userId)
        throws DaoException;

    Optional<DeviceCompany> getCompany(int id)
        throws DaoException;

    List<DeviceCompany> getCompanies()
        throws DaoException;

    Optional<DeviceBrand> getBrand(int id)
        throws DaoException;

    List<DeviceBrand> getBrands()
        throws DaoException;

    Optional<DeviceModel> getModel(int id)
        throws DaoException;

    List<DeviceModel> getModels()
        throws DaoException;

    void insert(int userId, Device device)
        throws DaoException;

    void delete(Device device)
        throws DaoException;

}
