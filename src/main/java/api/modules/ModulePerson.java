package api.modules;

import api.dto.DtoNaturalPerson;
import api.dto.DtoPerson;
import api.exceptions.ApiException;
import api.exceptions.InvalidDataException;
import api.storage.exceptions.DaoException;
import api.storage.interfaces.DaoManager;
import api.storage.models.LegalPerson;
import api.storage.models.NaturalPerson;
import api.storage.models.Person;
import net.sf.oval.ConstraintViolation;
import net.sf.oval.Validator;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static api.storage.models.Perms.person_delete;
import static api.storage.models.Perms.person_read;

public class ModulePerson extends Module {

    public ModulePerson(DaoManager daoManager) {
        super(daoManager);
    }

    public void createLegalPerson(String cuit, String name) {
        try {
            LegalPerson person = new LegalPerson(cuit, name);
            List<ConstraintViolation> violations = new Validator().validate(person);
            if (violations.isEmpty()) {
                daoPerson().insert(person);
            } else throw new InvalidDataException(violations);
        } catch (DaoException e) {
            throw new ApiException(e);
        }
    }

    public void createNaturalPerson(String cuil, String firstName, String lastName, LocalDate birthdate) {
        try {
            NaturalPerson person = new NaturalPerson(cuil, firstName, lastName, birthdate);
            List<ConstraintViolation> violations = new Validator().validate(person);
            if (violations.isEmpty()) {
                daoPerson().insert(person);
            } else throw new InvalidDataException(violations);
        } catch (DaoException e) {
            throw new ApiException(e);
        }
    }

    public DtoNaturalPerson search(String cuil) {
        try {
            List<ConstraintViolation> violations = new Validator().validateFieldValue(
                    cuil, NaturalPerson.class.getDeclaredField("CUIL"), cuil
            );
            if (violations.isEmpty())
                return daoPerson().search(cuil)
                        .map(DtoNaturalPerson::new)
                        .orElse(null);
            else throw new ApiException("El CUIL ingresado no es válido.");
        } catch (DaoException | NoSuchFieldException e) {
            throw new ApiException(e);
        }
    }

    public DtoPerson getPerson(Integer id) {
        try {
            Optional<Person> result = daoPerson().search(id);
            if (result.isPresent())
                return new DtoPerson(result.get());
            else throw new ApiException("No se encontró ninguna persona con ID " + id);
        } catch (DaoException e) {
            throw new ApiException(e);
        }
    }

    private List<DtoPerson> getDtoPerson(List<Person> persons) {
        return persons.stream()
                .map(DtoPerson::new)
                .collect(Collectors.toList());
    }

    public List<DtoPerson> getAll() {
        try {
            verifyAuthorization(person_read);
            return getDtoPerson(
                    daoPerson().getAll()
            );
        } catch (DaoException de) {
            throw new ApiException(de);
        }
    }

    public void delete(int id) {
        try {
            verifyAuthorization(person_delete);
            if (daoCitizen().isRegistered(id))
                throw new ApiException("Esta persona esta asociada a una cuenta de usuario");
            daoPerson().delete(id);
        } catch (DaoException de) {
            throw new ApiException(de);
        }
    }

}
