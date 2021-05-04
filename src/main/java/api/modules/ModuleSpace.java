package api.modules;

import api.dto.*;
import api.exceptions.ApiException;
import api.exceptions.InvalidDataException;
import api.storage.exceptions.DaoException;
import api.storage.interfaces.DaoManager;
import api.storage.models.*;
import api.util.CheckNumber;
import net.sf.oval.ConstraintViolation;
import net.sf.oval.Validator;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static api.storage.models.Perms.*;
import static api.util.CheckNumber.*;

public class ModuleSpace extends Module {

    public ModuleSpace(DaoManager daoManager) {
        super(daoManager);
    }

    public void create(String name, boolean available, String latitude, String longitude,
                       String radius, String capacity, int categoryId, int entryCampaignId, int exitCampaignId) {

        try {

            verifyAuthorization(space_create);

            SpaceCategory category = daoSpaceCategory().search(categoryId)
                    .orElseThrow(() -> new InvalidDataException("La categoría es invalida."));

            SpaceCampaign entryCampaign = daoSpaceCampaign().search(entryCampaignId)
                    .orElseThrow(() -> new InvalidDataException("La campaña de entrada es invalida."));

            SpaceCampaign exitCampaign = daoSpaceCampaign().search(exitCampaignId)
                    .orElseThrow(() -> new InvalidDataException("La campaña de salida es invalida."));

            OpenSpace openSpace = new OpenSpace(
                    name, available, checkInteger(capacity, "La capacidad debe ser un entero."),
                    new DelimitedArea(
                            new Coordinate(
                                    checkDouble(latitude, "La latitud debe ser un numero."),
                                    checkDouble(longitude, "La longitud debe ser un numero.")
                            ), CheckNumber.checkFloat(radius, "El radio debe ser un numero.")),
                    category, entryCampaign, exitCampaign
            );

            List<ConstraintViolation> violations = new Validator().validate(openSpace);

            if (violations.isEmpty()) {
                daoSpace().insert(openSpace);
            } else throw new InvalidDataException(violations);

        } catch (DaoException e) {
            throw new ApiException(e);
        }

    }

    public void create(String name, boolean available, String street, int cityId, String latitude, String longitude,
                       String radius, String capacity, int categoryId, int entryCampaignId, int exitCampaignId, int responsibleId) {

        try {

            verifyAuthorization(space_create);

            SpaceCategory category = daoSpaceCategory().search(categoryId)
                    .orElseThrow(() -> new InvalidDataException("La categoría es invalida."));

            SpaceCampaign entryCampaign = daoSpaceCampaign().search(entryCampaignId)
                    .orElseThrow(() -> new InvalidDataException("La campaña de entrada es invalida."));

            SpaceCampaign exitCampaign = daoSpaceCampaign().search(exitCampaignId)
                    .orElseThrow(() -> new InvalidDataException("La campaña de salida es invalida."));

            City city = daoSpace().getCity(cityId)
                    .orElseThrow(() -> new InvalidDataException("La ciudad es invalida."));

            Person responsible = daoPerson().search(responsibleId)
                    .orElseThrow(() -> new InvalidDataException("El responsable es invalido."));

            ClosedSpace closedSpace = new ClosedSpace(
                    name, available, checkInteger(capacity, "La capacidad debe ser un entero."),
                    new DelimitedArea(
                            new Coordinate(
                                    checkDouble(latitude, "La latitud debe ser un numero."),
                                    checkDouble(longitude, "La longitud debe ser un numero.")
                            ), CheckNumber.checkFloat(radius, "El radio debe ser un numero.")),
                    category, entryCampaign, exitCampaign,
                    new Address(street, city),
                    responsible
            );

            List<ConstraintViolation> violations = new Validator().validate(closedSpace);

            if (violations.isEmpty()) {
                daoSpace().insert(closedSpace);
            } else throw new InvalidDataException(violations);

        } catch (DaoException e) {
            e.printStackTrace();
            throw new ApiException(e);
        }

    }

