package api.storage.interfaces;

import api.storage.exceptions.DaoException;
import api.storage.models.Device;
import api.storage.models.NaturalPerson;
import api.storage.models.User;

import java.util.List;
import java.util.Optional;

public interface DaoCitizen {

    boolean isRegistered(int personId)
        throws DaoException;

    void register(NaturalPerson person, User user)
        throws DaoException;

}
