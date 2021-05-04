package api.modules;

import api.dto.DtoDeviceBrand;
import api.dto.DtoDeviceCompany;
import api.dto.DtoDeviceModel;
import api.exceptions.ApiException;
import api.storage.exceptions.DaoException;
import api.storage.interfaces.DaoManager;

import java.util.List;
import java.util.stream.Collectors;

public class ModuleDevice extends Module {

    public ModuleDevice(DaoManager daoManager) {
        super(daoManager);
    }

    public List<DtoDeviceCompany> getCompanies() {
        try {
            return daoDevice().getCompanies().stream().map(DtoDeviceCompany::new).collect(Collectors.toList());
        } catch (DaoException e) {
            throw new ApiException(e);
        }
    }

    public List<DtoDeviceBrand> getBrands() {
        try {
            return daoDevice().getBrands().stream().map(DtoDeviceBrand::new).collect(Collectors.toList());
        } catch (DaoException e) {
            throw new ApiException(e);
        }
    }

    public List<DtoDeviceModel> getModels() {
        try {
            return daoDevice().getModels().stream().map(DtoDeviceModel::new).collect(Collectors.toList());
        } catch (DaoException e) {
            throw new ApiException(e);
        }
    }

}
