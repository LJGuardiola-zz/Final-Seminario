package api.modules;

import api.dto.DtoUser;
import api.dto.DtoUserState;
import api.exceptions.ApiException;
import api.exceptions.InvalidDataException;
import api.storage.exceptions.DaoException;
import api.storage.interfaces.DaoManager;
import api.storage.models.Role;
import api.storage.models.User;
import net.sf.oval.ConstraintViolation;
import net.sf.oval.Validator;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static api.storage.models.Perms.*;

public class ModuleUser extends Module {

    public ModuleUser(DaoManager daoManager) {
        super(daoManager);
    }

    private User getUser(int id) {
        try {
            Optional<User> result = daoUser().search(id);
            if (result.isPresent())
                return result.get();
            throw new ApiException("No se encontró ningún usuario con ID " + id);
        } catch (DaoException e) {
            throw new ApiException(e);
        }
    }

    public DtoUser search(int id) {
        verifyAuthorization(user_read);
        return new DtoUser(getUser(id));
    }

    private List<DtoUser> getDtoUsers(List<User> users) {
        return users.stream()
                .map(DtoUser::new)
                .collect(Collectors.toList());
    }

    public List<DtoUser> getAll() {
        try {
            verifyAuthorization(user_read);
            return getDtoUsers(
                    daoUser().getAll()
            );
        } catch (DaoException de) {
            throw new ApiException(de);
        }
    }

    public List<DtoUserState> getUserStates() {
        return Arrays.stream(User.State.values())
                .map(DtoUserState::new)
                .collect(Collectors.toList());
    }

    public void create(String username, String email, String password, int roleId, int state) {
        try {
            verifyAuthorization(user_create);
            Optional<Role> roleOpt = daoRole().search(roleId);
            if (roleOpt.isPresent()) {
                User user = new User(username, email, password, roleOpt.get(), state);
                List<ConstraintViolation> violations = new Validator().validate(user);
                if (violations.isEmpty())
                    daoUser().insert(user);
                else throw new InvalidDataException(violations);
            } else throw new ApiException("No existe un rol con id " + roleId + ".");
        } catch (DaoException de) {
            throw new ApiException(de);
        }
    }

    public void enable(int id) {
        verifyAuthorization(user_update);
        User user = getUser(id);
        if (!user.isEnabled()) {
            user.enable();
            update(user);
        } else throw new ApiException("La cuenta \""+ user.getUsername() + "\" ya se encuentra activada.");
    }

    public void disable(int id) {
        verifyAuthorization(user_update);
        User user = getUser(id);
        if (!user.isDisabled()) {
            user.disable();
            update(user);
        } else throw new ApiException("La cuenta \""+ user.getUsername() + "\" ya se encuentra desactivada.");
    }

    public void suspend(int id) {
        verifyAuthorization(user_update);
        User user = getUser(id);
        if (!user.isSuspended()) {
            user.suspend();
            update(user);
        } else throw new ApiException("La cuenta \""+ user.getUsername() + "\" ya se encuentra suspendida.");
    }

    private void update(User user) {
        try {
            List<ConstraintViolation> violations = new Validator().validate(user);
            if (violations.isEmpty())
                daoUser().update(user);
            else throw new InvalidDataException(violations);
        } catch (DaoException de) {
            throw new ApiException(de);
        }
    }

    public void delete(int id) {
        try {
            verifyAuthorization(user_delete);
            daoUser().delete(id);
        } catch (DaoException de) {
            throw new ApiException(de);
        }
    }

}
