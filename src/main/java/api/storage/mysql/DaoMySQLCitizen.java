package api.storage.mysql;

import api.storage.exceptions.DaoException;
import api.storage.interfaces.DaoCitizen;
import api.storage.models.Device;
import api.storage.models.NaturalPerson;
import api.storage.models.User;
import api.storage.runner.Mappers;
import api.storage.runner.interfaces.ResultSetHandler;

import java.sql.ResultSet;
import java.util.List;

public class DaoMySQLCitizen extends DaoMySQL implements DaoCitizen {

    private static final String
            SQL_INSERT_CITIZEN = "INSERT INTO citizens (natural_person_id, user_id) VALUES (?, ?)";


    @Override
    public boolean isRegistered(int personId) throws DaoException {
        return runWithResult(ex -> ex.query("SELECT id FROM citizens WHERE natural_person_id = ?", ResultSet::next, personId));
    }

    @Override
    public void register(NaturalPerson person, User user) throws DaoException {
        runInTransaction(ex -> {

            int idPerson;

            NaturalPerson naturalPerson = ex.query(
                    DaoMySQLPerson.SQL_SELECT_NATURAL_BY_CUIL, DaoMySQLPerson.ONE_NATURAL_PERSON, person.getCUI()
            );

            if (naturalPerson == null) {
                idPerson = ex.insert(DaoMySQLPerson.SQL_INSERT_PERSON, true);
                person.setID(idPerson);
                ex.update(DaoMySQLPerson.SQL_INSERT_NATURAL,
                        idPerson,
                        person.getCUI(),
                        person.getFirstName(),
                        person.getLastName(),
                        person.getBirthdate()
                );
            } else idPerson = naturalPerson.getID();

            person.setID(idPerson);

            int idUser = ex.insert(DaoMySQLUser.SQL_INSERT,
                    user.getUsername(),
                    user.getEmail(),
                    user.getPassword(),
                    user.getStateCode(),
                    user.getRole().getID()
            );
            user.setID(idUser);
            ex.update(SQL_INSERT_CITIZEN, idPerson, idUser);
        });
    }

}
