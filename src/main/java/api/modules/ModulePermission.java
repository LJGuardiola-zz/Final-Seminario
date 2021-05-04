package api.modules;

import api.dto.DtoPermission;
import api.exceptions.ApiException;
import api.storage.exceptions.DaoException;
import api.storage.interfaces.DaoManager;
import api.storage.models.Permission;

import java.util.List;
import java.util.stream.Collectors;

public class ModulePermission extends Module {

    public ModulePermission(DaoManager daoManager) {
        super(daoManager);
    }

    private List<DtoPermission> getDtoPermissions(List<Permission> permissions) {
        return permissions.stream()
                .map(DtoPermission::new)
                .collect(Collectors.toList());
    }

    public List<DtoPermission> getAll() {
        try {
            return getDtoPermissions(
                    daoPermission().getAll()
            );
        } catch (DaoException de) {
            throw new ApiException(de);
        }
    }

}
