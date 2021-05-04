package api.modules;

import api.dto.DtoCampaign;
import api.dto.DtoCategory;
import api.dto.DtoRole;
import api.exceptions.ApiException;
import api.exceptions.InvalidDataException;
import api.storage.exceptions.DaoException;
import api.storage.interfaces.DaoManager;
import api.storage.models.Role;
import api.storage.models.SpaceCampaign;
import api.storage.models.SpaceCategory;
import net.sf.oval.ConstraintViolation;
import net.sf.oval.Validator;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static api.storage.models.Perms.*;

public class ModuleCampaign extends Module {

    public ModuleCampaign(DaoManager daoManager) {
        super(daoManager);
    }

    private List<DtoCampaign> getDtoCampaign(List<SpaceCampaign> campaigns) {
        return campaigns.stream()
                .map(DtoCampaign::new)
                .collect(Collectors.toList());
    }

    private SpaceCampaign getCampaign(int id) {
        try {
            Optional<SpaceCampaign> result = daoSpaceCampaign().search(id);
            if (result.isPresent())
                return result.get();
            throw new ApiException("No se encontró ninguna campaña con ID " + id);
        } catch (DaoException e) {
            throw new ApiException(e);
        }
    }

    public DtoCampaign search(int id) {
        verifyAuthorization(campaign_read);
        return new DtoCampaign(getCampaign(id));
    }

    public List<DtoCampaign> getAll() {
        try {
            verifyAuthorization(campaign_read);
            return getDtoCampaign(
                    daoSpaceCampaign().getAll()
            );
        } catch (DaoException de) {
            throw new ApiException(de);
        }
    }

    public void create(String message) {
        try {
            verifyAuthorization(campaign_create);
            SpaceCampaign campaign = new SpaceCampaign(message);
            List<ConstraintViolation> violations = new Validator().validate(campaign);
            if (violations.isEmpty()) {
                daoSpaceCampaign().insert(campaign);
            } else throw new InvalidDataException(violations);
        } catch (DaoException de) {
            throw new ApiException(de);
        }
    }

    public DtoCampaign update(int id, String message) {
        verifyAuthorization(campaign_update);
        SpaceCampaign campaign = getCampaign(id);
        campaign.setMessage(message);
        List<ConstraintViolation> violations = new Validator().validate(campaign);
        if (violations.isEmpty()) {
            updateCampaign(campaign);
            return new DtoCampaign(campaign);
        } else throw new InvalidDataException(violations);
    }

    private void updateCampaign(SpaceCampaign campaign) {
        try {
            daoSpaceCampaign().update(campaign);
        } catch (DaoException de) {
            de.printStackTrace();
            throw new ApiException(de);
        }
    }

    public void delete(int id) {
        try {
            verifyAuthorization(campaign_delete);
            daoSpaceCampaign().delete(id);
        } catch (DaoException de) {
            de.printStackTrace();
            throw new ApiException(de);
        }
    }
}