    public void update(int id, String name, boolean available, String latitude, String longitude,
                       String radius, String capacity, int categoryId, int entryCampaignId, int exitCampaignId) {

        try {

            verifyAuthorization(space_update);

            OpenSpace openSpace = daoSpace().searchOpen(id)
                    .orElseThrow(() -> new ApiException("No se encontró ningún espacio con id " + id));

            SpaceCategory category = daoSpaceCategory().search(categoryId)
                    .orElseThrow(() -> new InvalidDataException("La categoría es invalida."));

            SpaceCampaign entryCampaign = daoSpaceCampaign().search(entryCampaignId)
                    .orElseThrow(() -> new InvalidDataException("La campaña de entrada es invalida."));

            SpaceCampaign exitCampaign = daoSpaceCampaign().search(exitCampaignId)
                    .orElseThrow(() -> new InvalidDataException("La campaña de salida es invalida."));

            openSpace.setName(name);
            if (available) openSpace.setAvailable(); else openSpace.setUnavailable();
            openSpace.getArea().getCoordinate().setLatitude(checkDouble(latitude, "La latitud debe ser un numero."));
            openSpace.getArea().getCoordinate().setLongitude(checkDouble(longitude, "La longitud debe ser un numero."));
            openSpace.getArea().setRadius(checkFloat(radius, "El radio debe ser un numero."));
            openSpace.setCapacity(checkInteger(capacity, "La capacidad debe ser un entero"));
            openSpace.setCategory(category);
            openSpace.setEntryCampaign(entryCampaign);
            openSpace.setExitCampaign(exitCampaign);

            List<ConstraintViolation> violations = new Validator().validate(openSpace);

            if (violations.isEmpty()) {
                daoSpace().update(openSpace);
            } else throw new InvalidDataException(violations);

        } catch (DaoException e) {
            e.printStackTrace();
            throw new ApiException(e);
        }

    }

    public void update(int id, String name, boolean available, String street, int cityId, String latitude, String longitude,
                       String radius, String capacity, int categoryId, int entryCampaignId, int exitCampaignId, int responsibleId) {

        try {

            verifyAuthorization(space_update);

            ClosedSpace closedSpace = daoSpace().searchClosed(id)
                    .orElseThrow(() -> new ApiException("No se encontró ningún espacio con id " + id));

            SpaceCategory category = daoSpaceCategory().search(categoryId)
                    .orElseThrow(() -> new InvalidDataException("La categoría es invalida."));

            SpaceCampaign entryCampaign = daoSpaceCampaign().search(entryCampaignId)
                    .orElseThrow(() -> new InvalidDataException("La campaña de entrada es invalida."));

            SpaceCampaign exitCampaign = daoSpaceCampaign().search(exitCampaignId)
                    .orElseThrow(() -> new InvalidDataException("La campaña de salida es invalida."));

            City city = daoSpace().getCity(cityId)
                    .orElseThrow(() -> new InvalidDataException("La ciudad es invalida."));

            Person responsible = daoPerson().search(responsibleId)
                    .orElseThrow(() -> new InvalidDataException("El responsable es invalido."));

            closedSpace.setName(name);
            if (available) closedSpace.setAvailable(); else closedSpace.setUnavailable();
            closedSpace.setAddress(
                    new Address(street, city)
            );

            closedSpace.getArea().getCoordinate().setLatitude(checkDouble(latitude, "La latitud debe ser un numero."));
            closedSpace.getArea().getCoordinate().setLongitude(checkDouble(longitude, "La longitud debe ser un numero."));
            closedSpace.getArea().setRadius(checkFloat(radius, "El radio debe ser un numero."));
            closedSpace.setCapacity(checkInteger(capacity, "La capacidad debe ser un entero"));
            closedSpace.setCategory(category);
            closedSpace.setEntryCampaign(entryCampaign);
            closedSpace.setExitCampaign(exitCampaign);

            List<ConstraintViolation> violations = new Validator().validate(closedSpace);

            if (violations.isEmpty()) {
                daoSpace().update(closedSpace);
                if (!closedSpace.getResponsible().equals(responsible)) {
                    daoSpace().updateResponsible(closedSpace, responsible);
                }
            } else throw new InvalidDataException(violations);

        } catch (DaoException e) {
            throw new ApiException(e);
        }

    }

    public DtoOpenSpace searchOpen(int id) {
        try {
            verifyAuthorization(space_read);
            return new DtoOpenSpace(
                    daoSpace().searchOpen(id)
                            .orElseThrow(() -> new ApiException("No se encontró ningún espacio abierto con id " + id))
            );
        } catch (DaoException e) {
            throw new ApiException(e);
        }
    }

    public DtoClosedSpace searchClosed(int id) {
        try {
            verifyAuthorization(space_read);
            return new DtoClosedSpace(
                    daoSpace().searchClosed(id)
                            .orElseThrow(() -> new ApiException("No se encontró ningún espacio cerrado con id " + id))
            );
        } catch (DaoException e) {
            throw new ApiException(e);
        }
    }

