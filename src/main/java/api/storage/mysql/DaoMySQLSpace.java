package api.storage.mysql;

import api.storage.exceptions.DaoException;
import api.storage.interfaces.DaoSpace;
import api.storage.models.*;
import api.storage.runner.Mappers;
import api.storage.runner.interfaces.ResultSetHandler;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class DaoMySQLSpace extends DaoMySQL implements DaoSpace {

    private static final String

            SQL_SELECT_ALL = "SELECT * FROM spaces " +
                    "JOIN space_categories on spaces.category_id = space_categories.id " +
                    "JOIN space_campaigns as entry_campaign on spaces.entry_campaign_id = entry_campaign.id " +
                    "JOIN space_campaigns as exit_campaign on spaces.exit_campaign_id = exit_campaign.id " +
                    "LEFT JOIN closed_spaces on spaces.id = closed_spaces.space_id " +
                    "LEFT JOIN cities on closed_spaces.city_id = cities.id " +
                    "LEFT JOIN responsible on closed_spaces.space_id = responsible.closed_space_id " +
                    "LEFT JOIN persons on responsible.person_id = persons.id " +
                    "LEFT JOIN legal_persons on persons.id = legal_persons.person_id " +
                    "LEFT JOIN natural_persons on persons.id = natural_persons.person_id " +
                    "GROUP BY spaces.id",

            SQL_SELECT_ALL_OPEN = "SELECT * FROM spaces " +
                    "JOIN space_categories on spaces.category_id = space_categories.id " +
                    "JOIN space_campaigns as entry_campaign on spaces.entry_campaign_id = entry_campaign.id " +
                    "JOIN space_campaigns as exit_campaign on spaces.exit_campaign_id = exit_campaign.id " +
                    "WHERE is_closed = 0",

            SQL_SELECT_ALL_CLOSED = "SELECT * FROM spaces " +
                    "JOIN space_categories on spaces.category_id = space_categories.id " +
                    "JOIN space_campaigns as entry_campaign on spaces.entry_campaign_id = entry_campaign.id " +
                    "JOIN space_campaigns as exit_campaign on spaces.exit_campaign_id = exit_campaign.id " +
                    "JOIN closed_spaces on spaces.id = closed_spaces.space_id " +
                    "JOIN cities on closed_spaces.city_id = cities.id " +
                    "JOIN responsible on closed_spaces.space_id = responsible.closed_space_id and responsible.end_date is null " +
                    "JOIN persons on responsible.person_id = persons.id " +
                    "LEFT JOIN legal_persons on persons.id = legal_persons.person_id " +
                    "LEFT JOIN natural_persons on persons.id = natural_persons.person_id",

            SQL_SELECT_BY_ID = "SELECT * FROM spaces " +
                    "JOIN space_categories on spaces.category_id = space_categories.id " +
                    "JOIN space_campaigns as entry_campaign on spaces.entry_campaign_id = entry_campaign.id " +
                    "JOIN space_campaigns as exit_campaign on spaces.exit_campaign_id = exit_campaign.id " +
                    "LEFT JOIN closed_spaces on spaces.id = closed_spaces.space_id " +
                    "LEFT JOIN cities on closed_spaces.city_id = cities.id " +
                    "LEFT JOIN responsible on closed_spaces.space_id = responsible.closed_space_id " +
                    "LEFT JOIN persons on responsible.person_id = persons.id " +
                    "LEFT JOIN legal_persons on persons.id = legal_persons.person_id " +
                    "LEFT JOIN natural_persons on persons.id = natural_persons.person_id " +
                    "WHERE spaces.id = ?",

            SQL_SELECT_OPEN_BY_ID = "SELECT * FROM spaces " +
                    "JOIN space_categories on spaces.category_id = space_categories.id " +
                    "JOIN space_campaigns as entry_campaign on spaces.entry_campaign_id = entry_campaign.id " +
                    "JOIN space_campaigns as exit_campaign on spaces.exit_campaign_id = exit_campaign.id " +
                    "WHERE spaces.id = ?",

            SQL_SELECT_CLOSED_BY_ID = "SELECT * FROM spaces " +
                    "JOIN space_categories on spaces.category_id = space_categories.id " +
                    "JOIN space_campaigns as entry_campaign on spaces.entry_campaign_id = entry_campaign.id " +
                    "JOIN space_campaigns as exit_campaign on spaces.exit_campaign_id = exit_campaign.id " +
                    "JOIN closed_spaces on spaces.id = closed_spaces.space_id " +
                    "JOIN cities on closed_spaces.city_id = cities.id " +
                    "JOIN responsible on closed_spaces.space_id = responsible.closed_space_id and responsible.end_date is null " +
                    "JOIN persons on responsible.person_id = persons.id " +
                    "LEFT JOIN legal_persons on persons.id = legal_persons.person_id " +
                    "LEFT JOIN natural_persons on persons.id = natural_persons.person_id " +
                    "WHERE spaces.id = ?",

            SQL_SELECT_ALL_CITIES = "SELECT * FROM cities",
            SQL_SELECT_ONE_CITY = "SELECT * FROM cities WHERE id = ?",

            SQL_INSERT_SPACE = "INSERT INTO " +
                    "spaces (name, available, is_closed, capacity, latitude, longitude, radius, " +
                        "category_id, entry_campaign_id, exit_campaign_id) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",

            SQL_INSERT_CLOSED = "INSERT INTO " +
                    "closed_spaces (space_id, street, city_id) " +
                    "VALUES (?, ?, ?)",

            SQL_INSERT_RESPONSIBLE = "INSERT INTO " +
                    "responsible (closed_space_id, person_id, start_date) " +
                    "VALUES (?, ?, ?)",

            SQL_UPDATE_RESPONSIBLE = "UPDATE responsible " +
                    "SET end_date = ? " +
                    "WHERE closed_space_id = ? AND end_date IS NULL",

            SQL_UPDATE_SPACE = "UPDATE spaces " +
                    "SET name = ?, available = ?, capacity = ?, latitude = ?, longitude = ?, radius = ?, " +
                        "category_id = ?, entry_campaign_id = ?, exit_campaign_id = ? " +
                    "WHERE id = ?",

            SQL_DELETE = "DELETE FROM spaces WHERE id = ?";

    private static final ResultSetHandler<Space>
            ONE_SPACE = getHandlerForOne(Mappers.spaceMapper());

    private static final ResultSetHandler<List<Space>>
            MANY_SPACES = getHandlerForMany(Mappers.spaceMapper());

    private static final ResultSetHandler<OpenSpace>
            ONE_OPEN_SPACE = getHandlerForOne(Mappers.openSpaceMapper());

    private static final ResultSetHandler<List<OpenSpace>>
            MANY_OPEN_SPACES = getHandlerForMany(Mappers.openSpaceMapper());

    private static final ResultSetHandler<ClosedSpace>
            ONE_CLOSED_SPACE = getHandlerForOne(Mappers.closedSpaceMapper());

    private static final ResultSetHandler<List<ClosedSpace>>
            MANY_CLOSED_SPACES = getHandlerForMany(Mappers.closedSpaceMapper());

    private static final ResultSetHandler<List<City>>
            MANY_CITIES = getHandlerForMany(Mappers.cityMapper());

    private static final ResultSetHandler<City>
            ONE_CITY = getHandlerForOne(Mappers.cityMapper());

    @Override
    public Optional<Space> search(int id) throws DaoException {
        return Optional.ofNullable(
                runWithResult(
                        ex -> ex.query(SQL_SELECT_BY_ID, ONE_SPACE, id)
                )
        );
    }

    @Override
    public Optional<OpenSpace> searchOpen(int id) throws DaoException {
        return Optional.ofNullable(
                runWithResult(
                        ex -> ex.query(SQL_SELECT_OPEN_BY_ID, ONE_OPEN_SPACE, id)
                )
        );
    }

    @Override
    public Optional<ClosedSpace> searchClosed(int id) throws DaoException {
        return Optional.ofNullable(
                runWithResult(
                        ex -> ex.query(SQL_SELECT_CLOSED_BY_ID, ONE_CLOSED_SPACE, id)
                )
        );
    }

    @Override
    public List<OpenSpace> getAllOpen() throws DaoException {
        return runWithResult(
                ex -> ex.query(SQL_SELECT_ALL_OPEN, MANY_OPEN_SPACES)
        );
    }

    @Override
    public List<ClosedSpace> getAllClosed() throws DaoException {
        return runWithResult(
                ex -> ex.query(SQL_SELECT_ALL_CLOSED, MANY_CLOSED_SPACES)
        );
    }

    @Override
    public List<Space> getAll() throws DaoException {
        return runWithResult(
                ex -> ex.query(SQL_SELECT_ALL, MANY_SPACES)
        );
    }

    @Override
    public List<City> getAllCities() throws DaoException {
        return runWithResult(
                ex -> ex.query(SQL_SELECT_ALL_CITIES, MANY_CITIES)
        );
    }

    @Override
    public Optional<City> getCity(int id) throws DaoException {
        return Optional.ofNullable(
                runWithResult(
                        ex -> ex.query(SQL_SELECT_ONE_CITY, ONE_CITY, id)
                )
        );
    }

    @Override
    public void insert(OpenSpace openSpace) throws DaoException {
        runWithoutResult(
                ex -> openSpace.setID(
                        ex.insert(SQL_INSERT_SPACE,
                                openSpace.getName(),
                                openSpace.isAvailable(),
                                false,
                                openSpace.getCapacity(),
                                openSpace.getArea().getLatitude(),
                                openSpace.getArea().getLongitude(),
                                openSpace.getArea().getRadius(),
                                openSpace.getCategory().getID(),
                                openSpace.getEntryCampaign().getID(),
                                openSpace.getExitCampaign().getID()
                        )
                )
        );
    }

    @Override
    public void insert(ClosedSpace closedSpace) throws DaoException {
        runInTransaction(ex -> {
            int idSpace = ex.insert(SQL_INSERT_SPACE,
                    closedSpace.getName(),
                    closedSpace.isAvailable(),
                    true,
                    closedSpace.getCapacity(),
                    closedSpace.getArea().getLatitude(),
                    closedSpace.getArea().getLongitude(),
                    closedSpace.getArea().getRadius(),
                    closedSpace.getCategory().getID(),
                    closedSpace.getEntryCampaign().getID(),
                    closedSpace.getExitCampaign().getID()
            );
            ex.update(SQL_INSERT_CLOSED,
                    idSpace,
                    closedSpace.getAddress().getStreet(),
                    closedSpace.getAddress().getCity().getID()
            );
            ex.update(SQL_INSERT_RESPONSIBLE,
                    idSpace,
                    closedSpace.getResponsible().getID(),
                    Timestamp.valueOf(LocalDateTime.now())
            );
            closedSpace.setID(idSpace);
        });
    }

    @Override
    public void update(OpenSpace openSpace) throws DaoException {
        runWithoutResult(
                ex -> ex.update(SQL_UPDATE_SPACE,
                        openSpace.getName(),
                        openSpace.isAvailable(),
                        openSpace.getCapacity(),
                        openSpace.getArea().getLatitude(),
                        openSpace.getArea().getLongitude(),
                        openSpace.getArea().getRadius(),
                        openSpace.getCategory().getID(),
                        openSpace.getEntryCampaign().getID(),
                        openSpace.getExitCampaign().getID(),
                        openSpace.getID()
                )
        );
    }

    @Override
    public void update(ClosedSpace closedSpace) throws DaoException {
        runInTransaction(ex -> {
            ex.update("UPDATE spaces " +
                            "SET name = ?, available = ?, capacity = ?, latitude = ?, longitude = ?, radius = ?, category_id = ?, entry_campaign_id = ?, exit_campaign_id = ? " +
                            "WHERE id = ?",
                    closedSpace.getName(),
                    closedSpace.isAvailable(),
                    closedSpace.getCapacity(),
                    closedSpace.getArea().getLatitude(),
                    closedSpace.getArea().getLongitude(),
                    closedSpace.getArea().getRadius(),
                    closedSpace.getCategory().getID(),
                    closedSpace.getEntryCampaign().getID(),
                    closedSpace.getExitCampaign().getID(),
                    closedSpace.getID()
            );
            ex.update("UPDATE closed_spaces " +
                            "SET street = ?, city_id = ? " +
                            "WHERE space_id = ?",
                    closedSpace.getAddress().getStreet(),
                    closedSpace.getAddress().getCity().getID(),
                    closedSpace.getID()
            );
        });
    }

    @Override
    public void updateResponsible(ClosedSpace closedSpace, Person person) throws DaoException {
        if (closedSpace.getResponsible().equals(person))
            throw new DaoException("La persona ya es responsable del espacio.");
        runInTransaction(ex -> {
            LocalDateTime now = LocalDateTime.now();
            ex.update(SQL_UPDATE_RESPONSIBLE,
                    Timestamp.valueOf(now),
                    closedSpace.getID()
            );
            ex.update(SQL_INSERT_RESPONSIBLE,
                    closedSpace.getID(),
                    person.getID(),
                    Timestamp.valueOf(now)
            );
            closedSpace.setResponsible(person);
        });
    }

    @Override
    public void delete(int id) throws DaoException {
        runWithoutResult(
                ex -> ex.update(SQL_DELETE, id)
        );
    }

}
