package api.storage.interfaces;

import api.storage.exceptions.DaoException;
import api.storage.models.LegalPerson;
import api.storage.models.NaturalPerson;
import api.storage.models.Person;

import java.util.List;
import java.util.Optional;

public interface DaoPerson {

    Optional<Person> search(int ID)
            throws DaoException;

    Optional<NaturalPerson> search(String cuil) throws DaoException;

    Optional<NaturalPerson> searchNatural(int ID)
            throws DaoException;

    Optional<NaturalPerson> searchNaturalByUser(int userId)
            throws DaoException;

    Optional<LegalPerson> searchLegal(int ID)
            throws DaoException;

    List<NaturalPerson> getAllNatural()
            throws DaoException;
    List<LegalPerson> getAllLegal()
            throws DaoException;
    List<Person> getAll() throws
            DaoException;

    void insert(NaturalPerson person)
            throws DaoException;
    void insert(LegalPerson person)
            throws DaoException;

    void update(NaturalPerson person)
            throws DaoException;
    void update(LegalPerson person)
            throws DaoException;

    void delete(int ID)
            throws DaoException;

}