    private List<DtoSpace> getDtoSpaces(List<Space> spaces) {
        return spaces.stream()
                .map(DtoSpace::new)
                .collect(Collectors.toList());
    }

    public List<DtoSpace> getAll() {
        try {
            return getDtoSpaces(
                    daoSpace().getAll()
            );
        } catch (DaoException e) {
            throw new ApiException(e);
        }
    }

    private List<DtoOpenSpace> getDtoOpenSpaces(List<OpenSpace> spaces) {
        return spaces.stream()
                .map(DtoOpenSpace::new)
                .collect(Collectors.toList());
    }

    public List<DtoOpenSpace> getAllOpen() {
        try {
            verifyAuthorization(space_read);
            return getDtoOpenSpaces(
                    daoSpace().getAllOpen()
            );
        } catch (DaoException e) {
            throw new ApiException(e);
        }
    }

    private List<DtoClosedSpace> getDtoClosedSpaces(List<ClosedSpace> spaces) {
        return spaces.stream()
                .map(DtoClosedSpace::new)
                .collect(Collectors.toList());
    }

    public List<DtoClosedSpace> getAllClosed() {
        try {
            verifyAuthorization(space_read);
            return getDtoClosedSpaces(
                    daoSpace().getAllClosed()
            );
        } catch (DaoException e) {
            throw new ApiException(e);
        }
    }

    public List<DtoCity> getAllCities() {
        try {
            return daoSpace().getAllCities().stream().map(DtoCity::new).collect(Collectors.toList());
        } catch (DaoException e) {
            throw new ApiException(e);
        }
    }

    public void delete(int id) {
        try {
            verifyAuthorization(space_delete);
            daoSpace().delete(id);
        } catch (DaoException e) {
            throw new ApiException(e);
        }
    }

    public List<DtoWarning> getSpacesWarnings() {
        try {
            List<DtoWarning> warnings = new ArrayList<>();
            for (Space space : daoSpace().getAll()) {
                int amount = daoMovement().getCurrent(space).stream().mapToInt(Movement::getAmount).sum();
                if (amount > space.getCapacity()) {
                    warnings.add(
                            new DtoWarning(
                                    space.getName(),
                                    space instanceof OpenSpace ? "Abierto" : "Cerrado",
                                    space.getCapacity(),
                                    amount
                            )
                    );
                }
            }
            return warnings;
        } catch (DaoException e) {
            throw new ApiException(e);
        }
    }

    public List<DtoCategoriesSummary> getSummariesForCategories() {
        try {
            List<DtoCategoriesSummary> summaries = new ArrayList<>();
            List<Movement> movements = daoMovement().getAll();
            for (SpaceCategory category : daoSpaceCategory().getAll()) {
                List<Movement> currentMovements = movements.stream()
                        .filter(movement -> movement.getSpace().getCategory().equals(category) &&
                                movement.getEntryDate().toLocalDate().equals(LocalDate.now()))
                        .collect(Collectors.toList());
                int devices = currentMovements.size();
                int amount = currentMovements.stream().mapToInt(Movement::getAmount).sum();
                summaries.add(
                        new DtoCategoriesSummary(
                                category.getID(), category.getName(), devices, amount
                        )
                );
            }
            return summaries;
        } catch (DaoException e) {
            throw new ApiException(e);
        }
    }

    public List<DtoSpaceSummary> getSummaries(LocalDateTime date) {
        try {
            List<DtoSpaceSummary> summaries = new ArrayList<>();
            List<Movement> movements = daoMovement().getAll();
            for (Space space : daoSpace().getAll()) {
                List<Movement> filtered = movements.stream().filter(
                        movement -> movement.getSpace().equals(space) &&
                                movement.getEntryDate().isBefore(date) &&
                                (movement.getExitDate() == null || movement.getExitDate().isAfter(date))
                ).collect(Collectors.toList());
                int persons = filtered.stream().mapToInt(Movement::getAmount).sum();
                summaries.add(
                        new DtoSpaceSummary(
                                space.getName(),
                                space.getCapacity(),
                                filtered.size(),
                                persons,
                                space.getCapacity() < persons
                        )
                );
            }
            return summaries;
        } catch (DaoException e) {
            throw new ApiException(e);
        }
    }

}
