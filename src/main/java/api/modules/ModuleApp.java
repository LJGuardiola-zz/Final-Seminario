package api.modules;

import api.dto.*;
import api.exceptions.*;
import api.storage.exceptions.DaoException;
import api.storage.interfaces.DaoManager;
import api.storage.models.*;
import net.sf.oval.ConstraintViolation;
import net.sf.oval.Validator;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static api.exceptions.LoginFailedException.ErrorType;

public class ModuleApp extends Module {

    private User userLogged = null;

    public ModuleApp(DaoManager daoManager) {
        super(daoManager);
    }

    public Optional<DtoMovement> getCurrentMovement() {
        try {
            verifyLogged();
            Optional<Movement> movement = daoMovement().searchCurrent(
                    getCurrentDevice().getId()
            );
            return movement.map(DtoMovement::new);
        } catch (DaoException e) {
            e.printStackTrace();
            throw new ApiException(e);
        }
    }

    public DtoDevice getCurrentDevice() {
        try {
            verifyLogged();
            return new DtoDevice(
                    daoDevice().getCurrent(userLogged.getID())
                            .orElseThrow(NoDevicePairedException::new)
            );
        } catch (DaoException e) {
            throw new ApiException(e);
        }
    }

    public List<DtoDevice> getDevices() {
        try {
            verifyLogged();
            return daoDevice().getAll(userLogged.getID())
                    .stream()
                    .map(DtoDevice::new)
                    .collect(Collectors.toList());
        } catch (DaoException e) {
            throw new ApiException(e);
        }
    }

    public void linkDevice(String number, int deviceCompanyId, int deviceModelId) {

        try {

            verifyLogged();

            Optional<Device> current = daoDevice().getCurrent(userLogged.getID());

            if (current.isPresent() && getCurrentMovement().isPresent()) {
                throw new ApiException("No puede cambiar el dispositivo ya que se encuentra registrado en " +
                        getCurrentMovement().get().getSpace().getName());
            }

            DeviceCompany company = daoDevice().getCompany(deviceCompanyId)
                    .orElseThrow(() -> new ApiException("La compañía no es válida."));

            DeviceModel model = daoDevice().getModel(deviceModelId)
                    .orElseThrow(() -> new ApiException("El modelo no válido."));

            Device device = new Device(number, company, model);

            List<ConstraintViolation> violations = new Validator().validate(device);

            if (violations.isEmpty()) {
                if (current.isPresent())
                    daoDevice().delete(current.get());
                daoDevice().insert(userLogged.getID(), device);
            } else throw new InvalidDataException(violations);

        } catch (DaoException e) {
            throw new ApiException(e);
        }

    }

    public void imHere(int spaceId, String amount) {
        try {
            verifyLogged();

            Device device = daoDevice().getCurrent(userLogged.getID())
                    .orElseThrow(NoDevicePairedException::new);

            Space space = daoSpace().search(spaceId)
                    .orElseThrow(() -> new ApiException("El espacio no es válido."));

            int amountI = 1;
            try {
                amountI = Integer.parseInt(amount);
            } catch (NumberFormatException ignored) {}

            Movement movement = new Movement(device, space, amountI);

            List<ConstraintViolation> violations = new Validator().validate(movement);

            if (violations.isEmpty()) {
                Optional<Movement> searchCurrent = daoMovement().searchCurrent(device.getId());
                if (searchCurrent.isPresent())
                    throw new ApiException("Actualmente se encuentra registrado en " + searchCurrent.get().getSpace().getName());
                daoMovement().insert(movement);
                daoNotification().insert(new Notification(device, space.getEntryCampaign().getMessage()));
            } else throw new InvalidDataException(violations);

        } catch (DaoException e) {
            throw new ApiException(e);
        }
    }

