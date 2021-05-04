package api.storage.interfaces;

import api.storage.exceptions.DaoException;
import api.storage.models.SpaceCampaign;

import java.util.List;
import java.util.Optional;

public interface DaoSpaceCampaign {

    Optional<SpaceCampaign> search(int id) throws DaoException;

    List<SpaceCampaign> getAll() throws DaoException;

    void insert(SpaceCampaign spaceCampaign) throws DaoException;

    void update(SpaceCampaign spaceCampaign) throws DaoException;

    void delete(int id) throws DaoException;

}
