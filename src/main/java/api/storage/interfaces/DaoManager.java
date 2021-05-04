package api.storage.interfaces;

public interface DaoManager {
    DaoSession          getDaoSession();
    DaoPermission       getDaoPermission();
    DaoRole             getDaoRole();
    DaoUser             getDaoUser();
    DaoPerson           getDaoPerson();
    DaoCitizen          getDaoCitizen();
    DaoCity             getDaoCity();
    DaoSpaceCampaign    getDaoSpaceCampaign();
    DaoSpaceCategory    getDaoSpaceCategory();
    DaoSpace            getDaoSpace();
    DaoDevice           getDaoDevice();
    DaoMovement         getDaoMovement();
    DaoNotification     getDaoNotification();
}
