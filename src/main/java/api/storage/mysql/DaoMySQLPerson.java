package api.storage.mysql;

import api.storage.exceptions.DaoException;
import api.storage.interfaces.DaoPerson;
import api.storage.models.LegalPerson;
import api.storage.models.NaturalPerson;
import api.storage.models.Person;
import api.storage.runner.Mappers;
import api.storage.runner.interfaces.ResultSetHandler;

import java.util.List;
import java.util.Optional;

public class DaoMySQLPerson extends DaoMySQL implements DaoPerson {

    public static final String
            SQL_SELECT_ALL              = "SELECT * FROM persons " +
                    "LEFT JOIN legal_persons on persons.id = legal_persons.person_id " +
                    "LEFT JOIN natural_persons on persons.id = natural_persons.person_id",

            SQL_SELECT_ALL_NATURAL      = "SELECT * FROM persons " +
                    "JOIN natural_persons on persons.id = natural_persons.person_id",

            SQL_SELECT_ALL_LEGAL        = "SELECT * FROM persons " +
                    "JOIN legal_persons on persons.id = legal_persons.person_id",

            SQL_SELECT_BY_ID            = "SELECT * FROM persons " +
                    "LEFT JOIN legal_persons on persons.id = legal_persons.person_id " +
                    "LEFT JOIN natural_persons on persons.id = natural_persons.person_id " +
                    "WHERE id = ?",

            SQL_SELECT_NATURAL_BY_ID    = "SELECT * FROM persons " +
                    "JOIN legal_persons on persons.id = legal_persons.person_id " +
                    "WHERE id = ?",

            SQL_SELECT_NATURAL_BY_CUIL  = "SELECT * FROM persons " +
                    "JOIN natural_persons on persons.id = natural_persons.person_id " +
                    "WHERE cuil = ?",

            SQL_SELECT_NATURAL_BY_USER_ID  = "SELECT * FROM persons " +
                    "JOIN natural_persons on persons.id = natural_persons.person_id " +
                    "JOIN citizens on natural_persons.person_id = citizens.natural_person_id " +
                    "WHERE citizens.user_id = ?",

            SQL_SELECT_LEGAL_BY_ID      = "SELECT * FROM persons " +
                    "JOIN natural_persons on persons.id = natural_persons.person_id " +
                    "WHERE id = ?",

            SQL_INSERT_PERSON           = "INSERT INTO persons (is_natural) VALUES (?)",

            SQL_INSERT_NATURAL          = "INSERT INTO natural_persons (person_id, cuil, first_name, last_name, birthdate) VALUES (?, ?, ?, ?, ?)",

            SQL_INSERT_LEGAL            = "INSERT INTO legal_persons (person_id, cuit, name) VALUES (?, ?, ?)",

            SQL_UPDATE_NATURAL          = "UPDATE natural_persons SET first_name = ?, last_name = ? WHERE person_id = ?",

            SQL_UPDATE_LEGAL            = "UPDATE legal_persons SET name = ? WHERE person_id = ?",

            SQL_DELETE                  = "DELETE FROM persons WHERE id = ?";

    public static final ResultSetHandler<Person>
            ONE_PERSON = getHandlerForOne(Mappers.personMapper());

    public static final ResultSetHandler<List<Person>>
            MANY_PERSONS = getHandlerForMany(Mappers.personMapper());

    public static final ResultSetHandler<NaturalPerson>
            ONE_NATURAL_PERSON = getHandlerForOne(Mappers.naturalPersonMapper());

    public static final ResultSetHandler<List<NaturalPerson>>
            MANY_NATURAL_PERSONS = getHandlerForMany(Mappers.naturalPersonMapper());

    public static final ResultSetHandler<LegalPerson>
            ONE_LEGAL_PERSON = getHandlerForOne(Mappers.legalPersonMapper());

    public static final ResultSetHandler<List<LegalPerson>>
            MANY_LEGAL_PERSONS = getHandlerForMany(Mappers.legalPersonMapper());

    @Override
    public Optional<Person> search(int id) throws DaoException {
        return Optional.ofNullable(
                runWithResult(
                        ex -> ex.query(SQL_SELECT_BY_ID, ONE_PERSON, id)
                )
        );
    }

    @Override
    public Optional<NaturalPerson> search(String cuil) throws DaoException {
        return Optional.ofNullable(
                runWithResult(
                        ex -> ex.query(SQL_SELECT_NATURAL_BY_CUIL, ONE_NATURAL_PERSON, cuil)
                )
        );
    }

    @Override
    public Optional<NaturalPerson> searchNatural(int id) throws DaoException {
        return Optional.ofNullable(
                runWithResult(
                        ex -> ex.query(SQL_SELECT_NATURAL_BY_ID, ONE_NATURAL_PERSON, id)
                )
        );
    }

    @Override
    public Optional<NaturalPerson> searchNaturalByUser(int userId) throws DaoException {
        return Optional.ofNullable(
                runWithResult(
                        ex -> ex.query(SQL_SELECT_NATURAL_BY_USER_ID, ONE_NATURAL_PERSON, userId)
                )
        );
    }

    @Override
    public Optional<LegalPerson> searchLegal(int id) throws DaoException {
        return Optional.ofNullable(
                runWithResult(
                        ex -> ex.query(SQL_SELECT_LEGAL_BY_ID, ONE_LEGAL_PERSON, id)
                )
        );
    }

    @Override
    public List<NaturalPerson> getAllNatural() throws DaoException {
        return runWithResult(
                ex -> ex.query(SQL_SELECT_ALL_NATURAL, MANY_NATURAL_PERSONS)
        );
    }

    @Override
    public List<LegalPerson> getAllLegal() throws DaoException {
        return runWithResult(
                ex -> ex.query(SQL_SELECT_ALL_LEGAL, MANY_LEGAL_PERSONS)
        );
    }

    @Override
    public List<Person> getAll() throws DaoException {
        return runWithResult(
                ex -> ex.query(SQL_SELECT_ALL, MANY_PERSONS)
        );
    }

    @Override
    public void insert(NaturalPerson person) throws DaoException {
        runInTransaction(ex -> {
            int idPerson = ex.insert(SQL_INSERT_PERSON, true);
            ex.update(SQL_INSERT_NATURAL,
                    idPerson,
                    person.getCUI(),
                    person.getFirstName(),
                    person.getLastName(),
                    person.getBirthdate()
            );
            person.setID(idPerson);
        });
    }

    @Override
    public void insert(LegalPerson person) throws DaoException {
        runInTransaction(ex -> {
            int idPerson = ex.insert(SQL_INSERT_PERSON, false);
            ex.update(SQL_INSERT_LEGAL,
                    idPerson,
                    person.getCUI(),
                    person.getName()
            );
            person.setID(idPerson);
        });
    }

    @Override
    public void update(NaturalPerson person) throws DaoException {
        runWithoutResult(
                ex -> ex.update(SQL_UPDATE_NATURAL,
                        person.getFirstName(),
                        person.getLastName(),
                        person.getID()
                )
        );
    }

    @Override
    public void update(LegalPerson person) throws DaoException {
        runWithoutResult(
                ex -> ex.update(SQL_UPDATE_LEGAL,
                        person.getName()
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
