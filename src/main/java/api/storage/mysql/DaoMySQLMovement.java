package api.storage.mysql;

import api.storage.exceptions.DaoException;
import api.storage.interfaces.DaoMovement;
import api.storage.models.Movement;
import api.storage.models.Space;
import api.storage.runner.Mappers;
import api.storage.runner.interfaces.ResultSetHandler;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class DaoMySQLMovement extends DaoMySQL implements DaoMovement {

    private static final String
            SQL_INSERT = "INSERT INTO movements (device_id, space_id, amount) VALUES (?, ?, ?)",
            SQL_DELETE = "UPDATE movements SET exit_date = ? WHERE id = ?",
            SQL_CURRENT = "SELECT * FROM movements\n" +
                    "JOIN devices on devices.id = movements.device_id\n" +
                    "JOIN device_companies on device_companies.id = devices.device_company_id\n" +
                    "JOIN device_models on device_models.id = devices.device_model_id\n" +
                    "JOIN device_brands on device_brands.id = device_models.device_brand_id\n" +
                    "JOIN spaces on spaces.id = movements.space_id\n" +
                    "JOIN space_categories on spaces.category_id = space_categories.id\n" +
                    "JOIN space_campaigns as entry_campaign on spaces.entry_campaign_id = entry_campaign.id\n" +
                    "JOIN space_campaigns as exit_campaign on spaces.exit_campaign_id = exit_campaign.id\n" +
                    "LEFT JOIN closed_spaces on spaces.id = closed_spaces.space_id\n" +
                    "LEFT JOIN cities on closed_spaces.city_id = cities.id\n" +
                    "LEFT JOIN responsible on closed_spaces.space_id = responsible.closed_space_id\n" +
                    "LEFT JOIN persons on responsible.person_id = persons.id\n" +
                    "LEFT JOIN legal_persons on persons.id = legal_persons.person_id\n" +
                    "LEFT JOIN natural_persons on persons.id = natural_persons.person_id\n" +
                    "WHERE movements.device_id = ? AND movements.exit_date IS NULL",
            SQL_SELECT_ALL = "SELECT * FROM movements\n" +
                    "JOIN devices on devices.id = movements.device_id\n" +
                    "JOIN device_companies on device_companies.id = devices.device_company_id\n" +
                    "JOIN device_models on device_models.id = devices.device_model_id\n" +
                    "JOIN device_brands on device_brands.id = device_models.device_brand_id\n" +
                    "JOIN spaces on spaces.id = movements.space_id\n" +
                    "JOIN space_categories on spaces.category_id = space_categories.id\n" +
                    "JOIN space_campaigns as entry_campaign on spaces.entry_campaign_id = entry_campaign.id\n" +
                    "JOIN space_campaigns as exit_campaign on spaces.exit_campaign_id = exit_campaign.id\n" +
                    "LEFT JOIN closed_spaces on spaces.id = closed_spaces.space_id\n" +
                    "LEFT JOIN cities on closed_spaces.city_id = cities.id\n" +
                    "LEFT JOIN responsible on closed_spaces.space_id = responsible.closed_space_id\n" +
                    "LEFT JOIN persons on responsible.person_id = persons.id\n" +
                    "LEFT JOIN legal_persons on persons.id = legal_persons.person_id\n" +
                    "LEFT JOIN natural_persons on persons.id = natural_persons.person_id\n" +
                    "GROUP BY movements.id",
            SQL_CURRENT_BY_SPACE = "SELECT * FROM movements\n" +
                    "JOIN devices on devices.id = movements.device_id\n" +
                    "JOIN device_companies on device_companies.id = devices.device_company_id\n" +
                    "JOIN device_models on device_models.id = devices.device_model_id\n" +
                    "JOIN device_brands on device_brands.id = device_models.device_brand_id\n" +
                    "JOIN spaces on spaces.id = movements.space_id\n" +
                    "JOIN space_categories on spaces.category_id = space_categories.id\n" +
                    "JOIN space_campaigns as entry_campaign on spaces.entry_campaign_id = entry_campaign.id\n" +
                    "JOIN space_campaigns as exit_campaign on spaces.exit_campaign_id = exit_campaign.id\n" +
                    "LEFT JOIN closed_spaces on spaces.id = closed_spaces.space_id\n" +
                    "LEFT JOIN cities on closed_spaces.city_id = cities.id\n" +
                    "LEFT JOIN responsible on closed_spaces.space_id = responsible.closed_space_id\n" +
                    "LEFT JOIN persons on responsible.person_id = persons.id\n" +
                    "LEFT JOIN legal_persons on persons.id = legal_persons.person_id\n" +
                    "LEFT JOIN natural_persons on persons.id = natural_persons.person_id\n" +
                    "WHERE movements.space_id = ? AND exit_date IS NULL";

    private static final ResultSetHandler<Movement> ONE_MOVEMENT = getHandlerForOne(Mappers.movementMapper());
    private static final ResultSetHandler<List<Movement>> MANY_MOVEMENTS = getHandlerForMany(Mappers.movementMapper());

    @Override
    public List<Movement> getAll() throws DaoException {
        return runWithResult(
                ex -> ex.query(SQL_SELECT_ALL, MANY_MOVEMENTS)
        );
    }

    @Override
    public List<Movement> getCurrent(Space space) throws DaoException {
        return runWithResult(
                ex -> ex.query(SQL_CURRENT_BY_SPACE, MANY_MOVEMENTS, space.getID())
        );
    }

    @Override
    public Optional<Movement> searchCurrent(int deviceId) throws DaoException {
        return Optional.ofNullable(
                runWithResult(
                        ex -> ex.query(SQL_CURRENT, ONE_MOVEMENT, deviceId)
                )
        );
    }

    @Override
    public void insert(Movement movement) throws DaoException {
        runWithoutResult(
                ex -> movement.setId(
                        ex.insert(SQL_INSERT, movement.getDevice().getId(), movement.getSpace().getID(), movement.getAmount())
                )
        );
    }

    @Override
    public void delete(Movement movement) throws DaoException {
        runWithoutResult(
                ex -> ex.update(SQL_DELETE, Timestamp.valueOf(LocalDateTime.now()), movement.getId())
        );
    }

}
