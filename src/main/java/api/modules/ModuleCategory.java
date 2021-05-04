package api.modules;

import api.dto.DtoCategory;
import api.exceptions.ApiException;
import api.exceptions.InvalidDataException;
import api.storage.exceptions.DaoException;
import api.storage.interfaces.DaoManager;
import api.storage.models.SpaceCategory;
import net.sf.oval.ConstraintViolation;
import net.sf.oval.Validator;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static api.storage.models.Perms.*;

public class ModuleCategory extends Module {

    public ModuleCategory(DaoManager daoManager) {
        super(daoManager);
    }

    private List<DtoCategory> getDtoCategories(List<SpaceCategory> categories) {
        return categories.stream()
                .map(DtoCategory::new)
                .collect(Collectors.toList());
    }

    private SpaceCategory getCategory(int id) {
        try {
            Optional<SpaceCategory> result = daoSpaceCategory().search(id);
            if (result.isPresent())
                return result.get();
            throw new ApiException("No se encontró ninguna categoría con ID " + id);
        } catch (DaoException e) {
            throw new ApiException(e);
        }
    }

    public DtoCategory search(int id) {
        verifyAuthorization(category_read);
        return new DtoCategory(getCategory(id));
    }

    public List<DtoCategory> getAll() {
        try {
            verifyAuthorization(category_read);
            return getDtoCategories(
                    daoSpaceCategory().getAll()
            );
        } catch (DaoException de) {
            throw new ApiException(de);
        }
    }

    public DtoCategory create(String name, String description) {
        try {
            verifyAuthorization(category_create);
            SpaceCategory category = new SpaceCategory(name, description);
            List<ConstraintViolation> violations = new Validator().validate(category);
            if (violations.isEmpty()) {
                daoSpaceCategory().insert(category);
                return new DtoCategory(category);
            } else throw new InvalidDataException(violations);
        } catch (DaoException de) {
            throw new ApiException(de);
        }
    }

    public DtoCategory update(int id, String name, String description) {
        verifyAuthorization(category_update);
        SpaceCategory category = getCategory(id);
        category.setName(name);
        category.setDescription(description);
        List<ConstraintViolation> violations = new Validator().validate(category);
        if (violations.isEmpty()) {
            updateCategory(category);
            return new DtoCategory(category);
        } else throw new InvalidDataException(violations);
    }

    private void updateCategory(SpaceCategory category) {
        try {
            daoSpaceCategory().update(category);
        } catch (DaoException de) {
            throw new ApiException(de);
        }
    }

    public void delete(int id) {
        try {
            verifyAuthorization(category_delete);
            daoSpaceCategory().delete(id);
        } catch (DaoException de) {
            throw new ApiException(de);
        }
    }

}
