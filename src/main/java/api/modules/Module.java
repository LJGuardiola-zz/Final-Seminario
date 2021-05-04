package api.modules;

import api.Api;
import api.storage.interfaces.*;
import api.storage.models.Perms;

public abstract class Module {

    private final DaoManager daoManager;

    protected void verifyAuthorization(Perms permission) {
        Api.getInstance().app().verifyAuthorization(permission.getCode());
    }

    Module(DaoManager daoManager) {
        this.daoManager = daoManager;
    }

    DaoSession daoSession() {
        return daoManager.getDaoSession();
    }

    DaoPermission daoPermission() {
        return daoManager.getDaoPermission();
    }

    DaoRole daoRole() {
        return daoManager.getDaoRole();
    }

    DaoUser daoUser() {
        return daoManager.getDaoUser();
    }

    DaoPerson daoPerson() {
        return daoManager.getDaoPerson();
    }

    DaoCitizen daoCitizen() {
        return daoManager.getDaoCitizen();
    }

    DaoSpaceCategory daoSpaceCategory() {
        return daoManager.getDaoSpaceCategory();
    }

    DaoSpaceCampaign daoSpaceCampaign() {
        return daoManager.getDaoSpaceCampaign();
    }

    DaoSpace daoSpace() {
        return daoManager.getDaoSpace();
    }

    DaoDevice daoDevice() {
        return daoManager.getDaoDevice();
    }

    DaoMovement daoMovement() {
        return daoManager.getDaoMovement();
    }

    DaoNotification daoNotification() {
        return daoManager.getDaoNotification();
    }

}
