package api.storage.mysql;

import api.storage.exceptions.DaoException;
import api.storage.interfaces.DaoSpaceCampaign;
import api.storage.models.SpaceCampaign;
import api.storage.runner.Mappers;
import api.storage.runner.interfaces.ResultSetHandler;

import java.util.List;
import java.util.Optional;

public class DaoMySQLSpaceCampaign extends DaoMySQL implements DaoSpaceCampaign {

    private static final String
            SQL_SELECT_ALL      = "SELECT * FROM space_campaigns",
            SQL_SELECT_BY_ID    = "SELECT * FROM space_campaigns WHERE id = ?",
            SQL_INSERT          = "INSERT INTO space_campaigns (message) VALUES (?)",
            SQL_UPDATE          = "UPDATE space_campaigns SET message = ? WHERE id = ?",
            SQL_DELETE          = "DELETE FROM space_campaigns WHERE id = ?";

    private static final ResultSetHandler<SpaceCampaign>
            ONE_SPACE_CAMPAIGN = getHandlerForOne(Mappers.spaceCampaignMapper());

    private static final ResultSetHandler<List<SpaceCampaign>>
            MANY_SPACE_CAMPAIGNS = getHandlerForMany(Mappers.spaceCampaignMapper());

    @Override
    public Optional<SpaceCampaign> search(int id) throws DaoException {
        return Optional.ofNullable(
                runWithResult(
                        ex -> ex.query(SQL_SELECT_BY_ID, ONE_SPACE_CAMPAIGN, id)
                )
        );
    }

    @Override
    public List<SpaceCampaign> getAll() throws DaoException {
        return runWithResult(
                ex -> ex.query(SQL_SELECT_ALL, MANY_SPACE_CAMPAIGNS)
        );
    }

    @Override
    public void insert(SpaceCampaign spaceCampaign) throws DaoException {
        runWithoutResult(
                ex -> spaceCampaign.setID(
                        ex.insert(SQL_INSERT,
                                spaceCampaign.getMessage()
                        )
                )
        );
    }

    @Override
    public void update(SpaceCampaign spaceCampaign) throws DaoException {
        runWithoutResult(
                ex -> ex.update(SQL_UPDATE,
                        spaceCampaign.getMessage(),
                        spaceCampaign.getID()
                )
        );
    }

    @Override
    public void delete(int id) throws DaoException {
        runWithoutResult(
                ex -> ex.update(SQL_DELETE, id)
        );
    }

}