    public void imNotHere() {
        try {
            verifyLogged();
            Optional<Movement> movement = daoMovement().searchCurrent(
                    getCurrentDevice().getId()
            );
            if (movement.isPresent()) {
                daoMovement().delete(movement.get());
                daoNotification().insert(
                        new Notification(
                                movement.get().getDevice(),
                                movement.get().getSpace().getExitCampaign().getMessage()
                        )
                );
            } else throw new ApiException("El dispositivo no se encuentra registrado en ningún espacio.");
        } catch (DaoException e) {
            throw new ApiException(e);
        }
    }

    public List<DtoNotification> getNotifications() {
        try {
            return daoNotification().getNotifications(userLogged.getID())
                    .stream().map(DtoNotification::new).collect(Collectors.toList());
        } catch (DaoException e) {
            throw new ApiException(e);
        }
    }

    public void register(String firstName, String lastName, String cuil, LocalDate birthdate,
                         String username, String email, String password) {
        try {

            NaturalPerson person = new NaturalPerson(cuil, firstName, lastName, birthdate);
            Role role = daoRole().search(3).orElseThrow(ApiException::new);
            User user = new User(username, email, password, role);

            List<ConstraintViolation> violations = new ArrayList<>(new Validator().validate(person));
            violations.addAll(new Validator().validate(user));

            if (violations.isEmpty()) {
                daoCitizen().register(person, user);
            } else throw new InvalidDataException(violations);

        } catch (DaoException e) {
            throw new ApiException(e);
        }
    }

    public DtoUser login(String username, String password) throws LoginFailedException {

        if (username == null || username.isEmpty() || password == null || password.isEmpty())
            throw new LoginFailedException(ErrorType.EMPTY);

        try {

            Optional<User> result = daoUser().search(username);

            User user = result.orElseThrow(
                    () -> new LoginFailedException(ErrorType.USERNAME)
            );

            if (!user.passwordEquals(password))
                throw new LoginFailedException(ErrorType.PASSWORD);

            if (!user.isEnabled())
                throw new LoginFailedException(ErrorType.NO_ACTIVE);

            return new DtoUser(userLogged = user);

        } catch (DaoException e) {
            throw new LoginFailedException(ErrorType.OTHER, e);
        }

    }

    public void logout() {
        verifyLogged();
        userLogged = null;
    }

    private void verifyLogged() {
        if (!isLogged())
            throw new ApiException("No se ha iniciado sesión.");
    }

    public boolean isLogged() {
        return userLogged != null;
    }

    public DtoUser getUserLogged() {
        verifyLogged();
        return new DtoUser(userLogged);
    }

    public DtoUserLogged getUserLoggedData() {
        verifyLogged();
        try {
            NaturalPerson person = daoPerson().searchNaturalByUser(userLogged.getID())
                    .orElseThrow(() -> new ApiException("No se pudieron encontrar sus datos."));
            return new DtoUserLogged(userLogged, person);
        } catch (DaoException e) {
            throw new ApiException(e);
        }
    }

    public void updateUserLoggedData(String firstname, String lastname) {
        verifyLogged();
        try {
            NaturalPerson person = daoPerson().searchNaturalByUser(userLogged.getID())
                    .orElseThrow(() -> new ApiException("No se pudieron encontrar sus datos."));
            person.setFirstName(firstname);
            person.setLastName(lastname);
            daoPerson().update(person);
        } catch (DaoException e) {
            throw new ApiException(e);
        }
    }

    public boolean hasPermission(String permissionCode) {
        try {
            verifyAuthorization(permissionCode);
            return true;
        } catch (UnAuthorizedException unused) {
            return false;
        }
    }

    public void verifyAuthorization(String permissionCode) {
        try {
            if (isLogged()) {
                daoPermission()
                        .search(userLogged.getRole().getID(), permissionCode)
                        .orElseThrow(UnAuthorizedException::new);
            } else throw new UnAuthorizedException();
        } catch (DaoException e) {
            throw new UnAuthorizedException();
        }
    }

    public void testConnection() {
        try {
            daoRole().search(1);
        } catch (DaoException e) {
            throw new ApiException("No se pudo acceder a la base de datos");
        }
    }

}
